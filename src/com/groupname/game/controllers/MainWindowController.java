package com.groupname.game.controllers;

import com.groupname.game.Scene.SceneManager;
import com.groupname.game.Scene.SceneName;
import com.groupname.game.core.Game;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class MainWindowController {

    private Game game;

    public void init(Game game) {
        this.game = Objects.requireNonNull(game);
    }

    @FXML
    private VBox pausedMenu;

    @FXML
    protected void menuExitToHomeOnClicked(ActionEvent event) {
        game.stop();

        SceneManager sceneManager = SceneManager.INSTANCE;
        sceneManager.changeToScene(SceneName.Title);
    }

    @FXML
    protected void saveGameOnClicked(ActionEvent event) {
        System.out.println("Save game: check");
    }

    @FXML
    protected void restartLevelOnClicked(ActionEvent event) {
        System.out.println("Restart level: check");
    }

    @FXML
    protected void restartGameOnClicked(ActionEvent event) {
        System.out.println("Restart game: check");
    }

    @FXML
    protected void resumeOnClicked(ActionEvent event) {
        game.setPaused(!game.isPaused());
        boolean paused = game.isPaused();

        pausedMenu.setVisible(paused);
        if(paused) {
            game.getCanvas().setOpacity(0.5);
        } else {
            game.getCanvas().setOpacity(1);
        }
    }
}
