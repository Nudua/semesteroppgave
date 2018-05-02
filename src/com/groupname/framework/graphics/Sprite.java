package com.groupname.framework.graphics;

import com.groupname.framework.math.IntVector2D;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

/**
 * This class is a representation for a character in a game
 * using a region of a SpriteSheet as the backing for the Image
 * used to draw this class.
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
     * Return the scale value of the sprite.
     *
     * @return the scale value of the sprite.
     */
    public double getScale() {
        return scale;
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


    /**
     * Static helper method for creating a SpriteRegion (Rectangle) that is
     * generally used by the Sprite class and it's subclasses to define an
     * area of an image which is used for the sprite representation.
     *
     * @param width the width of the SpriteRegion (Rectangle).
     * @param height the width of the SpriteRegion (Rectangle).
     * @return a new Rectangle width the coordonates (0,0) and the specified width and height.
     */
    public static Rectangle createSpriteRegion(int width, int height) {
        return createSpriteRegion(0,0, width, height);
    }

    /**
     * Static helper method for creating a SpriteRegion (Rectangle) that is
     * generally used by the Sprite class and it's subclasses to define an
     * area of an image which is used for the sprite representation.
     *
     * @param smallX this value is multiplied by the sprite's width to give the actual coordinate within the image.
     * @param smallY this value is multiplied by the sprite's height to give the actual coordinate within the image.
     * @param width the width of the SpriteRegion (Rectangle).
     * @param height the width of the SpriteRegion (Rectangle).
     * @return a new Rectangle with the specified coordinates, width and height.
     */
    public static Rectangle createSpriteRegion(int smallX, int smallY, int width, int height) {
        return createSpriteRegion(smallX, smallY, width, height, new IntVector2D());
    }

    /**
     * Static helper method for creating a SpriteRegion (Rectangle) that is
     * generally used by the Sprite class and it's subclasses to define an
     * area of an image which is used for the sprite representation.
     *
     * @param smallX this value is multiplied by the sprite's width to give the actual coordinate within the image.
     * @param smallY this value is multiplied by the sprite's height to give the actual coordinate within the image.
     * @param width the width of the SpriteRegion (Rectangle).
     * @param height the width of the SpriteRegion (Rectangle).
     * @param offset the offset between each SpriteRegion in pixel size.
     * @return a new Rectangle with the specified coordinates, width, height and offset.
     */
    public static Rectangle createSpriteRegion(int smallX, int smallY, int width, int height, IntVector2D offset) {
        return new Rectangle(smallX * width + offset.getX(), smallY * height + offset.getY(), width, height);
    }
}
