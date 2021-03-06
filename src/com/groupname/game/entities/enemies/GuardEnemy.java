package com.groupname.game.entities.enemies;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.graphics.drawing.SpriteFlip;
import com.groupname.framework.math.Counter;
import com.groupname.framework.math.Direction;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.data.AppSettings;
import com.groupname.game.entities.Enemy;
import javafx.scene.shape.Rectangle;

import java.util.EnumSet;
import java.util.Objects;

/**
 * GuardEnemy is an enemy that patrols from A to B.
 * This class extends Enemy.
 */
public class GuardEnemy extends Enemy {
    private Vector2D endPosition;
    private Vector2D startPosition;
    private int walkDistance = 200;
    private double speed = 1.4d;
    private Direction direction = Direction.RIGHT;

    /**
     * Creates a new instance of this class with the specific parameters.
     *
     * @param sprite The sprite of the object.
     * @param position The start position of the object.
     */
    public GuardEnemy(Sprite sprite, Vector2D position) {
        super(sprite, position);
        endPosition = new Vector2D(position.getX() + walkDistance, position.getY());
        startPosition = new Vector2D(position);
        clampWalkingPath();
    }

    /**
     * Setter that sets the position of the object.
     *
     * @param position an Vector2D that is the position on the canvas.
     */
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

    /**
     * Method for the event on collide.
     * Enemies give damage on collide.
     *
     * @param damage the amount of damage it gets.
     */
    @Override
    public void onCollides(int damage) {
        super.onCollides(damage);

    }

    /**
     * Sets the speed of the enemy.
     *
     * @param speed takes a double for the speed of the enemy.
     */
    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * The specific logic for this type of enemy.
     * The GuardEnemy walks from A to B.
     */
    @Override
    public void update() {
        super.update();

        if(!isAlive()) {
            return;
        }

        double x = position.getX();
        double currentSpeed = speed;
        if(direction == Direction.RIGHT) {
            currentSpeed = speed;
        } else if(direction == Direction.LEFT) {
            currentSpeed = -speed;
        }

        position.setX(x + currentSpeed);

        x = position.getX();
        if(x >= endPosition.getX()) {
            direction = Direction.LEFT;
        } else if(x <= startPosition.getX()) {
            direction = Direction.RIGHT;
        }
    }

    /**
     * Draws the enemy if it is alive.
     *
     * @param spriteBatch draws the given sprite at the specified position
     */
    @Override
    public void draw(SpriteBatch spriteBatch) {
        Objects.requireNonNull(spriteBatch);
        if(isAlive()) {

            spriteFlip = direction == Direction.RIGHT ? SpriteFlip.HORIZONTAL : SpriteFlip.NONE;

            spriteBatch.draw(sprite, position, EnumSet.of(spriteFlip));
        }
    }

    /**
     * Method for resetting the enemy.
     */
    @Override
    public void reset() {
    }

    /**
     * Returns the String representation of this instance.
     *
     * @return String representation of this instance.
     */
    @Override
    public String toString() {
        return super.toString() +
                "GuardEnemy{" +
                "endPosition=" + endPosition +
                ", startPosition=" + startPosition +
                ", walkDistance=" + walkDistance +
                ", speed=" + speed +
                ", direction=" + direction +
                '}';
    }
}
