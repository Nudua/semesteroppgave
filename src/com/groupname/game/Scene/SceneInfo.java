package com.groupname.game.scene;

import javafx.scene.Scene;

public class SceneInfo {

    private final SceneName sceneName;
    private final String title;
    private final String viewPath;
    private Runnable init;
    private Scene scene;

    public SceneInfo(SceneName sceneName, String title, String viewPath) {
        this(sceneName,title, viewPath, null);
    }

    public SceneInfo(SceneName sceneName, String title, String viewPath, Runnable init) {
        this.sceneName = sceneName;
        this.title = title;
        this.viewPath = viewPath;
        this.init = init;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public SceneName getSceneName() {
        return sceneName;
    }

    public String getViewPath() {
        return viewPath;
    }

    public Runnable getInit() {
        return init;
    }

    public void setInit(Runnable init) {
        this.init = init;
    }

    public String getTitle() {
        return title;
    }
}
