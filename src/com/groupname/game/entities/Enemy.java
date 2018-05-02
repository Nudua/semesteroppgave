package com.groupname.game.entities;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.math.Vector2D;


public abstract class Enemy extends Actor {
    protected double speed;
    protected int damage;

    public Enemy(Sprite sprite, Vector2D position) {
        super(sprite, position);
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

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
        return "Enemy{" +
                "speed=" + speed +
                ", damage=" + damage +
                '}';
    }
}
