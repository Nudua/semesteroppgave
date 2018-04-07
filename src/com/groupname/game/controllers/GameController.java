package com.groupname.game.controllers;

import com.groupname.framework.core.PauseButton;
import com.groupname.framework.input.InputManager;
import com.groupname.game.Scene.SceneManager;
import com.groupname.game.Scene.SceneName;
import com.groupname.game.core.Game;
import com.groupname.game.input.PlayerInputDefinitions;
import com.groupname.game.levels.Level1;
import com.groupname.game.levels.Title;
import com.groupname.game.levels.core.LevelBase;
import com.groupname.game.views.menus.GameMenuFX;
import com.groupname.game.views.menus.TitleMenuNames;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class GameController implements Controller {
    @FXML protected GridPane root;
    @FXML protected Canvas canvas;

    private Game game;

    private GameMenuFX<PauseButton> pauseMenu;
    private LevelBase level1;

    private boolean isPaused = false;

    // Maybe move into constructor instead
    public void init(Game game) {
        this.game = Objects.requireNonNull(game);

        game.initialize(canvas, this::update, this::draw);

        level1 = new Level1(game, canvas.getGraphicsContext2D());
        level1.initialize();

        if(!game.isRunning()) {
            game.start();
        }

        setupMenu();
    }

    private void setupMenu() {
        pauseMenu = new GameMenuFX<>(PauseButton.class, "/com/groupname/game/views/menus/pausemenu.fxml");

        pauseMenu.setOnClicked(PauseButton.Resume, this::unPause);
        pauseMenu.setOnClicked(PauseButton.MainMenu, () -> SceneManager.navigate(SceneName.Title));

        root.getChildren().add(pauseMenu);

        unPause();
    }

    private void update(InputManager inputManager) {
        inputManager.update();

        if(isPaused) {
            pauseMenu.update(inputManager);

        } else {
            level1.update();

            if(inputManager.wasPressed(PlayerInputDefinitions.START) || inputManager.wasPressed(PlayerInputDefinitions.SELECT)) {
                pause();
            }
        }
    }

    private void pause() {
        Platform.runLater(() -> pauseMenu.focusButton(PauseButton.Resume));

        isPaused = true;
        pauseMenu.setVisible(true);
        canvas.setOpacity(0.5d);
    }

    private void unPause() {
        isPaused = false;
        pauseMenu.setVisible(false);
        canvas.setOpacity(1);
    }

    private void draw() {
        level1.draw();
    }
}