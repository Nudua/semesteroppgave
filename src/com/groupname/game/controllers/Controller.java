package com.groupname.game.controllers;

import com.groupname.game.core.Game;
import javafx.scene.canvas.Canvas;

/**
 * Controller interface that connects the view and the game logic.
 */
public interface Controller {
    void init(Game game);
    void exit();
}
