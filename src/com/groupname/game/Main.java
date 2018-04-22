package com.groupname.game;

import com.groupname.framework.io.Content;
import com.groupname.game.scene.SceneManager;
import com.groupname.game.scene.SceneName;
import com.groupname.game.data.AppSettings;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // We have to set this before running
        Content.setContentBaseFolder("/com/groupname/game/resources");

        SceneManager sceneManager = SceneManager.INSTANCE;

        sceneManager.setPrimaryStage(primaryStage);
        SceneManager.navigate(SceneName.Title);

        AppSettings settings = AppSettings.INSTANCE;

        try {
            settings.load();
        } catch (IOException exception) {
            System.err.println("Unable to load settings, restoring defaults");
        }

        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        AppSettings settings = AppSettings.INSTANCE;

        System.out.println("Saving settings...");

        try {
            settings.save();
            System.out.println("Settings saved successfully...");
        } catch (IOException ex) {
            System.err.println("Unable to store settings..");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


}
