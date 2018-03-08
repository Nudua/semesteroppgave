package com.groupname.framework.graphics.animation.improved;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.SpriteSheet;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.Objects;

// Todo
/*
enum AnimationLoop {
    Once,
    Infinite,
}
*/

public class AnimatedSprite extends Sprite {

    private final List<AnimationFrame> animationFrames;
    private int frame;

    public AnimatedSprite(String name, SpriteSheet spriteSheet, Rectangle initialSpriteRegion, List<AnimationFrame> animationFrames) {
        super(name, spriteSheet, initialSpriteRegion);
        this.animationFrames = Objects.requireNonNull(animationFrames);
        frame = 0;
    }

    public void step() {
        AnimationFrame activeFrame = animationFrames.get(frame);

        activeFrame.step();

        if(activeFrame.isDone()) {
            // Reset the frame's internal timer and move on the the next frame
            activeFrame.reset();

            frame++;

            // Wrap around, or fire off completed if once?
            if(frame > animationFrames.size() - 1) {
                frame = 0;
            }
        }
    }

    @Override
    public Rectangle getSpriteRegion() {
        // getRegion instead?
        return animationFrames.get(frame).getSpriteRegion();
    }
}
