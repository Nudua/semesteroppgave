package com.groupname.game.controllers;

import com.groupname.framework.core.GameEngine;
import com.groupname.game.core.Game;

import java.util.Objects;

/*
interface Controller {
    void init(Game game);
}
*/

public class EditorController {

    private boolean initialized;
    private GameEngine game;

    public EditorController() {

    }

    public void init(GameEngine game) {
        this.game = Objects.requireNonNull(game);
        initialized = true;
    }


}
