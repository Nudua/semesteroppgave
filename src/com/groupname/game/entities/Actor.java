package com.groupname.game.entities;

import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.animation.improved.AnimatedSprite;
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
        if(!alive && onDeath != null) {
            onDeath.run();
        }
    }

    public void setOnDeath(Runnable onDeath) {
        this.onDeath = onDeath;
    }

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

    @Override
    public void update() {
        if(alive) {
            AnimatedSprite.stepIfAnimatedSprite(sprite);
        }
    }

    public abstract void reset();
}
