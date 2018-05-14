package com.groupname.framework.graphics.drawing;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.math.Vector2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

import java.util.EnumSet;
import java.util.Objects;

/**
 * An JavaFX based implementation of the SpriteBatch interface which uses the GraphicsContext2D class to draw Sprites.
 *
 * Supports drawing sprites at the position given, horizontal and vertical flipping of these sprites.
 *
 * Example usage: (assume that we already have a Sprite called mySprite.)
 *
 * // We need a Canvas to draw on.
 * Canvas canvas = new Canvas();
 *
 * // Create a new instance using the Canvas' graphicsContext
 * SpriteBatch spriteBatchFX = new SpriteBatchFX(canvas.getGraphicsContext2D());
 *
 * // spriteBatchFX will now draw mySprite horizontally flipped at the position (100,200) on the canvas.
 * spriteBatch.draw(mySprite, new Vector2D(100, 200), EnumSet.of(SpriteFlip.HORIZONTAL));
 */
public class SpriteBatchFX implements SpriteBatch {
    private GraphicsContext graphicsContext;

    /**
     * Creates a new instance with the specified GraphicsContext,
     * this GraphicsContext will usually be acquired from a Canvas instance by using it's 'getGraphicsContext2D()' method.
     *
     * @param graphicsContext the graphicsContext that is used to draw with internally.
     */
    public SpriteBatchFX(GraphicsContext graphicsContext) {
        this.graphicsContext = Objects.requireNonNull(graphicsContext, "graphicsContext cannot be null");
    }

    /**
     * Draws the given sprite at the specified position.
     *
     * @param sprite the sprite to draw.
     * @param position the position to draw it.
     */
    @Override
    public void draw(Sprite sprite, Vector2D position) {
        draw(sprite, position, EnumSet.noneOf(SpriteFlip.class));
    }

    /**
     * Draws the given sprite at the specified position with the specified spriteFlip flags.
     *
     * @param sprite the sprite to draw.
     * @param position the position to draw it.
     */
    @Override
    public void draw(Sprite sprite, Vector2D position, EnumSet<SpriteFlip> flipFlags) {
        Objects.requireNonNull(sprite);
        Objects.requireNonNull(position);

        Rectangle srcRect = sprite.getSpriteRegion();

        double spriteWidth = sprite.getWidth();
        double spriteHeight = sprite.getHeight();
        double posX = position.getX();
        double posY = position.getY();

        if(flipFlags.contains(SpriteFlip.HORIZONTAL)) {
            // Setting the width to negative width will flip it horizontally
            spriteWidth = -spriteWidth;
            // Offset the position with the width
            posX += sprite.getWidth();
        }

        if(flipFlags.contains(SpriteFlip.VERTICAL)) {
            // Setting the height to negative height will flip the sprite vertically
            spriteHeight = -spriteHeight;
            // Offset the position with the height
            posY += sprite.getHeight();
        }

        graphicsContext.drawImage(sprite.getSpriteSheet().getImage(), srcRect.getX(), srcRect.getY() + 1, srcRect.getWidth(), srcRect.getHeight() - 1, posX, posY, spriteWidth, spriteHeight);
    }

    /**
     * Returns the String representation of this object.
     *
     * @return the String representation of this object.
     */
    @Override
    public String toString() {
        return "SpriteBatchFX{" +
                "graphicsContext=" + graphicsContext +
                '}';
    }
}
