package com.groupname.game.Scene;

import com.groupname.framework.core.GameEngine;
import com.groupname.game.controllers.EditorController;
import com.groupname.game.controllers.MainWindowController;
import com.groupname.game.core.Game;
import com.groupname.game.core.GameEditor;
import com.groupname.game.levels.GameOver;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

// Singleton - https://en.wikipedia.org/wiki/Singleton_pattern
public enum SceneManager {
    INSTANCE;

    private Stage primaryStage = null;
    private final Map<SceneName, SceneInfo> scenes;

    private SceneInfo currentScene = null;

    private boolean initialized = false;

    //private final List<GameEngine> gameScreens;

    SceneManager() {
        scenes = new HashMap<>();
        //gameScreens = new ArrayList<>();
        createSceneInfos();
    }

    // Better naming
    private void createSceneInfos() {
        SceneInfo titleSceneInfo = new SceneInfo(SceneName.Title, "Title - Untitled Game", "../views/titleview.fxml");
        SceneInfo gameSceneInfo = new SceneInfo(SceneName.Game, "Game - Untitled Game", "../views/mainwindow.fxml");
        SceneInfo testGameSceneInfo = new SceneInfo(SceneName.GameOver, "Game - Test", "");
        SceneInfo creditsSceneInfo = new SceneInfo(SceneName.Credits, "Game - Credits", "");

        SceneInfo editorSceneInfo = new SceneInfo(SceneName.Editor, "Level editor!", "../views/editorview.fxml");

        scenes.put(SceneName.Title, titleSceneInfo);
        scenes.put(SceneName.Game, gameSceneInfo);
        scenes.put(SceneName.GameOver, testGameSceneInfo);
        scenes.put(SceneName.Credits, creditsSceneInfo);
        scenes.put(SceneName.Editor, editorSceneInfo);
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

        if(currentScene.getInit() != null) {
            currentScene.getInit().run();
        }

        primaryStage.setTitle(sceneInfo.getTitle());
        primaryStage.setScene(sceneInfo.getScene());
    }

    //private GameOver gameOverScreen;
    //private Credits creditsScreen;

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

        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneInfo.getViewPath()));
            root = loader.load();

            // This is a bit lazy, but fine for now, do something with generics
            if(sceneInfo.getSceneName() == SceneName.Game) {
                MainWindowController controller = loader.getController();

                Game game = new Game(root, 1280, 720);
                sceneInfo.setInit(game::start);
                sceneInfo.setScene(game.getScene());

                controller.init(game);
            } else if(sceneInfo.getSceneName() == SceneName.Editor) {
                EditorController controller = loader.getController();

                GameEngine editor = new GameEditor(root);
                sceneInfo.setInit(editor::start);
                sceneInfo.setScene(editor.getScene());

                controller.init(editor);
            }
            else {
                Scene scene = new Scene(root);
                scene.getStylesheets().add("/com/groupname/game/views/css/title.css");
                sceneInfo.setScene(scene);
            }
        }

        //showScene(sceneInfo);
    }


}
