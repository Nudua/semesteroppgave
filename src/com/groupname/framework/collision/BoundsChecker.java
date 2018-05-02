package com.groupname.framework.collision;

import com.groupname.framework.core.GameObject;
import com.groupname.framework.math.Size;
import com.groupname.framework.math.Vector2D;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

/**
 * Check if a game object is with in the specified rectangle.
 */
public class BoundsChecker {

    /**
     * Creates an new instance of this object.
     */
    public BoundsChecker() {
    }

    /**
     * Return true if the game object is outside the specified rectangle.
     * Its purpose is to keep the game object inside a specified rectangle.
     *
     * @param gameObject the object to control.
     * @param bounds the rectangle it shall stay inside of.
     * @return true if the game object is outside the specified rectangle.
     */
    public boolean isWithinBounds(GameObject gameObject, Rectangle bounds) {
        return isWithinBounds(gameObject, bounds, null);
    }

    /**
     * Return true if the game object is outside the specified rectangle.
     * Its purpose is to keep the game object inside a specified rectangle.
     *
     * @param gameObject the object to control.
     * @param bounds the rectangle it shall stay inside of.
     * @param newPosition use this position instead of the position supplied by the gameObject.
     *                    if this parameter is null then the position of the gameObject will be used.
     * @return true if the game object is outside the specified rectangle.
     */
    public boolean isWithinBounds(GameObject gameObject, Rectangle bounds, Vector2D newPosition) {
        Objects.requireNonNull(gameObject);
        Objects.requireNonNull(bounds);

        Vector2D position = newPosition == null ? gameObject.getPosition() : newPosition;

        Size size = new Size((int)gameObject.getSprite().getWidth(), (int)gameObject.getSprite().getHeight());

        // Right side
        if(position.getX() + size.getWidth() >= bounds.getX() + bounds.getWidth()) {
            return false;
        }

        // Left side
        if(position.getX() <= bounds.getX()) {
            return false;
        }

        // Top
        if(position.getY() <= bounds.getY()) {
            return false;
        }

        // Bottom
        if(position.getY() + size.getHeight() >= bounds.getY() + bounds.getHeight()) {
            return false;
        }

        return true;
    }
}
