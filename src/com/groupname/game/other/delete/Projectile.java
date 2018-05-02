package com.groupname.game.other.delete;

import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.animation.AnimatedSprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.math.Direction;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.data.AppSettings;


public class Projectile extends GameObject {
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


    public boolean isAlive() {
        return alive;
    }


    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    @Override
    public void update() {
        if(isAlive()) {
            // Update our sprite if it's animated
            AnimatedSprite.stepIfAnimatedSprite(sprite);
            if(position.getX() < 0) {
                alive = false;
            } else if(position.getX() > AppSettings.SCREEN_BOUNDS.getWidth())
                alive = false;
        }
        if(position.getY() < 0) {
            alive = false;
        } else if(position.getY() > AppSettings.SCREEN_BOUNDS.getHeight()) {
            alive = false;
        }


    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        if(isAlive()) {
            spriteBatch.draw(sprite, position);
        }
    }
}

