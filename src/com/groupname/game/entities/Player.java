package com.groupname.game.entities;
import com.groupname.framework.core.GameObject;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.graphics.drawing.SpriteFlip;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.math.Direction;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.data.AppSettings;
import com.groupname.game.entities.powerups.PowerUp;
import com.groupname.game.entities.projectiles.SingleBulletWeapon;
import com.groupname.game.input.PlayerInputDefinitions;
import javafx.scene.shape.Rectangle;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This is the player class, used to control our hero via the inputManager.
 * It also handles firing weapon and checking for collision between enemies and powerups.
 */
public class Player extends Actor {

    private static final int DEFAULT_MAX_HEARTS = 5;
    private static final double KNOCKBACK_AMOUNT = 150;

    private final InputManager inputManager;
    private final Vector2D initialPosition;

    private double speed = 10.5d;
    private SpriteFlip spriteFlip = SpriteFlip.NONE;

    private EnumSet<Direction> direction = EnumSet.of(Direction.RIGHT);
    private SingleBulletWeapon currentWeapon;

    private int maxHitpoints = DEFAULT_MAX_HEARTS;

    /**
     * Creates a new instance of our Player with the specified sprite, the starting position of the player
     * and the inputManager used to control this player.
     *
     * @param sprite the sprite used for this instance.
     * @param position the initial position of this player.
     * @param inputManager the inputManager to use for controlling this player.
     */
    public Player(Sprite sprite, Vector2D position, InputManager inputManager) {
        super(sprite, position);
        this.inputManager = inputManager;
        initialPosition = new Vector2D(position);

        createWeapon();
    }

    /**
     * Returns the max amount of hitpoints that this player can have.
     *
     * @return the max amount of hitpoints that this player can have.
     */
    public int getMaxHitpoints() {
        return maxHitpoints;
    }

    private void createWeapon() {
        currentWeapon = new SingleBulletWeapon(20, 1);
    }

    /**
     * Resets the player to it's original position and hitpoints.
     */
    @Override
    public void reset() {
        position.set(initialPosition);
        setHitPoints(5);
    }

    /**
     * Updates logic for this player. Should be called 60 times per second.
     */
    @Override
    public void update() {
        // Step our animation if needed
        super.update();

        if(!isAlive()) {
            return;
        }

        handleMovement();
        handleWeapon();
    }

    private void handleWeapon() {
        if(!currentWeapon.canFire()) {
            currentWeapon.update();
            return;
        }

        if(inputManager.isDown(PlayerInputDefinitions.SHOOT_RIGHT)) {
            currentWeapon.fire(new Vector2D(position.getX() + sprite.getWidth() / 2, position.getY()), Direction.RIGHT);
        } else if(inputManager.isDown(PlayerInputDefinitions.SHOOT_LEFT)) {
            currentWeapon.fire(new Vector2D(position.getX() + sprite.getWidth() / 2, position.getY()), Direction.LEFT);
        } else if(inputManager.isDown(PlayerInputDefinitions.SHOOT_DOWN)) {
            currentWeapon.fire(new Vector2D(position.getX() + sprite.getWidth() / 2, position.getY()), Direction.DOWN);
        } else if(inputManager.isDown(PlayerInputDefinitions.SHOOT_UP)) {
            currentWeapon.fire(new Vector2D(position.getX() + sprite.getWidth() / 2, position.getY()), Direction.UP);
        }

        currentWeapon.update();
    }

    private void handleMovement() {
        Rectangle levelBounds = AppSettings.LEVEL_BOUNDS;

        boolean isMoving = false;

        if(inputManager.isDown(PlayerInputDefinitions.LEFT)) {

            if(position.getX() - speed <= levelBounds.getX()) {
                position.setX(levelBounds.getX());
            } else {
                position.addX(-speed);
            }

            spriteFlip = SpriteFlip.NONE;
            setDirection(Direction.LEFT);
            isMoving = true;
        } else if (inputManager.isDown((PlayerInputDefinitions.RIGHT))) {
            if(position.getX() + sprite.getWidth() + speed >= levelBounds.getX() + levelBounds.getWidth()) {
                position.setX(levelBounds.getX() + levelBounds.getWidth() - sprite.getWidth());
            } else {
                position.addX(speed);
            }

            spriteFlip = SpriteFlip.HORIZONTAL;
            setDirection(Direction.RIGHT);
            isMoving = true;
        }

        if(inputManager.isDown(PlayerInputDefinitions.UP)) {
            if(position.getY() <= levelBounds.getY()) {
                position.setY(levelBounds.getY());
            } else {
                position.addY(-speed);
            }

            setDirection(Direction.UP);
            isMoving = true;
        } else if (inputManager.isDown(PlayerInputDefinitions.DOWN)) {

            if(position.getY() + sprite.getHeight() >= levelBounds.getY() + levelBounds.getHeight()) {
                position.setY(levelBounds.getY() + levelBounds.getHeight() - sprite.getHeight());
            } else {
                position.addY(speed);
            }

            setDirection(Direction.DOWN);
            isMoving = true;
        }

        // Remove this
        if(!isMoving) {
            direction.clear();
            direction.add(Direction.LEFT);
        }
    }

