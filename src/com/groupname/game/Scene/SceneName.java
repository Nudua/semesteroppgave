package com.groupname.game.scene;

/**
 * Represents the different scenes within the application.
 * Used to navigate between them using the SceneManager singleton.
 *
 * To navigate to another scene u should use the
 * SceneManager.INSTANCE.navigate method with the specified SceneName.
 */
public enum SceneName {
    NONE,
    TITLE,
    GAME,
    CREDITS,
    EDITOR
}
