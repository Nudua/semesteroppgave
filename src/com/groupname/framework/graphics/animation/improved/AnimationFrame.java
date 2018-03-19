package com.groupname.framework.graphics.animation.improved;

import javafx.scene.shape.Rectangle;

public class AnimationFrame {

    private final Rectangle spriteRegion;
    private final int repeatCount;
    private int counter;

    public AnimationFrame(Rectangle spriteRegion, int repeatCount) {
        this.spriteRegion = spriteRegion;
        this.repeatCount = repeatCount;
    }

    public Rectangle getSpriteRegion() {
        return spriteRegion;
    }

    public boolean isDone() {
        return counter >= repeatCount;
    }

    public void reset() {
        counter = 0;
    }

    // IncrementCounter?
    public void step() {
        counter++;
    }
}