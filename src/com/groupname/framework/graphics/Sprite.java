package com.groupname.framework.graphics;

import com.groupname.framework.math.IntVector2D;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

/**
 *
 */
public class Sprite {

    private final SpriteSheet spriteSheet;
    private Rectangle spriteRegion;
    private double scale = 1.0d;

    /**
     * Constructs a new instance with the specified spriteSheet and spriteregion.
     *
     * @param spriteSheet the spritesheet to use as the source of the image for this sprite.
     * @param spriteRegion the region within the spritesheet to use for this sprite.
     * @throws NullPointerException if spriteSheet or spriteRegion are null.
     */
    public Sprite(SpriteSheet spriteSheet, Rectangle spriteRegion) {
        this.spriteSheet = Objects.requireNonNull(spriteSheet);
        this.spriteRegion = Objects.requireNonNull(spriteRegion);
    }

    /**
     * Sets the scale of the Sprite.
     *
     * @param scale any value above 0.0
     * @throws IllegalArgumentException if the scale value was less than or equal to 0.0 (double).
     */
    public void setScale(double scale) {
        if(scale <= 0.0d) {
            throw new IllegalArgumentException();
        }
        this.scale = scale;
    }

    /**
     * Gets the width of the Sprite, this value is multiplied by the current scale factor.
     *
     * @return the width of the sprite multiplied by the current scale factor.
     */
    public double getWidth() {
        return scale * spriteRegion.getWidth();
    }

    /**
     * Gets the height of the Sprite, this value is multiplied by the current scale factor.
     * @return the height of the sprite multiplied by the current scale factor.
     */
    public double getHeight() {
        return scale * spriteRegion.getHeight();
    }

    /**
     * Returns the spritesheet used by this instance.
     *
     * @return the spritesheet used by this instance.
     */
    public SpriteSheet getSpriteSheet() {
        return spriteSheet;
    }

    /**
     * Returns the spriteregion used by this instance.
     *
     * @return the spriteregion used by this instance.
     */
    public Rectangle getSpriteRegion() {
        return spriteRegion;
    }

    /**
     * Sets the spriteregion used by this sprite.
     *
     * @param spriteRegion the new spriteregion to set.
     * @throws NullPointerException if the specified spriteRegion parameter was null.
     */
    public void setSpriteRegion(Rectangle spriteRegion) {
        this.spriteRegion = Objects.requireNonNull(spriteRegion);
    }

    //todo: move somewhere?
    // Sort this out
    public static Rectangle createSpriteRegion(int width, int height) {
        return createSpriteRegion(0,0, width, height);
    }
    // Do something about this
    public static Rectangle createSpriteRegion(int smallX, int smallY, int width, int height) {
        return createSpriteRegion(smallX, smallY, width, height, new IntVector2D());
    }

    public static Rectangle createSpriteRegion(int smallX, int smallY, int width, int height, IntVector2D offset) {
        return new Rectangle(smallX * width + offset.getX(), smallY * height + offset.getY(), width, height);
    }
}
