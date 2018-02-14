package com.groupname.game.controllers;

import com.groupname.game.Scene.SceneManager;
import com.groupname.game.Scene.SceneName;
import com.groupname.game.core.Game;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.Objects;

public class MainWindowController {

    private Game game;

    public void init(Game game) {
        this.game = Objects.requireNonNull(game);
    }

    @FXML
    protected void menuNewGameOnClicked(ActionEvent event) {
        // Todo: Actually pause the game when we switch the scene out.
        SceneManager sceneManager = SceneManager.INSTANCE;
        sceneManager.changeToScene(SceneName.Title);
    }

    @FXML
    protected void GameOverOnClicked(ActionEvent event) {
        // Todo: Actually pause the game when we switch the scene out.
        SceneManager sceneManager = SceneManager.INSTANCE;
        sceneManager.changeToScene(SceneName.GameOver);
    }

    @FXML
    protected void menuExitOnClicked(ActionEvent event) {
        Platform.exit();
    }
}
