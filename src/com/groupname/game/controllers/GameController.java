package com.groupname.game.controllers;

import com.groupname.framework.core.PauseButton;
import com.groupname.framework.graphics.background.transitions.BlindsScreenTransition;
import com.groupname.framework.graphics.background.transitions.ScreenTransition;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import com.groupname.framework.serialization.ObjectSerializer;
import com.groupname.framework.serialization.SerializationException;
import com.groupname.framework.util.Strings;
import com.groupname.game.data.SaveData;
import com.groupname.game.scene.SceneManager;
import com.groupname.game.scene.SceneName;
import com.groupname.game.core.Game;
import com.groupname.game.editor.metadata.LevelMetaData;
import com.groupname.game.data.AppSettings;
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
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;

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

    private Game game;

    private GameMenuFX<PauseButton> pauseMenu;

    private List<LevelBase> levels = new ArrayList<>();

    private boolean isPaused = false;
    private int currentLevelIndex = 0;

    private ScreenTransition levelCompletedTransition;
    private int gameOverIndex = 0;

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
    public void init(Game game, Object parameters) {
        this.game = Objects.requireNonNull(game);

        game.initialize(canvas, this::update, this::draw);

        loadLevels();

        // The parameters may be the String id of the level to load
        if(parameters instanceof String) {
            Optional<LevelBase> level = getLevelFromId((String)parameters);
            level.ifPresent((n) -> currentLevelIndex = levels.indexOf(level.get()));
        }

        LevelBase currentLevel = getCurrentLevel();

        onPlayerDead(currentLevel);

        levelCompletedTransition = new BlindsScreenTransition(canvas.getGraphicsContext2D());

        if(!game.isRunning()) {
            game.start();
        }

        setupMenu();
    }

    private Optional<LevelBase> getLevelFromId(String levelId) {
        return levels.stream().filter(n -> n.getId().equals(levelId)).findFirst();
    }

    private LevelBase getCurrentLevel() {
        return levels.get(currentLevelIndex);
    }

    private void loadLevels() {
        LevelBase credits = new Credits(game, canvas.getGraphicsContext2D());
        credits.initialize();

        ObjectSerializer reader = new ObjectSerializer();

        String[] levelFiles = {"level1.level", "level2.level"};

        for(String levelPath: levelFiles) {
            boolean loaded = loadLevel(reader, levelPath);
        }
        levels.add(credits);

        LevelBase gameOver = new GameOver(game, canvas.getGraphicsContext2D());
        gameOver.initialize();

        levels.add(gameOver);

        gameOverIndex = levels.indexOf(gameOver);
    }

    private boolean loadLevel(ObjectSerializer reader, String fileName) {
        boolean error = false;
        String errorMessage = "";

        try {
            LevelMetaData levelMetaData = reader.read(Content.loadFile(fileName, ResourceType.LEVEL), LevelMetaData.class);

            Level level = new Level(game, canvas.getGraphicsContext2D(), levelMetaData);
            level.initialize();

            levels.add(level);
        } catch (SerializationException exception) {
            error = true;
            errorMessage = exception.getMessage();
        }

        if(error) {
            //Show alert here
            System.out.println("Error loading level" + errorMessage);
        }

        return error;
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



    // Save our current progress
    private void save() {
        PlayerProgress playerProgress = new PlayerProgress();
        playerProgress.save(new SaveData(getCurrentLevel().getId(), 0));
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
                    currentLevelIndex++;

                    // Loop around for now
                    if(currentLevelIndex >= levels.size()) {
                        currentLevelIndex = 0;
                    }

                    // Update to the next level
                    currentLevel = getCurrentLevel();
                    currentLevel.reset();

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
     * Stub method, this nothing in this controller.
     */
    @Override
    public void exit() {
    }
}