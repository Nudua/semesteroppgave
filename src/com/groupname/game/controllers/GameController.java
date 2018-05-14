package com.groupname.game.controllers;

import com.groupname.framework.concurrency.TaskRunner;
import com.groupname.framework.graphics.background.transitions.BlindsScreenTransition;
import com.groupname.game.entities.Player;
import com.groupname.game.views.menus.PauseButton;
import com.groupname.framework.graphics.background.transitions.ArrowScreenTransition;
import com.groupname.framework.graphics.background.transitions.ScreenTransition;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import com.groupname.framework.serialization.ObjectSerializer;
import com.groupname.framework.serialization.SerializationException;
import com.groupname.game.data.PlayerProgress;
import com.groupname.game.data.SaveData;
import com.groupname.game.scene.SceneManager;
import com.groupname.game.scene.SceneName;
import com.groupname.game.core.Game;
import com.groupname.game.editor.metadata.LevelMetaData;
import com.groupname.game.input.PlayerInputDefinitions;
import com.groupname.game.levels.*;
import com.groupname.game.levels.core.LevelBase;
import com.groupname.game.levels.core.LevelState;
import com.groupname.game.views.menus.GameMenuFX;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.junit.rules.Stopwatch;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * This controller is used to connect the fxml (views/gameview.fxml)
 * and handles the lifecycle of the game itself. It also handles
 * loading levels and switching between the current levels.
 *
 * Note: consider making a level manager.
 */
public class GameController implements Controller {
    @FXML protected GridPane root;
    @FXML protected Canvas canvas;
    @FXML protected Button pauseButton;
    @FXML protected HBox loadingBox;

    private Game game;

    private GameMenuFX<PauseButton> pauseMenu;

    //private List<LevelBase> levels = new CopyOnWriteArrayList<>();

    private ConcurrentMap<Integer, LevelBase> levels = new ConcurrentHashMap<>();

    private boolean isPaused = false;
    private int currentLevelIndex = 0;

    private ScreenTransition levelCompletedTransition;
    private int gameOverIndex = 0;

    private final TaskRunner taskRunner = new TaskRunner();

    private long now;

    /**
     * Creates a new instance of this controller.
     */
    public GameController() {
    }

    /**
     * Initializes the controller with the specified game to run on the specified game.
     *
     * @param game the game instance to use for this controller.
     * @param parameters the String id of the level that you want to start on.
     */
    @Override
    public void init(Game game, Object parameters) {
        this.game = Objects.requireNonNull(game);

        now = System.currentTimeMillis();

        loadLevels(parameters);
    }

    // Load all our levels in parallel
    private void loadLevels(Object parameters) {
        ObjectSerializer reader = new ObjectSerializer();

        List<Runnable> levelLoadAction = new ArrayList<>();

        for(int i = 1; i <= 8; i++) {
            final int index = i;
            Runnable load = () -> { loadLevel(reader, index); };
            levelLoadAction.add(load);
        }

        Runnable loadCredits = () -> {
            LevelBase credits = new Credits(game, canvas.getGraphicsContext2D());
            credits.initialize();
            levels.put(8, credits);
        };

        levelLoadAction.add(loadCredits);

        Runnable loadGameOver = () -> {
            LevelBase gameOver = new GameOver(game, canvas.getGraphicsContext2D());
            gameOver.initialize();

            levels.put(9, gameOver);

            gameOverIndex = getKeyFromLevel(gameOver);
        };

        levelLoadAction.add(loadGameOver);

        taskRunner.submitAll(levelLoadAction, () -> levelsLoadCompleted(parameters));
    }

    private void loadLevel(ObjectSerializer reader, int index) {
        boolean error = false;
        String errorMessage = "";

        try {
            String levelFile = String.format("level%d.level", index);

            LevelMetaData levelMetaData = reader.read(Content.loadFile(levelFile, ResourceType.LEVEL), LevelMetaData.class);

            Level level = new Level(game, canvas.getGraphicsContext2D(), levelMetaData);
            level.initialize();

            levels.put(index - 1, level);
        } catch (SerializationException exception) {
            error = true;
            errorMessage = exception.getMessage();
        }

        if(error) {
            System.err.println("Error loading level" + errorMessage);
        }
    }

    private void levelsLoadCompleted(Object parameters) {
        long completed =  System.currentTimeMillis() - now;
        System.out.println("Loading of levels took: " + completed + "ms");

        int startHitpoints = Player.DEFAULT_HITPOINTS;

        // The parameters may be the String id of the level to load
        if(parameters instanceof SaveData) {
            SaveData progress = (SaveData)parameters;
            Optional<LevelBase> level = getLevelFromId(progress.getCurrentLevel());

            if(level.isPresent()) {
                currentLevelIndex = getKeyFromLevel(level.get());
                startHitpoints = progress.getHitpoints();
            }
        }

        LevelBase currentLevel = getCurrentLevel();

        trySetPlayerHitpoints(startHitpoints);

        onPlayerDead(currentLevel);

        levelCompletedTransition = getRandomScreenTransition();

        if(!game.isRunning()) {
            game.start();
        }

        game.initialize(canvas, this::update, this::draw);

        loadingBox.setVisible(false);

        setupMenu();
    }

