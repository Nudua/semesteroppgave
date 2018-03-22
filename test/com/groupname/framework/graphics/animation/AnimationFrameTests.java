package com.groupname.framework.graphics.animation;

import javafx.scene.shape.Rectangle;
import org.junit.Test;
import static org.junit.Assert.*;


public class AnimationFrameTests {

    private Rectangle testRectangle = new Rectangle(100,100);

    // Constructor parameter tests
    @Test(expected = NullPointerException.class)
    public void RectangleParameterCannotBeNull() {
        AnimationFrame animationFrame = new AnimationFrame(null, 12);
    }

    // Constructor parameter tests
    @Test(expected = IllegalArgumentException.class)
    public void RepeatCountParameterCannotBeNull() {
        AnimationFrame animationFrame = new AnimationFrame(testRectangle, -1);
    }

    @Test
    public void checkGetSpriteRegion() {
        AnimationFrame testAnimationFrame = new AnimationFrame(testRectangle, 1);
        assertSame(testAnimationFrame.getSpriteRegion(), testRectangle);
    }

}