    private void setDirection(Direction newDirection) {
        direction.add(newDirection);

        switch (newDirection) {
            case RIGHT:
                direction.remove(Direction.LEFT);
                break;
            case LEFT:
                direction.remove(Direction.RIGHT);
                break;
            case UP:
                direction.remove(Direction.DOWN);
                break;
            case DOWN:
                direction.remove(Direction.UP);
                break;
        }
    }

    /**
     * Checks collision between the player and different gameobjects such as enemies and powerups.
     * Also checks collision between our weapon's bullets and enemies.
     *
     * @param gameObjects the list of gameobjects to check for collision.
     */
    public void checkCollision(List<GameObject> gameObjects) {

        // Get a list of all the GameObjects that are actually of the Enemy class that are still alive
        List<Actor> enemies = gameObjects.stream()
                .filter(n -> n instanceof Enemy && ((Actor) n).isAlive())
                .map((n) -> (Actor) n)
                .collect(Collectors.toList());

        // Check collision between the weapon projectiles and the enemies
        currentWeapon.checkCollision(enemies);


        // Check collision between player and enemies
        checkEnemyCollision(enemies);

        // Write generic class?
        List<PowerUp> powerUps = gameObjects.stream()
                .filter(n -> n instanceof PowerUp && !((PowerUp) n).isCollected())
                .map((n) -> (PowerUp) n)
                .collect(Collectors.toList());

        checkPowerUpCollision(powerUps);
    }

    private void checkEnemyCollision(List<Actor> enemies) {
        assert enemies != null;
        assert enemies.size() != 0;

        for(Actor enemy : enemies) {
            if(enemy.isAlive()) {
                if(enemy.collides(getHitbox())) {

                    if(isAlive()){
                        onCollides(1);

                        // Knockback our player if we collide with an enemy
                        if(direction.contains(Direction.RIGHT)){
                            position.setX(position.getX()- KNOCKBACK_AMOUNT);
                        } else if (direction.contains(Direction.LEFT)){
                            position.setX(position.getX()+ KNOCKBACK_AMOUNT);
                        }
                        if(direction.contains(Direction.UP)){
                            position.setY(position.getY()+ KNOCKBACK_AMOUNT);
                        } else if (direction.contains(Direction.DOWN)){
                            position.setY(position.getY()- KNOCKBACK_AMOUNT);
                        }

                    }
                }
            }
        }
    }

    // Checks if our player is picking up any powerups
    private void checkPowerUpCollision(List<PowerUp> powerUps) {
        assert powerUps != null;

        for(PowerUp item : powerUps) {
            if(!item.isCollected()) {
                if(item.collides(getHitbox())) {
                    item.onCollect(this);
                }
            }
        }
    }

    /**
     * Used to draw this player using the specified spriteBatch instance.
     *
     * @param spriteBatch the spriteBatch used to draw this gameObject.
     */
    @Override
    public void draw(SpriteBatch spriteBatch) {
        Objects.requireNonNull(spriteBatch);

        if(isAlive()) {
            spriteBatch.draw(sprite,position, EnumSet.of(spriteFlip));

            currentWeapon.draw(spriteBatch);
        }
    }

    /**
     * Returns the String representation of this instance.
     *
     * @return String representation of this instance.
     */
    @Override
    public String toString() {
        return super.toString() + "Player{" +
                "inputManager=" + inputManager +
                ", initialPosition=" + initialPosition +
                ", speed=" + speed +
                ", spriteFlip=" + spriteFlip +
                ", direction=" + direction +
                ", currentWeapon=" + currentWeapon +
                ", maxHitpoints=" + maxHitpoints +
                '}';
    }
}