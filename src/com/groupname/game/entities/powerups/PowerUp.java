package com.groupname.game.entities.powerups;

import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.animation.AnimatedSprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.entities.Player;


/**
 * Base class for PowerUps.
 * PowerUps gives the player "upgrades".
 */
public abstract class PowerUp extends GameObject {

    private boolean collected = false;

    public PowerUp(Sprite sprite, Vector2D position) {
        super(sprite, position);
    }

    public boolean isCollected() {
        return collected;
    }

    /**
     * Method for updating the base logic for PowerUps
     */
    @Override
    public void update() {
        if(!collected) {
            AnimatedSprite.stepIfAnimatedSprite(sprite);
        }
    }

    /**
     * Method we call on collect.
     *
     * @param player the player that collect the PowerUp
     */
    public void onCollect(Player player) {
        collected = true;
    }

    /**
     * Draw the PowerUp until its collected.
     *
     * @param spriteBatch the spriteBatch used to draw this gameObject.
     */
    @Override
    public void draw(SpriteBatch spriteBatch) {
        if(!collected) {
            spriteBatch.draw(sprite, position);
        }
    }

    /**
     * Returns the String representation of this instance.
     *
     * @return String representation of this instance.
     */
    @Override
    public String toString() {
        return "PowerUp{" +
                "collected=" + collected +
                '}';
    }
}
