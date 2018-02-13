package com.groupname.game.Scene;

import com.groupname.game.controllers.MainWindowController;
import com.groupname.game.core.Game;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// Singleton - https://en.wikipedia.org/wiki/Singleton_pattern
public enum SceneManager {
    INSTANCE;

    private Stage primaryStage = null;
    private Map<SceneName, SceneInfo> scenes;

    private SceneInfo currentScene = null;

    private boolean initialized = false;

    SceneManager() {
        scenes = new HashMap<>();
        createSceneInfos();
    }

    // Better naming
    private void createSceneInfos() {
        SceneInfo titleSceneInfo = new SceneInfo(SceneName.Title, "Title - Untitled Game", "../views/titleview.fxml");
        SceneInfo gameSceneInfo = new SceneInfo(SceneName.Game, "Game - Untitled Game", "../views/mainwindow.fxml");

        scenes.put(SceneName.Title, titleSceneInfo);
        scenes.put(SceneName.Game, gameSceneInfo);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = Objects.requireNonNull(primaryStage, "primaryStage cannot be null");
        initialized = true;
    }

    public void changeToScene(SceneName newScene) {
        if(!initialized) {
            return;
        }

        Objects.requireNonNull(newScene);

        if(currentScene != null && currentScene.getSceneName() == newScene) {
            System.out.println("Scene is already loaded, use reset instead.");
            return;
        }

        // Just return if our scene has no definition yet, should probably throw an exception instead
        if(!scenes.containsKey(newScene)) {
            return;
        }

        SceneInfo sceneInfo = scenes.get(newScene);

        // Scene for that screen isn't loaded yet, so load it
        if(sceneInfo.getScene() == null) {
            // Redo this, maybe upcast the exception
            try {
                createScene(sceneInfo);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        // Show the scene
        showScene(sceneInfo);
    }

    private void showScene(SceneInfo sceneInfo) {
        currentScene = sceneInfo;

        primaryStage.setTitle(sceneInfo.getTitle());
        primaryStage.setScene(sceneInfo.getScene());
    }

    // Better exception handling
    private void createScene(SceneInfo sceneInfo) throws IOException {
        if(!initialized) {
            System.out.println("Error: The SceneManager has not been initialized");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneInfo.getViewPath()));
        Pane root = loader.load();

        Scene scene = new Scene(root);
        sceneInfo.setScene(scene);

        // This is a bit lazy, but fine for now, do something with generics
        if(sceneInfo.getSceneName() == SceneName.Game) {
            MainWindowController controller = loader.getController();

            Game game = new Game(root, scene, 640, 480);
            game.start();

            controller.init(game);
        }
        /* Don't need this yet because the other controllers has no models yet
        else if(sceneInfo.getSceneName() == SceneName.Title) {
            TitleController controller = loader.getController();
        }
        */

        showScene(sceneInfo);
    }


}
