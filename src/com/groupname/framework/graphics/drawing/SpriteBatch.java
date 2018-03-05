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
public class SpriteBatch {

    private GraphicsContext graphicsContext;

    // A collection of all the spritesheets used in the application, make into a class of itself?
    //private final Map<String, SpriteSheet> spriteSheets;

    public SpriteBatch(GraphicsContext graphicsContext) {
        this.graphicsContext = Objects.requireNonNull(graphicsContext, "gc cannot be null");
        //spriteSheets = new HashMap<>();
    }

    /*
    public void addSpritesheet(String name, Image image) {
        spriteSheets.put(name, new SpriteSheet(name, (int)image.getWidth(), (int)image.getHeight(), image));
    }
    */

    /*
    public boolean containsSpriteSheet(String key) {
        return spriteSheets.containsKey(key);
    }
    */

    public void draw(Sprite sprite, Vector2D position) {
        Rectangle srcRect = sprite.getSpriteRegion();

        graphicsContext.drawImage(sprite.getSpriteSheet().getImage(), srcRect.getX(), srcRect.getY(), srcRect.getWidth(), srcRect.getHeight(), position.getX(),position.getY(),sprite.getWidth(), sprite.getHeight());
    }

    public void draw(Sprite sprite, Vector2D position, EnumSet<SpriteFlip> flipFlags) {
        Rectangle srcRect = sprite.getSpriteRegion();

        double spriteWidth = sprite.getWidth();
        double spriteHeight = sprite.getHeight();
        double posX = position.getX();
        double posY = position.getY();

        // Setting the width to negative will flip it horizontally
        if(flipFlags.contains(SpriteFlip.HORIZONTAL)) {
            spriteWidth = -spriteWidth;
            // We need to offset the X-position of the spriteOld with the width
            posX += sprite.getWidth();
        }

        // Setting the width to negative will flip it horizontally
        if(flipFlags.contains(SpriteFlip.VERTICAL)) {
            spriteHeight = -spriteHeight;
            posY += sprite.getHeight();
        }

        //if(containsSpriteSheet(sprite.getSpriteSheet())) {
        graphicsContext.drawImage(sprite.getSpriteSheet().getImage(), srcRect.getX(), srcRect.getY(), srcRect.getWidth(), srcRect.getHeight(), posX, posY, spriteWidth, spriteHeight);
        //}
    }
}
