package com.groupname.game.entities;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.math.Vector2D;

/**
 * Base class for enemies.
 * Contains the speed of the movement and the damage the enemy gives the player.
 */
public abstract class Enemy extends Actor {
    protected double speed;
    protected int damage;

    /**
     * Constructor for the object.
     *
     * @param sprite Sets an default sprite for the enemy.
     * @param position Sets the start position, on the canvas.
     */
    public Enemy(Sprite sprite, Vector2D position) {
        super(sprite, position);
    }

    /**
     * Setter for the speed of the enemy.
     *
     * @param speed takes a double for the speed of the enemy.
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Setter for the damage the enemy gives the player.
     *
     * @param damage takes a int that represent how many life the enemy takes from the player.
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Returns the String representation of this instance.
     *
     * @return String representation of this instance.
     */
    @Override
    public String toString() {
        return super.toString() +
                "Enemy{" +
                "speed=" + speed +
                ", damage=" + damage +
                '}';
    }
}
