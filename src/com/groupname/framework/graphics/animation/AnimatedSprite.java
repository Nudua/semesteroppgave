package com.groupname.framework.graphics.animation;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.SpriteSheet;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.Objects;

/**
 * This class is a representation for a character in a game
 * using a region of a SpriteSheet as the backing for the Image
 * used to draw this class and is a subclass of the SPRITE class that supports
 * animation via different AnimationFrames.
 */
public class AnimatedSprite extends Sprite {
    private final List<AnimationFrame> animationFrames;
    private int frame = 0;

    /**
     * Creates a new AnimatedSprite with the specified spriteSheet, initialSprieRegion and the
     * list of animationFrames used for animating this SPRITE.
     *
     * @param spriteSheet the SpriteSheet used by this instance.
     * @param initialSpriteRegion the first spriteRegion used by this instance.
     * @param animationFrames the list of animationFrames used for animating.
     * @throws NullPointerException if any of the parameters are null.
     * @throws IllegalArgumentException if the animationFrames list contains no frames.
     */
    public AnimatedSprite(SpriteSheet spriteSheet, Rectangle initialSpriteRegion, List<AnimationFrame> animationFrames) {
        super(spriteSheet, initialSpriteRegion);

        Objects.requireNonNull(animationFrames);

        if(animationFrames.size() == 0) {
            throw new IllegalArgumentException();
        }

        this.animationFrames = Objects.requireNonNull(animationFrames);
    }

    /**
     * Progresses the animation used by this AnimatedSprite.
     * Should be called every frame.
     */
    public void step() {
        AnimationFrame activeFrame = animationFrames.get(frame);

        activeFrame.step();

        if (activeFrame.isDone()) {
            // Reset the frame's internal timer and move on the the next frame
            activeFrame.reset();

            frame++;
        }

        if(frame > animationFrames.size() - 1) {
            frame = 0;
        }
    }

    /**
     * Gets the current SpriteRegion used by this AnimatedSprite.
     * Use step to animate this instance.
     *
     * @return the current SpriteRegion used by this AnimatedSprite.
     */
    @Override
    public Rectangle getSpriteRegion() {
        return animationFrames.get(frame).getSpriteRegion();
    }

    /**
     * Static method used to check if a SPRITE is a superclass of a AnimatedSprite,
     * and calls te step() method to further it's animation.
     *
     * @param sprite to check if this instance is a superclass of the type AnimatedSprite,
     *               and call step() to further it's animation.
     */
    public static void stepIfAnimatedSprite(Sprite sprite) {
        if(sprite instanceof AnimatedSprite) {
            AnimatedSprite animatedSprite = (AnimatedSprite)sprite;
            animatedSprite.step();
        }
    }

    /**
     * Returns the list of Animation frames used by this object and the current frame
     * as a String representation.
     *
     * @return the list of Animation frames used by this object and the current frame
     *         as a String representation.
     */
    @Override
    public String toString() {
        return super.toString() + "AnimatedSprite{" +
                "animationFrames=" + animationFrames +
                ", frame=" + frame +
                '}';
    }
}
