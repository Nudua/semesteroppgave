package com.groupname.game.entities;
import com.groupname.framework.audio.SoundPlayer;
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
import com.groupname.game.entities.projectiles.SingleBulletWeaponEx;
import com.groupname.game.input.PlayerInputDefinitions;
import javafx.scene.shape.Rectangle;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class Player extends Actor {

    public static final int DEFAULT_MAX_HEARTS = 5;

    private final InputManager inputManager;
    private final Vector2D initialPosition;
    private double speed = 10.5d;
    private SpriteFlip spriteFlip = SpriteFlip.NONE;
    private EnumSet<Direction> direction = EnumSet.of(Direction.Right);
    private SingleBulletWeaponEx currentWeapon;
    private double pushBack = 150;
    private int maxHitpoints = DEFAULT_MAX_HEARTS;

    public Player(Sprite sprite, Vector2D position, InputManager inputManager) {
        super(sprite, position);
        this.inputManager = inputManager;
        initialPosition = new Vector2D(position);

        createWeapon();
    }

    public int getMaxHitpoints() {
        return maxHitpoints;
    }

    public void setMaxHitpoints(int maxHitpoints) {
        this.maxHitpoints = maxHitpoints;
    }

    private void createWeapon() {
        currentWeapon = new SingleBulletWeaponEx(20, 1);
    }

    @Override
    public void reset() {
        position.set(initialPosition);
        setHitPoints(5);
    }

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
            currentWeapon.fire(new Vector2D(position.getX() + sprite.getWidth() / 2, position.getY()), Direction.Right);
        } else if(inputManager.isDown(PlayerInputDefinitions.SHOOT_LEFT)) {
            currentWeapon.fire(new Vector2D(position.getX() + sprite.getWidth() / 2, position.getY()), Direction.Left);
        } else if(inputManager.isDown(PlayerInputDefinitions.SHOOT_DOWN)) {
            currentWeapon.fire(new Vector2D(position.getX() + sprite.getWidth() / 2, position.getY()), Direction.Down);
        } else if(inputManager.isDown(PlayerInputDefinitions.SHOOT_UP)) {
            currentWeapon.fire(new Vector2D(position.getX() + sprite.getWidth() / 2, position.getY()), Direction.Up);
        }

        /*
        if(inputManager.isDown(PlayerInputDefinitions.SHOOT_RIGHT)) {
            currentWeapon.setDirection(Direction.Right);
            currentWeapon.fire(new Vector2D(position.getX() + sprite.getWidth() / 2, position.getY()), Direction.Right);
        } else if(inputManager.isDown(PlayerInputDefinitions.SHOOT_LEFT)) {
            currentWeapon.setDirection(Direction.Left);
            currentWeapon.fire(new Vector2D(position.getX() + sprite.getWidth() / 2, position.getY()));
        } else if(inputManager.isDown(PlayerInputDefinitions.SHOOT_DOWN)) {
            currentWeapon.setDirection(Direction.Down);
            currentWeapon.fire(new Vector2D(position.getX() + sprite.getWidth() / 2, position.getY()));
        } else if(inputManager.isDown(PlayerInputDefinitions.SHOOT_UP)) {
            currentWeapon.setDirection(Direction.Up);
            currentWeapon.fire(new Vector2D(position.getX() + sprite.getWidth() / 2, position.getY()));
        }
        */

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
            setDirection(Direction.Left);
            isMoving = true;
        } else if (inputManager.isDown((PlayerInputDefinitions.RIGHT))) {
            if(position.getX() + sprite.getWidth() + speed >= levelBounds.getX() + levelBounds.getWidth()) {
                position.setX(levelBounds.getX() + levelBounds.getWidth() - sprite.getWidth());
            } else {
                position.addX(speed);
            }

            spriteFlip = SpriteFlip.HORIZONTAL;
            setDirection(Direction.Right);
            isMoving = true;
        }

        if(inputManager.isDown(PlayerInputDefinitions.UP)) {
            if(position.getY() <= levelBounds.getY()) {
                position.setY(levelBounds.getY());
            } else {
                position.addY(-speed);
            }

            setDirection(Direction.Up);
            isMoving = true;
        } else if (inputManager.isDown(PlayerInputDefinitions.DOWN)) {

            if(position.getY() + sprite.getHeight() >= levelBounds.getY() + levelBounds.getHeight()) {
                position.setY(levelBounds.getY() + levelBounds.getHeight() - sprite.getHeight());
            } else {
                position.addY(speed);
            }

            setDirection(Direction.Down);
            isMoving = true;
        }

        // Remove this
        if(!isMoving) {
            direction.clear();
            direction.add(Direction.Left);
        }
    }

    private void setDirection(Direction newDirection) {
        direction.add(newDirection);

        switch (newDirection) {
            case Right:
                direction.remove(Direction.Left);
                break;
            case Left:
                direction.remove(Direction.Right);
                break;
            case Up:
                direction.remove(Direction.Down);
                break;
            case Down:
                direction.remove(Direction.Up);
                break;
        }
    }

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
        for(Actor enemy : enemies) {
            if(enemy.isAlive()) {
                if(enemy.collides(getHitbox())) {
                    //System.out.println("CRASH");

                    if(isAlive()){
                        onCollides(1);

                        if(direction.contains(Direction.Right)){
                            position.setX(position.getX()-pushBack);
                        } else if (direction.contains(Direction.Left)){
                            position.setX(position.getX()+pushBack);
                        }
                        if(direction.contains(Direction.Up)){
                            position.setY(position.getY()+pushBack);
                        } else if (direction.contains(Direction.Down)){
                            position.setY(position.getY()-pushBack);
                        }

                    }
                }
            }
        }
    }

    private void checkPowerUpCollision(List<PowerUp> powerUps) {
        for(PowerUp item : powerUps) {
            if(!item.isCollected()) {
                if(item.collides(getHitbox())) {
                    item.onCollect(this);
                }
            }
        }
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {

        if(isAlive()) {
            spriteBatch.draw(sprite,position, EnumSet.of(spriteFlip));

            currentWeapon.draw(spriteBatch);
        }
    }
}