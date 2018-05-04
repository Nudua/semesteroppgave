package com.groupname.game.levels;

import com.groupname.game.core.Game;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import org.junit.BeforeClass;
import org.junit.Test;
import test.util.MockFX;

public class CreditsTests {

    @BeforeClass
    public static void init() {
        // For these tests we have to make sure that the Javafx thread is initialized
        MockFX.initFX();
    }

    private Scene scene = new Scene(new GridPane());

    @Test(expected = NullPointerException.class)
    public void parentParameterCannotBeNull() {
        Canvas canvas = new Canvas();
        new Credits(null, canvas.getGraphicsContext2D());
    }

    @Test(expected = NullPointerException.class)
    public void graphicsContextParameterCannotBeNull() {
        Game game = new Game(scene, 100, 100);
        new Credits(game, null);
    }
}
