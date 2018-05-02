package com.groupname.framework.graphics.drawing;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.math.Vector2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.EnumSet;
import java.util.Objects;

// Facade - https://en.wikipedia.org/wiki/Facade_pattern
// Provides a simple way to draw sprites, hiding the complexity of JavaFX's graphicsContext
// Make an interface? then have the implementation be SpriteBatchFX


/**
 * An JavaFX based implementation of the SpriteBatch interface which uses the GraphicsContext2D class to draw Sprites.
 *
 * Supports drawing sprites at the position given, horizontal and vertical flipping of these sprites,
 * as well as tinting the sprites in a specified color.
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
    public void draw(Sprite sprite, Vector2D position) {
        draw(sprite, position, EnumSet.noneOf(SpriteFlip.class));

    }

    /**
     * Draws the given sprite at the specified position with the specified spriteFlip flags.
     *
     * @param sprite the sprite to draw.
     * @param position the position to draw it.
     * @see SpriteFlip
     */
    public void draw(Sprite sprite, Vector2D position, EnumSet<SpriteFlip> flipFlags) {
        draw(sprite, position, flipFlags, false);
    }

    /**
     * Draws the given sprite at the specified position with the specified spriteFlip flags and color tint.
     *
     * @param sprite the sprite to draw.
     * @param position the position to draw it.
     * @param invertColors if we should invert the colors.
     */
    public void draw(Sprite sprite, Vector2D position, EnumSet<SpriteFlip> flipFlags, boolean invertColors) {
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

        //graphicsContext.setEffect(null);

        graphicsContext.save();
        // Figure out how to tint red
        if(invertColors) {

            Glow glow = new Glow();
            glow.setLevel(0.9d);


            //Blend blend = new Blend(BlendMode.MULTIPLY, monochrome, new ColorInput(posX,posY, spriteWidth, spriteHeight, Color.RED));

            // do nothing for now
            //graphicsContext.setEffect(glow);
        }

        //graphicsContext.setEffect(new DropShadow());

        graphicsContext.drawImage(sprite.getSpriteSheet().getImage(), srcRect.getX(), srcRect.getY() + 1, srcRect.getWidth(), srcRect.getHeight() - 1, posX, posY, spriteWidth, spriteHeight);

        graphicsContext.restore();
    }
}
