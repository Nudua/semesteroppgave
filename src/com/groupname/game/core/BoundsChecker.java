package com.groupname.game.core;

import com.groupname.framework.core.GameObject;
import com.groupname.framework.math.Size;
import com.groupname.framework.math.Vector2D;
import javafx.scene.shape.Rectangle;

public class BoundsChecker {

    public boolean isWithinBounds(GameObject gameObject, Rectangle bounds) {
        //Vector2D position = gameObject.getPosition();
        Size size = new Size((int)gameObject.getSprite().getWidth(), (int)gameObject.getSprite().getHeight());

        // Right side
        if(gameObject.getPosition().getX() + size.getWidth() >= bounds.getX() + bounds.getWidth()) {
            return false;
        }

        // Left side
        if(gameObject.getPosition().getX() <= bounds.getX()) {
            return false;
        }

        // Top
        if(gameObject.getPosition().getY() <= bounds.getY()) {
            return false;
        }

        // Bottom
        if(gameObject.getPosition().getY() + size.getHeight() >= bounds.getY() + bounds.getHeight()) {
            return false;
        }

        return true;
    }
}
