package com.groupname.game;

import com.groupname.framework.io.Content;
import com.groupname.game.Scene.SceneManager;
import com.groupname.game.Scene.SceneName;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // We have to set this before running
        Content.setContentBaseFolder("/com/groupname/game/resources");

        SceneManager sceneManager = SceneManager.INSTANCE;

        sceneManager.setPrimaryStage(primaryStage);
        SceneManager.navigate(SceneName.Title);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
