package com.groupname.framework.graphics.animation;

import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class AnimationFrame {

    private final Rectangle spriteRegion;
    private final int repeatCount;
    private int counter;

    public AnimationFrame(Rectangle spriteRegion, int repeatCount) {
        this.spriteRegion = Objects.requireNonNull(spriteRegion);
        if(repeatCount < 0){
            throw new IllegalArgumentException("repeatCount cant be negative");
        }
        this.repeatCount = repeatCount;
    }

    public Rectangle getSpriteRegion() {
        return spriteRegion;
    }

    public boolean isDone() {
        return counter >= repeatCount - 1;
    }

    public void reset() {
        counter = 0;
    }

    // IncrementCounter?
    public void step() {
        counter++;
    }
}
