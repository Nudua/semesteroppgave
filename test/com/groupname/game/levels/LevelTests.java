package com.groupname.game.levels;

import com.groupname.game.core.Game;
import com.groupname.game.editor.metadata.LevelMetaData;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import org.junit.BeforeClass;
import org.junit.Test;
import test.util.MockFX;

public class LevelTests {

    @BeforeClass
    public static void init() {
        // For these tests we have to make sure that the Javafx thread is initialized
        MockFX.initFX();
    }

    private Scene scene = new Scene(new GridPane());

    @Test(expected = NullPointerException.class)
    public void parentParameterCannotBeNull() {
        Canvas canvas = new Canvas();
        new Level(null, canvas.getGraphicsContext2D(), new LevelMetaData("metaData"));
    }

    @Test(expected = NullPointerException.class)
    public void graphicsContextParameterCannotBeNull() {
        Game game = new Game(scene, 100, 100);
        new Level(game, null, new LevelMetaData("metaData"));
    }

    @Test(expected = NullPointerException.class)
    public void levelMetaDataCannotBeNull() {
        Game game = new Game(scene, 100, 100);
        Canvas canvas = new Canvas();
        new Level(game, canvas.getGraphicsContext2D(), null);
    }


}
