package com.groupname.game.entities.projectiles;

import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.animation.AnimatedSprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.math.Direction;
import com.groupname.framework.math.Vector2D;

// Needed?
interface IProjectile {
    boolean isAlive();
    void setAlive(boolean alive);

    Vector2D getPosition();
    void setPosition(Vector2D position);
}

public class Projectile extends GameObject implements IProjectile {
    private boolean alive = false;
    private Direction direction = Direction.Right;

    public Projectile(Sprite sprite) {
        super(sprite, new Vector2D());
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position.set(position);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    @Override
    public void update() {
        if(isAlive()) {
            // Update our sprite if it's animated
            AnimatedSprite.stepIfAnimatedSprite(sprite);
        }
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        if(isAlive()) {
            spriteBatch.draw(sprite, position);
        }
    }
}


/*
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
*/
