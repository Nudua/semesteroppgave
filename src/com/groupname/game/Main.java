package com.groupname.game;

import com.groupname.framework.io.Content;
import com.groupname.game.Scene.SceneManager;
import com.groupname.game.Scene.SceneName;
import com.groupname.game.data.AppSettings;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // We have to set this before running
        Content.setContentBaseFolder("/com/groupname/game/resources");

        SceneManager sceneManager = SceneManager.INSTANCE;

        sceneManager.setPrimaryStage(primaryStage);
        SceneManager.navigate(SceneName.Title);

        //Files.createDirectory(Paths.get("saveFolder"));
        /*
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s);
        */
        AppSettings settings = AppSettings.INSTANCE;
        settings.load();

        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        AppSettings settings = AppSettings.INSTANCE;
        settings.save();
        System.out.println("Saving settings...");
    }

    public static void main(String[] args) {
        launch(args);
    }


}
