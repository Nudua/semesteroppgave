package com.groupname.framework.graphics.drawing;

import com.groupname.framework.graphics.SpriteSheet;
import com.groupname.framework.math.Vector2D;
import javafx.scene.canvas.Canvas;
import org.junit.BeforeClass;
import org.junit.Test;
import test.util.MockFX;

import static org.junit.Assert.*;

public class SpriteBatchFXTests {

    /*
    @BeforeClass
    public static void init() {
        // For these tests we have to make sure that the Javafx thread is initialized
        Canvas canvas = new Canvas();
    }
    */

    // todo: Why does this suddenly not work (on windows)?

    @Test(expected = NullPointerException.class)
    public void ConstructorParameterGraphicsContextCannotBeNull() {
        new SpriteBatchFX(null);
    }

    @Test(expected = NullPointerException.class)
    public void drawSpriteParameterCannotBeNull() {
        Canvas canvas = new Canvas();
        SpriteBatch spriteBatch = new SpriteBatchFX(canvas.getGraphicsContext2D());
        spriteBatch.draw(null, new Vector2D());
    }

    /*
    @Test(expected = NullPointerException.class)
    public void drawPositionParameterCannotBeNull() {
        Canvas canvas = new Canvas();
        SpriteBatch spriteBatch = new SpriteBatchFX(canvas.getGraphicsContext2D());
        //spriteBatch.draw(null, new Vector2D());
    }
    */
}
