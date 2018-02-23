package com.groupname.framework.graphics.animation;

import javafx.scene.shape.Rectangle;

import java.util.List;

// A linear animation will just cycle through a set of spriteRectangles from a list
public class LinearAnimation implements AnimationLogic<AnimatedSprite> {

    private final int delay;
    private int counter = 0;
    private int currentFrame = 0;

    public LinearAnimation(int delay) {
        // Delay can't be negative
        this.delay = delay >= 0 ? delay  : 0;
        this.counter = delay;
    }

    public void step(AnimatedSprite sprite) {
        // Delay the animation until the counter is zero.
        if(counter > 0) {
            counter--;
            return;
        }

        currentFrame++;
        //else
        List<Rectangle> spriteRegions = sprite.getSpriteRegions();

        // Cycle through the animations
        if(currentFrame >= spriteRegions.size()) {
            currentFrame = 0;
        }

        changeSpriteRegion(sprite, currentFrame);

        // Reset the counter to the delay
        counter = delay;
    }

    public void reset(AnimatedSprite sprite) {
        currentFrame = 0;
        counter = 0;

        changeSpriteRegion(sprite, currentFrame);
    }

    private void changeSpriteRegion(AnimatedSprite sprite, int index) {
        // make sure we're inside bounds
        assert index > 0;
        assert index < sprite.getSpriteRegions().size() - 1;

        sprite.setSpriteRegion(sprite.getSpriteRegions().get(index));
    }

}
