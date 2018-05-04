package com.groupname.game.core;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import org.junit.BeforeClass;
import org.junit.Test;
import test.util.MockFX;

import static org.junit.Assert.*;

public class GameTests {

    @BeforeClass
    public static void init() {
        // For these tests we have to make sure that the Javafx thread is initialized
        MockFX.initFX();
    }

    private Scene scene = new Scene(new GridPane());

    @Test(expected = NullPointerException.class)
    public void sceneCannotBeNull() {
        new Game(null, 100, 100);
    }

    @Test(expected = NullPointerException.class)
    public void canvasCannotBeNull() {
        Game game = new Game(scene, 100, 100);

        game.initialize(null, null, null);
    }

    @Test
    public void inputManagerGetsCreated() {
        Game game = new Game(scene, 100, 100);

        game.initialize(new Canvas(), null, null);

        assertNotNull(game.getInputManager());
    }
}
