package com.groupname.game;

import com.groupname.game.Scene.SceneManager;
import com.groupname.game.Scene.SceneName;
import com.groupname.game.controllers.MainWindowController;
import com.groupname.game.core.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        SceneManager sceneManager = SceneManager.INSTANCE;

        sceneManager.setPrimaryStage(primaryStage);
        sceneManager.changeToScene(SceneName.Title);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
