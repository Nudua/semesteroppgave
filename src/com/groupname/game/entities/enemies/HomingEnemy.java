package com.groupname.game.entities.enemies;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.math.Counter;
import com.groupname.framework.math.Direction;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.entities.Enemy;
import com.groupname.game.entities.Player;
import com.groupname.game.entities.projectiles.EnemyWeapon;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class extends Enemy.
 * HomingEnemy is an enemy that follows the player
 * and shoot in the direction of the player.
 */
public class HomingEnemy extends Enemy {
    private final Vector2D startPosition;
    private Player player;
    private EnemyWeapon currentWeapon;
    private double speed = 1.0d;
    private int frequency = 1;
    private Counter counter = new Counter(frequency);
    ThreadLocalRandom random = ThreadLocalRandom.current();


    /**
     * This is the constructor for the HomingEnemy.
     *
     * @param sprite is an super from Enemy. Sets an default sprite for the enemy.
     * @param position is an super from Enemy. Sets the start position, on the canvas.
     * @param player is the player. This is for accessing the players position.
     */
    public HomingEnemy(Sprite sprite, Vector2D position, Player player) {
        super(sprite, position);
        startPosition = new Vector2D(position);
        this.player = Objects.requireNonNull(player);

        createWeapon();
    }

    /**
     * Makes it easy to set the walking speed for the enemy.
     *
     * @param speed is the speed that we add/subtract to the position per frame/update in the logic.
     */
    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Setter for the frequency the enemy shoots.
     *
     * @param frequency frequency in seconds.
     */
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    private void createWeapon() {
        currentWeapon = new EnemyWeapon(player, 7, 1);
    }

    /**
     * The specific logic for this type of enemy.
     * The HomingEnemy takes the players position and will always.
     * We have a delay that trigger the shooting for instance every 60 frame or 120 frame.
     */
    @Override
    public void update() {
        if(!isAlive()) {
            return;
        }

        super.update();

        if(!player.isAlive()) {
            return;
        }

        if(player.getPosition().getX() < getPosition().getX()) {
            position.addX(-speed / 4);
        } else if (player.getPosition().getX() > getPosition().getX()) {
            position.addX(speed / 4);
        }

        if(player.getPosition().getY() < getPosition().getY()) {
            position.addY(-speed / 4);
        } else if (player.getPosition().getY() > getPosition().getY()) {
            position.addY(speed / 4);
        }

        currentWeapon.checkCollision(player);

        counter.step();
        counter.setCounter(counter.getCounter() + random.nextInt(1, 10));
        if(counter.isDone()) {
            currentWeapon.fire(position, Direction.NONE);
            counter.reset();
        }

        currentWeapon.update();
    }

    /**
     * Draws the enemy if it is alive.
     *
     * @param spriteBatch draws the given sprite at the specified position
     */
    @Override
    public void draw(SpriteBatch spriteBatch) {
        if(isAlive()) {
            spriteBatch.draw(sprite, position);
            currentWeapon.draw(spriteBatch);
        }
    }


    /**
     * This method is an easy way for resetting this enemy.
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
        return "HomingEnemy{" +
                "startPosition=" + startPosition +
                ", player=" + player +
                ", currentWeapon=" + currentWeapon +
                ", speed=" + speed +
                ", frequency=" + frequency +
                ", counter=" + counter +
                ", random=" + random +
                '}';
    }
}
