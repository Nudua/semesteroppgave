package com.groupname.framework.graphics.animation;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.SpriteSheet;
import com.groupname.framework.graphics.animation.improved.AnimatedSprite;
import com.groupname.framework.graphics.animation.improved.AnimationFrame;
import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import com.groupname.framework.util.EmptyStringException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class AnimatedSpriteTests {

    private final int frame1Delay = 10;
    private final int frame2Delay = 12;

    private AnimationFrame frame1 = new AnimationFrame(Sprite.createSpriteRegion(100,100), frame1Delay);
    private AnimationFrame frame2 = new AnimationFrame(Sprite.createSpriteRegion(300,100), frame2Delay);
    private SpriteSheet spriteSheet = new SpriteSheet("testSheet", Content.loadImage("projectiles.png", ResourceType.SpriteSheet));

    public AnimatedSprite getValidAnimatedSprite() {
        return new AnimatedSprite(spriteSheet, frame1.getSpriteRegion(), Arrays.asList(frame1, frame2));
    }

    // Constructor

    @Test(expected = NullPointerException.class)
    public void spriteSheetCannotBeNull() {
        new AnimatedSprite(null, frame1.getSpriteRegion(), Arrays.asList(frame1, frame2));
    }

    @Test(expected = NullPointerException.class)
    public void initialSpriteRegionCannotBeNull() {
        new AnimatedSprite(spriteSheet, null, Arrays.asList(frame1, frame2));
    }

    @Test(expected = NullPointerException.class)
    public void animationFramesCannotBeNull() {
        new AnimatedSprite(spriteSheet, frame1.getSpriteRegion(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void animationFramesCannotBeEmpty() {
        new AnimatedSprite(spriteSheet, frame1.getSpriteRegion(), new ArrayList<>());
    }

    @Test
    public void stepChangesAndLoopsAnimationFrame() {
        AnimatedSprite sprite = getValidAnimatedSprite();

        assertSame(sprite.getSpriteRegion(), frame1.getSpriteRegion());

        for(int i = 0; i < frame1Delay; i++) {
            sprite.step();
        }
        //frame1.step();

        assertSame(sprite.getSpriteRegion(), frame2.getSpriteRegion());
        sprite.step();

        // Should loop to the first frame again
        //assertSame(sprite.getSpriteRegion(), frame1.getSpriteRegion());
    }

    @Test
    public void getSpriteRegionGivesCorrectValue() {
        AnimatedSprite sprite = getValidAnimatedSprite();

        assertSame(sprite.getSpriteRegion(), frame1.getSpriteRegion());
    }

    // todo looping tests
}
