package com.groupname.game.controllers;

import org.junit.Test;

public class GameControllerTests {

    @Test(expected = NullPointerException.class)
    public void gameCannotBeNull() {
        GameController controller = new GameController();
        controller.init(null, null);
    }

}
