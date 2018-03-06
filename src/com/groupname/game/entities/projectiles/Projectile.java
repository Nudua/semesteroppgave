package com.groupname.game.entities.projectiles;

import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.math.Direction;
import com.groupname.framework.math.Vector2D;
import com.groupname.framework.util.Strings;

public abstract class Projectile extends GameObject {

    private final String name;

    private double speed;
    private boolean alive = false;

    public Projectile(String name, Sprite sprite, Vector2D position) {
        super(sprite, position);
        this.name = Strings.requireNonNullAndNotEmpty(name);
    }

    public String getName() {
        return name;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public abstract void fire(Vector2D position, Direction direction);
}
