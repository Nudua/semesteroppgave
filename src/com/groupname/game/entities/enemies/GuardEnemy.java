package com.groupname.game.entities.enemies;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.animation.improved.AnimatedSprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.math.Direction;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.entities.Enemy;

public class GuardEnemy extends Enemy {
    private final Vector2D endPosition;
    private final Vector2D startPosition;
    private int walkDistance = 500;
    private double speed = 1.4d;
    private Direction direction = Direction.Right;

    public GuardEnemy(Sprite sprite, Vector2D position, int hitPoints) {
        super(sprite, position, hitPoints);
        endPosition = new Vector2D(position.getX() + walkDistance, position.getY());
        startPosition = new Vector2D(position);
    }

    @Override
    public void update() {
        super.update();

        if(!isAlive()) {
            return;
        }

        double x = position.getX();
        double currentSpeed = speed;
        if(direction == Direction.Right) {
            currentSpeed = speed;
        } else if(direction == Direction.Left) {
            currentSpeed = -speed;
        }

        position.setX(x + currentSpeed);

        x = position.getX();
        if(x >= endPosition.getX()) {
            direction = Direction.Left;
        } else if(x <= startPosition.getX()) {
            direction = Direction.Right;
        }
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        if(isAlive()) {
            spriteBatch.draw(sprite, position);
        }
    }

    @Override
    public void reset() {

    }
}
