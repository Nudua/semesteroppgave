package com.groupname.game.controllers;

import org.junit.Test;
import static org.junit.Assert.*;

public class EditorControllerTests {

    @Test(expected = NullPointerException.class)
    public void gameCannotBeNull() {
        EditorController controller = new EditorController();
        controller.init(null, null);
    }

}
