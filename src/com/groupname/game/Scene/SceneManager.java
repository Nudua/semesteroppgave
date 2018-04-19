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




public enum SceneManager {
    INSTANCE;

    private Stage primaryStage = null;
    private final Map<SceneName, SceneInfo> scenes;
    private boolean initialized;
    private Game game;

    SceneManager() {
        scenes = new HashMap<>();

        createSceneInfos();
    }

    private void setupGame(Scene scene) {
        assert scene != null;

        // Get width and height from canvas instead?
        game = new Game(scene, AppSettings.SCREEN_BOUNDS.getWidth(), AppSettings.SCREEN_BOUNDS.getHeight());
    }

    private void createSceneInfos() {
        SceneInfo title= new SceneInfo(SceneName.Title, "Title - Untitled Game", "/com/groupname/game/views/titleview.fxml");
        SceneInfo game = new SceneInfo(SceneName.Game, "Game - Untitled Game", "/com/groupname/game/views/gameview.fxml");
        SceneInfo editor = new SceneInfo(SceneName.Editor, "Level editor!", "/com/groupname/game/views/editorview.fxml");

        scenes.put(SceneName.Title, title);
        scenes.put(SceneName.Game, game);
        scenes.put(SceneName.Editor, editor);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = Objects.requireNonNull(primaryStage, "primaryStage cannot be null");
        initialized = true;
    }

    public static void navigate(SceneName sceneName) {
        INSTANCE.changeToScene(sceneName);
    }

    private void changeToScene(SceneName sceneName) {
        Objects.requireNonNull(sceneName);

        if(!scenes.containsKey(sceneName)) {
            throw new InvalidParameterException();
        }

        SceneInfo info = scenes.get(sceneName);

        FXMLLoader loader = new FXMLLoader(getClass().getResource(info.getViewPath()));

        Pane root;

        try {
            root = loader.load();

            Controller controller = loader.getController();

            Scene scene = new Scene(root);

            if(game == null) {
                setupGame(scene);
            } else {
                game.getInputManager().updateScene(scene);
            }

            controller.init(game);

            /*
            if(!game.isRunning()) {
                game.start();
            }
            */




            primaryStage.setTitle(info.getTitle());
            primaryStage.setScene(scene);

            // Change out the canvas we're drawing to in the game class

        } catch (IOException exception) { // throw other exception
            exception.printStackTrace();
        }
    }
}

/*
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
        SceneInfo gameSceneInfo = new SceneInfo(SceneName.Game, "Game - Untitled Game", "../views/gameview.fxml");
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
            System.out.println("scene is already loaded, use reset instead.");
            return;
        }

        // Just return if our scene has no definition yet, should probably throw an exception instead
        if(!scenes.containsKey(newScene)) {
            return;
        }

        SceneInfo sceneInfo = scenes.get(newScene);

        // scene for that screen isn't loaded yet, so load it
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
                GameController controller = loader.getController();

                Game game = new Game(root, 1280, 720);
                game.setTogglePauseMenu(controller::togglePauseMenu);
                sceneInfo.setInit(game::start);
                sceneInfo.setScene(game.getScene());
                game.getScene().getStylesheets().add("/com/groupname/game/views/css/title.css");


                controller.init(game);
            } else if(sceneInfo.getSceneName() == SceneName.Editor) {
                EditorController controller = loader.getController();

                GameEngine editor = new GameEditor(root);
                sceneInfo.setInit(editor::start);
                sceneInfo.setScene(editor.getScene());

                GridPane.setRowIndex(editor.getCanvas(), 2);
                //editor.getScene().getStylesheets().add("/com/groupname/game/views/css/editor.css");

                controller.init(editor);
            }
            else {
                scene scene = new scene(root);
                scene.getStylesheets().add("/com/groupname/game/views/css/title.css");
                sceneInfo.setScene(scene);
            }
        }

        //showScene(sceneInfo);
    }


}

*/
