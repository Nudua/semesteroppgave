package com.groupname.game.entities;

import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.animation.AnimatedSprite;
import com.groupname.framework.graphics.drawing.SpriteFlip;
import com.groupname.framework.math.Vector2D;

/**
 * Base class for game object that moves,
 * for example Player and Enemy.
 */
public abstract class Actor extends GameObject {

    private static final int DEFAULT_HITPOINTS = 5;

    private int hitPoints = DEFAULT_HITPOINTS;
    private boolean alive = true;

    // Think about these
    private Runnable onCollide;
    private Runnable onDeath;

    protected SpriteFlip spriteFlip = SpriteFlip.NONE;

    /**
     * Creates a new instance of this class with the specific parameters.
     *
     * @param sprite Sets an default sprite for the enemy.
     * @param position Sets the start position, on the canvas.
     */
    public Actor(Sprite sprite, Vector2D position) {
        super(sprite, position);
    }

    /**
     * Returns the hit points.
     *
     * @return the hit points.
     */
    public int getHitPoints() {
        return hitPoints;
    }

    /**
     * Sets the hit points.
     *
     * @param hitPoints the hit points to set.
     */
    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    /**
     * Returns is alive.
     *
     * @return is alive.
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Sets is alive.
     *
     * @param alive true or false, representing if it is alive.
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
        if(!alive && onDeath != null) {
            onDeath.run();
        }
    }

    /**
     * This function get called when the hit points get zero.
     *
     * @param onDeath The method to run.
     */
    public void setOnDeath(Runnable onDeath) {
        this.onDeath = onDeath;
    }

    /**
     * Gives damage on collide.
     *
     * @param damage the amount of damage it gets.
     */
    public void onCollides(int damage) {
        if(isAlive()) {
            if(hitPoints - damage <= 0) {
                hitPoints = 0;
                setAlive(false);
            } else {
                hitPoints -= damage;
            }
        }
    }

    /**
     * The logic for the actor.
     */
    @Override
    public void update() {
        if(alive) {
            AnimatedSprite.stepIfAnimatedSprite(sprite);
        }
    }

    /**
     * Reset method for the actor.
     */
    public abstract void reset();

    /**
     * Returns the String representation of this instance.
     *
     * @return String representation of this instance.
     */
    @Override
    public String toString() {
        return "Actor{" +
                "hitPoints=" + hitPoints +
                ", alive=" + alive +
                ", onCollide=" + onCollide +
                ", onDeath=" + onDeath +
                ", spriteFlip=" + spriteFlip +
                '}';
    }
}
