package com.groupname.framework.graphics;

import com.groupname.framework.math.Size;
import com.groupname.framework.math.IntVector2D;
import javafx.scene.shape.Rectangle;

// Builder pattern - https://en.wikipedia.org/wiki/Builder_pattern
// Useful when faced with a lot of constructor parameters
// Might consider refactoring again
public class Sprite {

    // Required parameters
    private final String name;
    private final String spriteSheet;
    private Size size;

    // Optional parameters

    // The X and Y position within the spritesheet grid (Big X and Big Y)
    private IntVector2D sourceVector;

    // Offset to the first spriteOld in the spritesheet
    private IntVector2D sourceOffset;

    private double scale;
    private int zIndex;

    // non-parameters
    private Rectangle sourceRect;

    public String getName() {
        return name;
    }

    public String getSpriteSheet() {
        return spriteSheet;
    }

    public void setSourceVector(int x, int y) {
        this.sourceVector = new IntVector2D(x,y);
        this.sourceRect = new Rectangle((sourceVector.getX() * size.getWidth()) + sourceOffset.getX(), (sourceVector.getY() * size.getHeight()) + sourceOffset.getY(), size.getWidth(), size.getHeight());
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public Rectangle getSourceRect() {
        return sourceRect;
    }

    public double getWidth() {
        return size.getWidth() * scale;
    }

    public double getHeight() {
        return size.getHeight() * scale;
    }

    public int getzIndex() {
        return zIndex;
    }

    // todo: parameter validation for everything
    public static class Builder {
        // Required parameters
        private final String name;
        private final String spriteSheet;
        private final Size size;

        // Optional parameters, these must have a default value!
        private IntVector2D sourceVector = new IntVector2D(0,0);
        private IntVector2D sourceOffset = new IntVector2D(0,0);
        private double scale = 1.0d;
        private int zIndex = 0;

        public Builder(String name, String spriteSheet, Size size) {
            this.name = name;
            this.spriteSheet = spriteSheet;
            this.size = size;
        }

        public Builder sourceVector(int x, int y) {
            sourceVector = new IntVector2D(x, y);
            return this;
        }

        public Builder sourceOffset(int x, int y) {
            sourceOffset = new IntVector2D(x, y);
            return this;
        }

        public Builder scale(double value) {
            scale = value;
            return this;
        }

        public Builder zIndex(int value) {
            zIndex = value;
            return this;
        }

        public Sprite build() {
            return new Sprite(this);
        }
    }

    private Sprite(Builder builder) {
        name = builder.name;
        spriteSheet = builder.spriteSheet;
        size = builder.size;

        sourceVector = builder.sourceVector;
        sourceOffset = builder.sourceOffset;
        scale = builder.scale;
        zIndex = builder.zIndex;

        // Generate this elsewhere
        sourceRect = new Rectangle((sourceVector.getX() * size.getWidth()) + sourceOffset.getX(), (sourceVector.getY() * size.getHeight()) + sourceOffset.getY(), size.getWidth(), size.getHeight());
    }
}
