package com.groupname.framework.graphics.animation;

import javafx.scene.shape.Rectangle;

import java.util.Objects;

/**
 * Represents a single frame (Rectangle) that can be used to animate objects
 * by switching between different animation frames.
 *
 * Users should use the step() each frame to make sure that the frame completes its cycle.
 * Use reset to restart the frame before starting it again.
 */
public class AnimationFrame {
    private final Rectangle spriteRegion;
    private final int repeatCount;
    private int counter;

    /**
     * Creates a new instance with the specified spriteregion
     * and how many times this spriteregion will be shown before it has completed it's cycle.
     *
     * @param spriteRegion the spriteregion this frame will use.
     * @param repeatCount the amount of frames that this AnimationFrame will use before it's cycle is completed.
     *                    this value cannot be less than 0.
     * @throws IllegalArgumentException if the repeatCount is less than 0.
     * @throws NullPointerException if the spriteRegion was null.
     */
    public AnimationFrame(Rectangle spriteRegion, int repeatCount) {
        this.spriteRegion = Objects.requireNonNull(spriteRegion);
        if(repeatCount < 0){
            throw new IllegalArgumentException("repeatCount cant be negative");
        }
        this.repeatCount = repeatCount;
    }

    /**
     * Returns the spriteRegion set to this object.
     *
     * @return the spriteRegion set to this object.
     */
    public Rectangle getSpriteRegion() {
        return spriteRegion;
    }

    /**
     * Returns true if the current cycle is completed, false otherwise.
     *
     * @return true if the cycle is completed.
     */
    public boolean isDone() {
        return counter >= repeatCount - 1;
    }

    /**
     * Resets the cycle to the start.
     */
    public void reset() {
        counter = 0;
    }

    /**
     * Increments the counter by one, users should call IsDone() to see if the cycle is completed after calling this method.
     */
    public void step() {
        counter++;
    }
}
