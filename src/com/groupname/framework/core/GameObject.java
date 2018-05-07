package com.groupname.framework.core;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.math.Vector2D;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

/**
 * A base class for all gameobjects, which essentially contains a
 * Sprite (Image) and a Vector2D (position).
 *
 * It also contains methods for generating a hitbox and checking collisions between different gameobjects.
 *
 * The update and draw methods must overridden.
 */
public abstract class GameObject implements UpdateDrawAble {
    protected boolean enabled;

    protected final Sprite sprite;
    protected final Vector2D position;

    public GameObject(Sprite sprite, Vector2D position) {
        this.sprite = Objects.requireNonNull(sprite, "sprite cannot be null");
        this.position = Objects.requireNonNull(position, "position cannot be null");
    }

    /**
     * Returns the sprite associated with this instance.
     *
     * @return the sprite associated with this instance.
     */
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * Generates and returns a new Rectangle that represents the hitbox for this GameObject.
     *
     * @return a new Rectangle that represents the hitbox for this GameObject.
     */
    public Rectangle getHitbox() {
        return new Rectangle(position.getX(), position.getY(), sprite.getWidth(), sprite.getHeight());
    }

    /**
     * Checks whether another rectangle collides (intersects) with the hitbox for this instance.
     *
     * @param other the rectangle to check if it intersects with this hitbox.
     * @return true if the supplied rectangle collides with the hitbox if this GameObject.
     */
    public boolean collides(Rectangle other) {
        return getHitbox().intersects(other.getBoundsInParent());
    }

    /**
     * Returns a copy of the position of this instance.
     *
     * @return a copy of the position of this instance.
     */
    public Vector2D getPosition() {
        return new Vector2D(position);
    }

    /**
     * Updates the position to the given position (the values are copied)
     *
     * @param position the new Vector2D to update the internal position to.
     */
    public void setPosition(Vector2D position) {
        this.position.set(position);
    }

    /**
     * Implementations must override this method to update any gameobject logic.
     */
    public abstract void update();

    /**
     * Implementations must override this method and use it to draw itself.
     * Implementations must also validate that spriteBatch is not null. (fail-fast)
     *
     * @param spriteBatch the spriteBatch used to draw this gameObject.
     */
    public abstract void draw(SpriteBatch spriteBatch);

    /**
     * Returns the String representation of this object.
     *
     * @return the String representation of this object.
     */
    @Override
    public String toString() {
        return "GameObject{" +
                "enabled=" + enabled +
                ", sprite=" + sprite +
                ", position=" + position +
                '}';
    }
}
