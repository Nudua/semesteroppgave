package com.groupname.game.controllers;

import com.groupname.game.core.Game;
import javafx.scene.canvas.Canvas;

/**
 * This interface is meant to be used to connect fxml (views)
 * with the specified game model
 */
public interface Controller {
    /**
     * Implementations must override this method to use it to initialize it with the specified game.
     *
     * @param game the game instance to use for this controller.
     * @param parameters optional parameters that you want to pass to the controller when initializing.
     */
    void init(Game game, Object parameters);

    /**
     * Implementations must use this method to clean up any running threads or other resources that needs cleaning up.
     *
     * This method should always be called when navigating away from this controller.
     */
    void exit();
}
