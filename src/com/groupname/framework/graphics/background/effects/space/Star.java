package com.groupname.framework.graphics.background.effects.space;

import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.math.Size;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.data.AppSettings;

import java.util.List;
import java.util.Objects;

/**
 * Represents a single star that moves across the screen.
 *
 * It's speed is determined by it's Type.
 *
 */
public class Star extends GameObject {

    /**
     * The kind of types that this star can be.
     */
    public enum Type {
        SMALL,
        MEDIUM,
        BIG
    }

    private final static double SPEED_SLOW = 1.4d;
    private final static double SPEED_MEDIUM = 5.0d;
    private final static double SPEED_FAST = 20.0d;

    private Size screenBounds = AppSettings.SCREEN_BOUNDS;
    private Type starType;

    private final double speed;

    /**
     * Creates a new instance of this object with the specified sprite, position and Star.Type.
     * @param sprite the sprite used by this Star.
     * @param position the initial position by this Star.
     * @param starType the type of this Star.
     */
    public Star(Sprite sprite, Vector2D position, Star.Type starType) {
        super(sprite, position);

        this.starType = Objects.requireNonNull(starType);

        speed = getStarSpeedFromType();
    }

    private double getStarSpeedFromType() {
        switch(starType) {
            case SMALL:
                return SPEED_SLOW;
            case MEDIUM:
                return SPEED_MEDIUM;
            case BIG:
                return SPEED_FAST;
                default:
                    return SPEED_SLOW;
        }
    }

    /**
     * Moves the star further to the left.
     * Wraps around when it goes outside of the bounds of the screen.
     */
    @Override
    public void update() {
        // Just moving from right to left
        position.addX(-speed);

        if(position.getX() <= 0) {
            position.setX(screenBounds.getWidth() + sprite.getWidth());
        }
    }

    /**
     * Draws the current sprite associated with this gameobject.
     *
     * @param spriteBatch the spriteBatch used to draw this gameObject.
     */
    @Override
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(sprite, position);
    }

    /**
     * Returns the String representation of this object.
     *
     * @return the String representation of this object.
     */
    @Override
    public String toString() {
        return super.toString() + "Star{" +
                "screenBounds=" + screenBounds +
                ", starType=" + starType +
                ", speed=" + speed +
                '}';
    }

    /**
     * Helper method that gets the sprite associated with the star type.
     *
     * SMALL = index 0
     * MEDIUM = index 1
     * BIG = index 2
     *
     * @param starType the star type.
     * @param sprites the list of sprites used.
     * @return the sprite associated with the following Star.Type.
     */
    public static Sprite getSpriteFromType(Star.Type starType, List<Sprite> sprites) {
        switch(starType) {
            case SMALL:
                return sprites.get(0);
            case MEDIUM:
                return sprites.get(1);
            case BIG:
                return sprites.get(2);
            default:
                return sprites.get(0);
        }
    }
}