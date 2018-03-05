package com.groupname.framework.graphics;

import com.groupname.framework.math.IntVector2D;
import com.groupname.framework.util.Strings;
import com.groupname.framework.math.IntVector2D;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class Sprite {

    private final String name;
    private final SpriteSheet spriteSheet;

    protected Rectangle spriteRegion;
    protected double scale = 1.0d;

    public Sprite(String name, SpriteSheet spriteSheet, Rectangle spriteRegion) {
        this.name = Strings.requireNonNullAndNotEmpty(name);
        this.spriteSheet = Objects.requireNonNull(spriteSheet);
        this.spriteRegion = Objects.requireNonNull(spriteRegion);
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public double getWidth() {
        return scale * spriteRegion.getWidth();
    }

    public double getHeight() {
        return scale * spriteRegion.getHeight();
    }

    public String getName() {
        return name;
    }

    public SpriteSheet getSpriteSheet() {
        return spriteSheet;
    }

    public Rectangle getSpriteRegion() {
        return spriteRegion;
    }

    public void setSpriteRegion(Rectangle spriteRegion) {
        this.spriteRegion = spriteRegion;
    }

    // Sort this out
    public static Rectangle createSpriteRegion(int width, int height) {
        return createSpriteRegion(0,0, width, height);
    }
    // Do something about this
    public static Rectangle createSpriteRegion(int smallX, int smallY, int width, int height) {
        return createSpriteRegion(smallX, smallY, width, height, new IntVector2D());
    }

    public static Rectangle createSpriteRegion(int smallX, int smallY, int width, int height, IntVector2D offset) {
        return new Rectangle(smallX * width + offset.getX(), smallY * height + offset.getY(), width, height);
    }
}
