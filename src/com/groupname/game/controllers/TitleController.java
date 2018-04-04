package com.groupname.game.controllers;

import com.groupname.game.Scene.SceneManager;
import com.groupname.game.Scene.SceneName;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class TitleController {

    @FXML
    protected void navigateToEditorOnClick(ActionEvent event) {
        SceneManager sceneManager = SceneManager.INSTANCE;
        sceneManager.changeToScene(SceneName.Editor);
    }

    @FXML
    protected void navigateToGameOnClick(ActionEvent event) {
        SceneManager sceneManager = SceneManager.INSTANCE;
        sceneManager.changeToScene(SceneName.Game);
    }


    @FXML
    protected void exitGameOnClick(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    protected void navigateToCreditsOnClick(ActionEvent event) {
        SceneManager sceneManager = SceneManager.INSTANCE;
        sceneManager.changeToScene(SceneName.Credits);
    }
}
