package com.groupname.framework.graphics.drawing;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.math.Vector2D;
import javafx.scene.paint.Color;

import java.util.EnumSet;

/**
 * This interface provides an easy way to draw sprites at a given position.
 *
 * Implementations must support drawing of sprites at the position given
 * and they must also support flipping of these sprites horizontally and vertically
 * as well as color tinting of these sprites.
 */
public interface SpriteBatch {

    /**
     * Draws the given sprite at the specified position.
     *
     * @param sprite the sprite to draw.
     * @param position the position to draw it.
     */
    void draw(Sprite sprite, Vector2D position);

    /**
     * Draws the given sprite at the specified position with the specified spriteFlip flags.
     *
     * @param sprite the sprite to draw.
     * @param position the position to draw it.
     * @see SpriteFlip
     */
    void draw(Sprite sprite, Vector2D position, EnumSet<SpriteFlip> flipFlags);

    /**
     * Draws the given sprite at the specified position with the specified spriteFlip flags and color tint.
     *
     * @param sprite the sprite to draw.
     * @param position the position to draw it.
     * @param tintColor the color to tint the sprite in.
     */
    void draw(Sprite sprite, Vector2D position, EnumSet<SpriteFlip> flipFlags, Color tintColor);
}
