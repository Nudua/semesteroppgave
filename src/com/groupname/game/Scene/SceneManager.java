package com.groupname.game.Scene;

import com.groupname.game.controllers.MainWindowController;
import com.groupname.game.core.Game;
import com.groupname.game.core.TestGame;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
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
        SceneInfo testGameSceneInfo = new SceneInfo(SceneName.GameOver, "Game - Test", "");

        scenes.put(SceneName.Title, titleSceneInfo);
        scenes.put(SceneName.Game, gameSceneInfo);
        scenes.put(SceneName.GameOver, testGameSceneInfo);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = Objects.requireNonNull(primaryStage, "primaryStage cannot be null");
        initialized = true;
    }

    // Add a navigate to and from action, i.e to pause and resume the game
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

    TestGame testGame;
    // Better exception handling
    private void createScene(SceneInfo sceneInfo) throws IOException {
        if(!initialized) {
            System.out.println("Error: The SceneManager has not been initialized");
            return;
        }

        Pane root;

        // Game without a fxml file and controller, just for testing
        if("".equals(sceneInfo.getViewPath())) {
            root = new GridPane();

            Scene scene = new Scene(root);
            sceneInfo.setScene(scene);

            if(sceneInfo.getSceneName() == SceneName.GameOver) {
                testGame = new TestGame(root, scene, 1280, 720);
                testGame.start();
            }



        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneInfo.getViewPath()));
            root = loader.load();

            Scene scene = new Scene(root);
            sceneInfo.setScene(scene);

            // This is a bit lazy, but fine for now, do something with generics
            if(sceneInfo.getSceneName() == SceneName.Game) {
                MainWindowController controller = loader.getController();

                Game game = new Game(root, scene, 1280, 720);
                game.start();

                controller.init(game);
            }
        }




        showScene(sceneInfo);
    }


}
