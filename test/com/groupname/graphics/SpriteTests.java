package com.groupname.graphics;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.util.EmptyStringException;
import javafx.scene.shape.Rectangle;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class SpriteTests {

    private final String name = "sprite1";
    private final String spriteSheet = "spritesheet1";
    private final Rectangle testSpriteRegion = new Rectangle(64,64);

    /*
    // Constructor parameter tests
    @Test(expected = NullPointerException.class)
    public void NameParameterCannotBeNull() {
        Sprite sprite = new Sprite(null, spriteSheet, testSpriteRegion);
    }

    @Test(expected = EmptyStringException.class)
    public void NameParameterCannotBeEmpty() {
        Sprite sprite = new Sprite("", spriteSheet, testSpriteRegion);
    }

    @Test(expected = NullPointerException.class)
    public void SpriteSheetParameterCannotBeNull() {
        Sprite sprite = new Sprite(name, null, testSpriteRegion);
    }

    @Test(expected = EmptyStringException.class)
    public void SpriteSheetParameterCannotBeEmpty() {
        Sprite sprite = new Sprite(name,"", testSpriteRegion);
    }

    @Test(expected = NullPointerException.class)
    public void SpriteRegionParameterCannotBeNull() {
        Sprite sprite = new Sprite(name, spriteSheet, null);
    }

    // Methods
    @Test
    public void ReturnsCorrectWidth() {
        Sprite sprite = new Sprite(name, spriteSheet, testSpriteRegion);
        assertTrue(sprite.getWidth() == testSpriteRegion.getWidth());
    }

    @Test
    public void ReturnsCorrectHeight() {
        Sprite sprite = new Sprite(name, spriteSheet, testSpriteRegion);
        assertTrue(sprite.getHeight() == testSpriteRegion.getHeight());
    }
    */
}
