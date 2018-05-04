package com.groupname.game.scene;

import com.groupname.framework.util.Strings;

/**
 * This immutable class holds the title and viewPath (fxml) for scenes used by this application.
 */
public class SceneInfo {

    private final String title;
    private final String viewPath;

    /**
     * Creates a new instance of this class with the specified title and viewPath to the fxml.
     * @param title the title to use for this info.
     * @param viewPath the viewPath to the fxml used by this sceneInfo.
     */
    public SceneInfo(String title, String viewPath) {
        this.title = title;
        this.viewPath = Strings.requireNonNullAndNotEmpty(viewPath);
    }

    /**
     * Returns the viewPath to the fxml file used by this info.
     *
     * @return the viewPath to the fxml file used by this info.
     */
    public String getViewPath() {
        return viewPath;
    }

    /**
     * Returns the title to the scene.
     *
     * @return the title to the scene.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the String representation of this object.
     *
     * @return the String representation of this object.
     */
    @Override
    public String toString() {
        return "SceneInfo{" +
                "title='" + title + '\'' +
                ", viewPath='" + viewPath + '\'' +
                '}';
    }
}
