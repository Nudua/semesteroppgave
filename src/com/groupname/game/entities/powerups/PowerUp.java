package com.groupname.game.entities.powerups;

import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.animation.improved.AnimatedSprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.entities.Player;



public abstract class PowerUp extends GameObject {

    private boolean collected = false;

    public PowerUp(Sprite sprite, Vector2D position) {
        super(sprite, position);
    }

    public boolean isCollected() {
        return collected;
    }

    @Override
    public void update() {
        if(!collected) {
            AnimatedSprite.stepIfAnimatedSprite(sprite);
        }
    }

    public void onCollect(Player player) {
        collected = true;
        System.out.println("Item Collected!");
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        if(!collected) {
            spriteBatch.draw(sprite, position);
        }
    }
}
