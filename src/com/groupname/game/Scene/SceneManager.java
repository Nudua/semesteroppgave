package com.groupname.game.scene;

import com.groupname.game.controllers.Controller;
import com.groupname.game.core.Game;
import com.groupname.game.data.AppSettings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.*;


/**
 * This enum singleton is used to load and switch between different scenes inside the application.
 *
 * setPrimaryStage must be set before using the navigate method.
 */
public enum SceneManager {
    INSTANCE;

    private Stage primaryStage = null;
    private final Map<SceneName, SceneInfo> scenes;
    private boolean initialized;

    private Game game;
    private Controller currentController;

    SceneManager() {
        scenes = new HashMap<>();

        createSceneInfos();
    }

    private void setupGame(Scene scene) {
        assert scene != null;

        game = new Game(scene, AppSettings.SCREEN_BOUNDS.getWidth(), AppSettings.SCREEN_BOUNDS.getHeight());
    }

    private void createSceneInfos() {
        SceneInfo title= new SceneInfo(SceneName.TITLE, "TITLE - Untitled GAME", "/com/groupname/game/views/titleview.fxml");
        SceneInfo game = new SceneInfo(SceneName.GAME, "GAME - Untitled GAME", "/com/groupname/game/views/gameview.fxml");
        SceneInfo editor = new SceneInfo(SceneName.EDITOR, "Level editor!", "/com/groupname/game/views/editorview.fxml");

        scenes.put(SceneName.TITLE, title);
        scenes.put(SceneName.GAME, game);
        scenes.put(SceneName.EDITOR, editor);
    }

    /**
     * Sets the primaryStage that is used to change between scenes.
     *
     * @param primaryStage to use for changing between scenes.
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = Objects.requireNonNull(primaryStage, "primaryStage cannot be null");

        primaryStage.setOnCloseRequest((windowEvent) -> {
            if(currentController != null) {
                System.out.println("Closing window");
                currentController.exit();
            }
            if(game != null) {
                game.getInputManager().stop();
            }
        });

        initialized = true;
    }

    /**
     * Attempts to navigate to the specified sceneName.
     *
     * @param sceneName to navigate to.
     */
    public static void navigate(SceneName sceneName) {
        INSTANCE.changeToScene(sceneName);
    }

    private void changeToScene(SceneName sceneName) {
        Objects.requireNonNull(sceneName);

        if(!scenes.containsKey(sceneName)) {
            throw new InvalidParameterException();
        }

        // Clean up our old controller if needed
        if(currentController != null) {
            currentController.exit();
        }

        SceneInfo info = scenes.get(sceneName);

        FXMLLoader loader = new FXMLLoader(getClass().getResource(info.getViewPath()));

        Pane root;

        try {
            root = loader.load();

            currentController = loader.getController();

            Scene scene = new Scene(root);

            if(game == null) {
                setupGame(scene);
            } else {
                game.getInputManager().updateScene(scene);
            }

            currentController.init(game);

            primaryStage.setTitle(info.getTitle());
            primaryStage.setScene(scene);

        } catch (IOException exception) { // throw other exception
            exception.printStackTrace();
        }
    }
}
