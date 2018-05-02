package com.groupname.game.entities.projectiles;

import com.groupname.framework.collision.BoundsChecker;
import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.animation.AnimatedSprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.data.AppSettings;
import javafx.scene.shape.Rectangle;

/**
 * This class represents a simple projectile.
 */
public class Projectile extends GameObject {
    private boolean alive = false;
    private static final Rectangle SCREEN_BOUNDS = new Rectangle(AppSettings.SCREEN_BOUNDS.getWidth(), AppSettings.SCREEN_BOUNDS.getHeight());
    private final BoundsChecker boundsChecker;

    /**
     * Creates a new instance with the specified sprite.
     *
     * @param sprite the sprite to use for this object.
     */
    public Projectile(Sprite sprite) {
        super(sprite, new Vector2D());
        boundsChecker = new BoundsChecker();
    }

    /**
     * Returns whether this projectile is alive or not.
     *
     * @return true if this projectile is still alive.
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Sets whether this projectile is alive.
     *
     * @param alive a boolean value that says whether this projectile is alive or not.
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * If this projectile is still alive, the animation will be stepped
     * and the projective will be killed if it's outside the bounds of the screen.
     */
    @Override
    public void update() {
        if(isAlive()) {
            // Update our sprite if it's animated
            AnimatedSprite.stepIfAnimatedSprite(sprite);

            // Check that our projectile is still within the screen.
            alive = boundsChecker.isWithinBounds(this, SCREEN_BOUNDS);
        }
    }

    /**
     * Draws this projectile if it's alive.
     *
     * @param spriteBatch the spriteBatch used to draw this gameObject.
     */
    @Override
    public void draw(SpriteBatch spriteBatch) {
        if(isAlive()) {
            spriteBatch.draw(sprite, position);
        }
    }

    /**
     * Returns the String representation of this instance.
     *
     * @return String representation of this instance.
     */
    @Override
    public String toString() {
        return super.toString() +
                "Projectile{" +
                "alive=" + alive +
                ", boundsChecker=" + boundsChecker +
                '}';
    }
}
