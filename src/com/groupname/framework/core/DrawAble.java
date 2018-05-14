package com.groupname.framework.core;

import com.groupname.framework.graphics.drawing.SpriteBatch;

/**
 * Used components that are able to draw themselves.
 */
public interface DrawAble {
    /**
     * Implementations should use this method to draw itself using the supplied SpriteBatch.
     *
     * @param spriteBatch the spriteBatch used for drawing any sprites.
     */
    void draw(SpriteBatch spriteBatch);
}
