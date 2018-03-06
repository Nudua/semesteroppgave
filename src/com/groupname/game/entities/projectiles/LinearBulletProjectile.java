package com.groupname.game.entities.projectiles;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.math.Direction;
import com.groupname.framework.math.Size;
import com.groupname.framework.math.Vector2D;

import java.util.Objects;


public class LinearBulletProjectile extends Projectile {

    private final Direction direction;
    private final Size screenBounds;

    public LinearBulletProjectile(String name, Sprite sprite, Vector2D position, Direction direction, Size screenBounds) {
        super(name, sprite, position);
        this.direction = Objects.requireNonNull(direction);
        this.screenBounds = Objects.requireNonNull(screenBounds);
    }

    @Override
    public void fire() {
        setAlive(true);
    }

    @Override
    public void update() {
        if(!isAlive()) {
            return;
        }

        //Alive

        double currentSpeed = getSpeed();

        if(direction == Direction.Right || direction == Direction.Down) {
            currentSpeed = getSpeed();
        } else if(direction == Direction.Left || direction == Direction.Up) {
            currentSpeed = -getSpeed();
        }

        if(direction == Direction.Up || direction == Direction.Down) {
            position.addY(currentSpeed);
        } else {
            position.addX(currentSpeed);
        }

        //X-axis
        if(screenBounds.getWidth() > position.getX() + sprite.getWidth() || position.getX() < position.getX()) {
            setAlive(false);
        }

        //Y-axis
        if(position.getY() < 0 || position.getY() + sprite.getHeight() > screenBounds.getHeight()) {
            setAlive(false);
        }
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        if(!isAlive()) {
            return;
        }

        spriteBatch.draw(sprite, position);
    }
}
