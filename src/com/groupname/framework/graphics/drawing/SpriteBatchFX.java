package com.groupname.framework.graphics.drawing;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.SpriteSheet;
import com.groupname.framework.math.Vector2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// Facade - https://en.wikipedia.org/wiki/Facade_pattern
// Provides a simple way to draw sprites, hiding the complexity of JavaFX's graphicsContext
// Make an interface? then have the implementation be SpriteBatchFX
public class SpriteBatchFX implements SpriteBatch {

    private GraphicsContext graphicsContext;


    public SpriteBatchFX(GraphicsContext graphicsContext) {
        this.graphicsContext = Objects.requireNonNull(graphicsContext, "graphicsContext cannot be null");
    }

    public void draw(Sprite sprite, Vector2D position) {
        //Rectangle srcRect = sprite.getSpriteRegion();

        //graphicsContext.drawImage(sprite.getSpriteSheet().getImage(), srcRect.getX(), srcRect.getY(), srcRect.getWidth(), srcRect.getHeight(), position.getX(),position.getY(),sprite.getWidth(), sprite.getHeight());
        draw(sprite, position, EnumSet.of(SpriteFlip.NONE));
    }

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

        graphicsContext.drawImage(sprite.getSpriteSheet().getImage(), srcRect.getX(), srcRect.getY(), srcRect.getWidth(), srcRect.getHeight(), posX, posY, spriteWidth, spriteHeight);
    }
}
