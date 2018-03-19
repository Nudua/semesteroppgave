package com.groupname.game.entities;

import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.math.Vector2D;

public abstract class Actor extends GameObject {

    private int hitPoints;
    private boolean alive = true;

    // Think about these
    private Runnable onCollide;
    private Runnable onDeath;

    public Actor(Sprite sprite, Vector2D position, int hitPoints) {
        super(sprite, position);
        this.hitPoints = hitPoints;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void onCollides(int damage) {
        if(alive) {
            if(hitPoints - damage <= 0) {
                hitPoints = 0;
                alive = false;
            } else {
                hitPoints -= damage;
            }
        }
    }

    public abstract void reset();
}
