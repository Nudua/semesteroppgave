package com.groupname.game.entities.enemies;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.graphics.drawing.SpriteFlip;
import com.groupname.framework.math.Direction;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.data.AppSettings;
import com.groupname.game.entities.Enemy;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.EnumSet;

public class GuardEnemy extends Enemy {
    private Vector2D endPosition;
    private Vector2D startPosition;
    private int walkDistance = 200;
    private double speed = 1.4d;
    private Direction direction = Direction.Right;

    public GuardEnemy(Sprite sprite, Vector2D position) {
        super(sprite, position);
        endPosition = new Vector2D(position.getX() + walkDistance, position.getY());
        startPosition = new Vector2D(position);
        clampWalkingPath();
    }

    @Override
    public void setPosition(Vector2D position) {
        endPosition = new Vector2D(position.getX() + walkDistance, position.getY());
        startPosition = new Vector2D(position);
        this.position.set(position);
        clampWalkingPath();
    }


    private void clampWalkingPath() {
        Rectangle levelBounds = AppSettings.LEVEL_BOUNDS;

        if(startPosition.getX() <= levelBounds.getX()) {
            startPosition.setX(levelBounds.getX() + 2);
        }

        if(endPosition.getX() + sprite.getWidth() >= levelBounds.getX() + levelBounds.getWidth()) {
            endPosition.setX(levelBounds.getX() + levelBounds.getWidth() - sprite.getWidth() - 2);
        }
    }


    public void setSpeed(double speed) {
        this.speed = speed;
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

            spriteFlip = direction == Direction.Right ? SpriteFlip.HORIZONTAL : SpriteFlip.NONE;

            spriteBatch.draw(sprite, position, EnumSet.of(spriteFlip));
        }
    }

    @Override
    public void reset() {

    }
}