    private ScreenTransition getRandomScreenTransition() {
        List<ScreenTransition> screenTransitions = Arrays.asList(
                new ArrowScreenTransition(canvas.getGraphicsContext2D()),
                new BlindsScreenTransition(canvas.getGraphicsContext2D())
        );

        ThreadLocalRandom random = ThreadLocalRandom.current();

        int randomIndex = random.nextInt(screenTransitions.size());

        return screenTransitions.get(randomIndex);

    }

    private int getKeyFromLevel(LevelBase level) {
        for(Map.Entry<Integer, LevelBase> entry : levels.entrySet()) {
            if(entry.getValue() == level) {
                return entry.getKey();
            }
        }
        return -1;
    }

    private Optional<LevelBase> getLevelFromId(String levelId) {
        return levels.values().stream().filter(n -> n.getId().equals(levelId)).findFirst();
    }

    private LevelBase getCurrentLevel() {
        return levels.get(currentLevelIndex);
    }



    private void setupMenu() {
        pauseMenu = new GameMenuFX<>(PauseButton.class, "/com/groupname/game/views/menus/pausemenu.fxml");

        pauseMenu.setOnClicked(PauseButton.Resume, this::unPause);
        pauseMenu.setOnClicked(PauseButton.MainMenu, () -> SceneManager.navigate(SceneName.TITLE));
        pauseMenu.setOnClicked(PauseButton.RestartLevel, () -> {
            getCurrentLevel().reset();
            unPause();
        });
        pauseMenu.setOnClicked(PauseButton.RestartGame, () -> {
            currentLevelIndex = 0;
            LevelBase currentLevel = getCurrentLevel();
            currentLevel.reset();
            onPlayerDead(currentLevel);
            unPause();
        });
        pauseMenu.setOnClicked(PauseButton.Save, this::save);

        root.getChildren().add(pauseMenu);

        unPause();
    }

    private int getCurrentHitpointsOfPlayerIfPossible() {
        LevelBase currentLevel = getCurrentLevel();

        if(currentLevel instanceof Level) {
            Optional<Player> player = ((Level) currentLevel).getPlayerIfExists();

            if(player.isPresent()) {
                return player.get().getHitPoints();
            }
        }
        return Player.DEFAULT_HITPOINTS;
    }

    private void trySetPlayerHitpoints(int value) {
        LevelBase currentLevel = getCurrentLevel();

        if(currentLevel instanceof Level) {
            Optional<Player> player = ((Level) currentLevel).getPlayerIfExists();

            player.ifPresent(n -> n.setHitPoints(value));
        }
    }

    // Save our current progress
    private void save() {
        PlayerProgress playerProgress = new PlayerProgress();

        int hitPoints = getCurrentHitpointsOfPlayerIfPossible();

        playerProgress.save(new SaveData(getCurrentLevel().getId(), hitPoints));
    }

    private void onPlayerDead(LevelBase level) {
        if(level instanceof Level) {
            ((Level) level).setOnPlayerDead(() ->{
                currentLevelIndex = gameOverIndex;
            });
        }
    }

    private void update(InputManager inputManager) {
        inputManager.update();

        if(isPaused) {
            pauseMenu.update(inputManager);

        } else {
            LevelBase currentLevel = getCurrentLevel();

            if(currentLevel.getState() == LevelState.COMPLETED) {

                if(levelCompletedTransition.isDone()) {
                    int hitPoints = getCurrentHitpointsOfPlayerIfPossible();

                    currentLevelIndex++;

                    // Update to the next level
                    currentLevel = getCurrentLevel();
                    currentLevel.reset();

                    trySetPlayerHitpoints(hitPoints);

                    onPlayerDead(currentLevel);

                    levelCompletedTransition.reset();
                } else {
                    levelCompletedTransition.update();
                }

            }

            currentLevel.update();

            if(inputManager.wasPressed(PlayerInputDefinitions.START) || inputManager.wasPressed(PlayerInputDefinitions.SELECT)) {
                pause();
            }
        }
    }

    @FXML
    protected void pauseButtonOnAction(ActionEvent event) {
        pause();
    }

    private void pause() {
        Platform.runLater(() -> pauseMenu.focusButton(PauseButton.Resume));

        isPaused = true;
        pauseMenu.setVisible(true);
        canvas.setOpacity(0.5d);
        pauseButton.setVisible(false);
    }

    private void unPause() {
        isPaused = false;
        pauseMenu.setVisible(false);
        canvas.setOpacity(1);
        pauseButton.setVisible(true);
    }

    private void draw() {
        LevelBase currentLevel = getCurrentLevel();

        if(currentLevel.getState() == LevelState.PLAYING) {
           currentLevel.draw();
        } else if(currentLevel.getState() == LevelState.COMPLETED && !levelCompletedTransition.isDone()) {
            levelCompletedTransition.draw();
        }
    }

    /**
     * Stub method, does nothing in this controller.
     */
    @Override
    public void exit() {
        if(!taskRunner.isShutdown()){
            try {
                taskRunner.stop();
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
}