package com.groupname.framework.graphics.background.effects.weather;

import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.math.Size;
import com.groupname.framework.math.Vector2D;

/**
 * This class represents a single flake blowing in the wind.
 */
public class WindFlake extends GameObject {

    public static final double MAX_WIND_SPEED = 7.0d;
    public static final double MIN_WIND_SPEED = 1.0d;
    private static final double FALL_SPEED = 0.2d;

    private Size screenBounds;
    private double speed;

    private int stallTimer;

    /**
     * Creates a new instance of this flake.
     *
     * @param sprite the sprite used by this instance.
     * @param position the starting position of this flake.
     * @param screenBounds the bounds of the screen on which this flake is drawed on.
     * @param stallTimer the time to stall before it begins moving for the first time.
     * @param speed the speed which the flake moves.
     */
    public WindFlake(Sprite sprite, Vector2D position, Size screenBounds, int stallTimer, double speed) {
        super(sprite, position);
        this.screenBounds = screenBounds;
        this.stallTimer = stallTimer;
        this.speed = speed;
    }

    /**
     * Updates any logic required to move the flake.
     */
    @Override
    public void update() {

        if(stallTimer > 0) {
            stallTimer--;
            return;
        }

        position.addX(-speed);

        // Wrap around on the x-axis.
        if(position.getX() <= 0) {
            position.setX(screenBounds.getWidth() + sprite.getWidth());
        }

        position.addY(FALL_SPEED);

        // Wrap around on the y-axis.
        if(position.getY() >= screenBounds.getHeight()) {
            position.setY(position.getY() - screenBounds.getHeight());
        }
    }

    /**
     * Draws the flake.
     *
     * @param spriteBatch the spriteBatch used to draw this gameObject.
     */
    @Override
    public void draw(SpriteBatch spriteBatch) {
        if(stallTimer > 0) {
            return;
        }

        spriteBatch.draw(sprite, position);
    }

    /**
     * Returns the String representation of this object.
     *
     * @return the String representation of this object.
     */
    @Override
    public String toString() {
        return super.toString() +
                "WindFlake{" +
                "screenBounds=" + screenBounds +
                ", speed=" + speed +
                ", stallTimer=" + stallTimer +
                '}';
    }
}