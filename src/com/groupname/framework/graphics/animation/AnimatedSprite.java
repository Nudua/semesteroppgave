package com.groupname.framework.graphics.animation;

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

// Check if animation is done, create an event for it?
/*
interface IAnimatedSprite {
    void start();
    void stop();
    void reset();
}
*/
public class AnimatedSprite extends Sprite {

    public enum Status {
        Running,
        Completed
    }

    private final List<AnimationFrame> animationFrames;

    private Status status = Status.Running;
    private int frame = 0;
    private boolean looping = true;

    // This method gets called if we're not loop
    private Runnable completed;

    public AnimatedSprite(SpriteSheet spriteSheet, Rectangle initialSpriteRegion, List<AnimationFrame> animationFrames) {
        super(spriteSheet, initialSpriteRegion);

        Objects.requireNonNull(animationFrames);

        if(animationFrames.size() == 0) {
            throw new IllegalArgumentException();
        }

        this.animationFrames = Objects.requireNonNull(animationFrames);
    }

    public void setOnCompleted(Runnable completed) {
        this.completed = completed;
    }

    public void isLooping(boolean loop) {
        this.looping = loop;
    }

    public void step() {
        if(status == Status.Running) {
            AnimationFrame activeFrame = animationFrames.get(frame);

            activeFrame.step();

            if(activeFrame.isDone()) {
                // Reset the frame's internal timer and move on the the next frame
                activeFrame.reset();

                frame++;

                // Wrap around, or fire off completed if once?
                if(frame > animationFrames.size() - 1) {
                    if(looping) {
                        frame = 0;
                    } else {
                        status = Status.Completed;
                        // Fire up our event
                        if(completed != null) {
                            completed.run();
                        }
                    }
                }
            }
        }
    }

    @Override
    public Rectangle getSpriteRegion() {
        // getRegion instead?
        return animationFrames.get(frame).getSpriteRegion();
    }

    public static void stepIfAnimatedSprite(Sprite sprite) {
        if(sprite instanceof AnimatedSprite) {
            AnimatedSprite animatedSprite = (AnimatedSprite)sprite;
            animatedSprite.step();
        }
    }
}
