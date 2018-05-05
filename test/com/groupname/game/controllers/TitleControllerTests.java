package com.groupname.game.controllers;

import org.junit.Test;
import static org.junit.Assert.*;

public class TitleControllerTests {

    @Test(expected = NullPointerException.class)
    public void gameCannotBeNull() {
        TitleController controller = new TitleController();
        controller.init(null);
    }
}
