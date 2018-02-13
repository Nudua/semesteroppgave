package com.groupname.game.controllers;

import com.groupname.game.Scene.SceneManager;
import com.groupname.game.Scene.SceneName;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class TitleController {

    @FXML
    protected void navigateToGameOnClick(ActionEvent event) {
        SceneManager sceneManager = SceneManager.INSTANCE;
        sceneManager.changeToScene(SceneName.Game);
    }
}
