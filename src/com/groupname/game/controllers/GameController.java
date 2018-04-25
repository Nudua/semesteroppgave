package com.groupname.game.controllers;

import com.groupname.framework.core.GameObject;
import com.groupname.framework.core.PauseButton;
import com.groupname.framework.graphics.background.transitions.BlindsScreenTransition;
import com.groupname.framework.graphics.background.transitions.ScreenTransition;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import com.groupname.framework.util.Strings;
import com.groupname.game.entities.Player;
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

import java.io.IOException;
import java.util.*;

public class GameController implements Controller {
    @FXML protected GridPane root;
    @FXML protected Canvas canvas;
    @FXML protected Button pauseButton;

    private Game game;

    private GameMenuFX<PauseButton> pauseMenu;
    //private LevelBase currentLevel;

    private List<LevelBase> levels = new ArrayList<>();

    private LevelBase credits;
    private boolean isPaused = false;
    private int currentLevelIndex = 0;

    private int creditsIndex = -1;
    private int gameOverIndex = -1;

    private ScreenTransition levelCompletedTransition;

    // Maybe move into constructor instead
    public void init(Game game) {
        this.game = Objects.requireNonNull(game);

        game.initialize(canvas, this::update, this::draw);

        //currentLevel = new Level1(game, canvas.getGraphicsContext2D());
        //currentLevel.initialize();
        loadLevels();

        String levelId = AppSettings.INSTANCE.getCurrentLevel();

        if(!Strings.isNullOrEmpty(levelId)) {
            Optional<LevelBase> level = levels.stream().filter(n -> n.getId().equals(levelId)).findFirst();

            if(level.isPresent()) {
                int index = levels.indexOf(level.get());
                currentLevelIndex = index;
                System.out.println("Restoring from level: " + currentLevelIndex);
            }
        }

        LevelBase currentLevel = getCurrentLevel();

        if(currentLevel instanceof Level) {
            ((Level) currentLevel).setOnPlayerDead(() ->{
                System.out.println("Dave, everybody's dead... everybody's dead Dave");
                getCurrentLevel().reset();
            });
        }

        GameOver gameOver = new GameOver(game, canvas.getGraphicsContext2D());
        gameOver.initialize();
        levels.add(0, new Level1(game, canvas.getGraphicsContext2D()));
        //levels.add(0, gameOver);
        levelCompletedTransition = new BlindsScreenTransition(canvas.getGraphicsContext2D());

        if(!game.isRunning()) {
            game.start();
        }

        setupMenu();
    }

    private LevelBase getCurrentLevel() {
        return levels.get(currentLevelIndex);
    }

    private void loadLevels() {
        credits = new Credits(game, canvas.getGraphicsContext2D());
        credits.initialize();

        LevelReader reader = new LevelReader();

        String[] levelFiles = {"level1.level"};

        for(String levelPath: levelFiles) {
            boolean loaded = loadLevel(reader, levelPath);
        }
        levels.add(credits);
    }

    private boolean loadLevel(LevelReader reader, String fileName) {
        boolean error = false;
        String errorMessage = "";

        try {
            LevelMetaData levelMetaData = reader.read(Content.loadFile(fileName, ResourceType.Level));

            Level level = new Level(game, canvas.getGraphicsContext2D(), levelMetaData);
            level.initialize();

            levels.add(level);
        } catch (IOException exception) {
            error = true;
            errorMessage = exception.getMessage();
        } catch (ClassNotFoundException exception) {
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
        pauseMenu.setOnClicked(PauseButton.MainMenu, () -> SceneManager.navigate(SceneName.Title));
        pauseMenu.setOnClicked(PauseButton.RestartLevel, () -> {
            getCurrentLevel().reset();
            unPause();
        });
        pauseMenu.setOnClicked(PauseButton.RestartGame, () -> {
            currentLevelIndex = 0;
            LevelBase currentLevel = getCurrentLevel();
            currentLevel.reset();
            if(currentLevel instanceof Level) {
                ((Level) currentLevel).setOnPlayerDead(() ->{
                    System.out.println("Dave, everybody's dead... everybody's dead Dave");
                    getCurrentLevel().reset();
                });
            }
            unPause();
        });
        pauseMenu.setOnClicked(PauseButton.Save, this::save);
        //pauseMenu.setButtonEnabled(PauseButton.Save, false);

        root.getChildren().add(pauseMenu);

        unPause();
    }

    private void save() {
        AppSettings appSettings = AppSettings.INSTANCE;

        appSettings.setCurrentLevel(getCurrentLevel().getId());
        try {
            appSettings.save();
        } catch (IOException ex) {
            System.out.println("Unable to store current level");
        }
    }

    private void update(InputManager inputManager) {
        inputManager.update();

        if(isPaused) {
            pauseMenu.update(inputManager);

        } else {
            LevelBase currentLevel = getCurrentLevel();

            if(currentLevel.getState() == LevelState.Completed) {

                if(levelCompletedTransition.isDone()) {
                    currentLevelIndex++;

                    // Loop around for now
                    if(currentLevelIndex >= levels.size()) {
                        currentLevelIndex = 0;
                    }

                    // Update to the next level
                    currentLevel = getCurrentLevel();
                    currentLevel.reset();

                    if(currentLevel instanceof Level) {
                        ((Level) currentLevel).setOnPlayerDead(() ->{
                            System.out.println("Dave, everybody's dead... everybody's dead Dave");
                            getCurrentLevel().reset();
                        });
                    }

                    levelCompletedTransition.reset();
                } else {
                    levelCompletedTransition.update();
                }

            } else if(currentLevel.getState() == LevelState.GameOver) {

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

        if(currentLevel.getState() == LevelState.Playing) {
           currentLevel.draw();
        } else if(currentLevel.getState() == LevelState.Completed && !levelCompletedTransition.isDone()) {
            levelCompletedTransition.draw();
        }
    }

    @Override
    public void exiting() {

    }
}