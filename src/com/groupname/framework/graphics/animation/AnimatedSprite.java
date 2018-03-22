package com.groupname.framework.graphics.animation;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.SpriteSheet;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.Objects;

public class AnimatedSprite extends Sprite implements Animatable {

    private AnimationLogic<AnimatedSprite> animationLogic;
    private List<Rectangle> spriteRegions;

    public AnimatedSprite(SpriteSheet spriteSheet, List<Rectangle> spriteRegions) {
        this(spriteSheet, spriteRegions, 0);
    }

    public AnimatedSprite(SpriteSheet spriteSheet, List<Rectangle> spriteRegions, int firstSpriteRegion) {
        super(spriteSheet, spriteRegions.get(firstSpriteRegion));
        this.spriteRegions = Objects.requireNonNull(spriteRegions);
    }

    public List<Rectangle> getSpriteRegions() {
        return spriteRegions;
    }

    // Strategy pattern, keep the animation logic separated.
    // Haven't decided the exact setup yet
    public void setAnimationLogic(AnimationLogic<AnimatedSprite> animationLogic) {
        this.animationLogic = animationLogic;
    }

    public void stepAnimation() {
        if(animationLogic != null) {
            animationLogic.step(this);
        }
    }

    public void resetAnimation() {
        if(animationLogic != null) {
            animationLogic.reset(this);
        }
    }

    public Sprite asSprite() {
        return this;
    }
}
