package com.groupname.framework.graphics;

import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import com.groupname.framework.math.IntVector2D;
import javafx.scene.shape.Rectangle;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SpriteTests {

    private final SpriteSheet spriteSheet = getValidSpriteSheet();
    private final Rectangle spriteRegion = new Rectangle(64,64);

    // Helper methods
    private SpriteSheet getValidSpriteSheet() {
        return new SpriteSheet("testSheet", Content.loadImage("projectiles.png", ResourceType.SPRITE_SHEET));
    }

    private Sprite getValidSprite() {
        return new Sprite(spriteSheet, spriteRegion);
    }

    @BeforeClass
    public static void init() {
        Content.setContentBaseFolder("/com/groupname/game/resources");
    }

    // Constructor parameter tests

    @Test(expected = NullPointerException.class)
    public void spriteSheetParameterCannotBeNull() {
        new Sprite(null, spriteRegion);
    }

    @Test(expected = NullPointerException.class)
    public void spriteRegionParameterCannotBeNull() {
        new Sprite(spriteSheet, null);
    }

    // Getters
    @Test
    public void returnsCorrectWidth() {
        Sprite sprite = new Sprite(spriteSheet, spriteRegion);
        assertEquals(sprite.getWidth(), spriteRegion.getWidth(), 0.0);
    }

    @Test
    public void returnsCorrectHeight() {
        Sprite sprite = new Sprite(spriteSheet, spriteRegion);
        assertEquals(sprite.getHeight(), spriteRegion.getHeight(), 0.0);
    }

    @Test
    public void setScaleAffectsWidthAndHeight() {
        Sprite sprite = getValidSprite();
        assertNotNull(sprite);

        double originalWidth = sprite.getWidth();
        double originalHeight = sprite.getHeight();

        double scale = 3.0;

        sprite.setScale(scale);

        double newWidth = scale * originalWidth;
        double newHeight = scale * originalHeight;

        assertEquals(newWidth, sprite.getWidth(), 0.0d);
        assertEquals(newHeight, sprite.getHeight(), 0.0d);
    }

    @Test
    public void getSpriteSheetGivesCorrectValue() {
        Sprite sprite = getValidSprite();

        assertSame(sprite.getSpriteSheet(), spriteSheet);
    }

    @Test
    public void getSpriteRegionGivesCorrectValue() {
        Sprite sprite = getValidSprite();

        assertSame(sprite.getSpriteRegion(), spriteRegion);
    }

    @Test
    public void setSpriteRegionUpdatesSpriteRegion() {
        Sprite sprite = getValidSprite();

        assertSame(sprite.getSpriteRegion(), spriteRegion);

        Rectangle newRegion = new Rectangle(0,0,200,400);
        assertNotNull(newRegion);

        sprite.setSpriteRegion(newRegion);

        assertNotSame(sprite.getSpriteRegion(), spriteRegion);
        assertSame(sprite.getSpriteRegion(), newRegion);
    }

    // Static methods
    // Look into this one more
    @Test
    public void createSpriteRegionGivesCorrectRegion() {
        int width = 200;
        int height = 200;

        int x = 23;
        int y = 44;

        Rectangle region = Sprite.createSpriteRegion(width, height);
        validateRectangle(region, width, height, 0, 0);

        Rectangle region2 = Sprite.createSpriteRegion(x, y, width, height);
        validateRectangle(region2, width, height, x * width, y * height);

        IntVector2D offset = new IntVector2D(20,40);
        Rectangle region3 = Sprite.createSpriteRegion(x, y, width, height, offset);
        validateRectangle(region3, width, height, x * width + (int)offset.getX(), y * height + (int)offset.getY());
    }

    private void validateRectangle(Rectangle region, int width, int height, int x, int y) {
        assertEquals((int)region.getWidth(), width);
        assertEquals((int)region.getHeight(), height);
        assertEquals((int)region.getX(), x);
        assertEquals((int)region.getY(), y);
    }

}
