package com.groupname.game.entities;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.graphics.drawing.SpriteFlip;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.input.devices.KeyboardInput;
import com.groupname.framework.math.Direction;
import com.groupname.framework.math.Size;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.Scene.SceneManager;
import com.groupname.game.Scene.SceneName;
import com.groupname.game.entities.Actor;
import com.groupname.game.entities.powerups.PowerUp;
import com.groupname.game.entities.projectiles.Projectile;
import com.groupname.game.entities.projectiles.SingleBulletWeapon;
import com.groupname.game.entities.projectiles.Weapon;
import com.groupname.game.input.PlayerInputDefinitions;
import javafx.scene.Scene;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class Player extends Actor {

    public static final int DEFAULT_MAX_HEARTS = 5;

    private final InputManager inputManager;
    private final Vector2D initialPosition;
    private double speed = 10.5d;
    private SpriteFlip spriteFlip = SpriteFlip.NONE;
    private EnumSet<Direction> direction = EnumSet.of(Direction.Right);
    private Weapon currentWeapon;
    private double pushBack = 200;
    private int maxHitpoints = DEFAULT_MAX_HEARTS;

    public Player(Sprite sprite, Vector2D position, InputManager inputManager, int hitPoints) {
        super(sprite, position, hitPoints);
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
        currentWeapon = new SingleBulletWeapon();
    }

    @Override
    public void reset() {
        position.set(initialPosition);
    }

    @Override
    public void update() {

        if(!isAlive()) {
            return;
        }

        double x = position.getX();
        double y = position.getY();

        boolean isMoving = false;

        if(inputManager.isDown(PlayerInputDefinitions.LEFT)) {
            position.setX(x - speed);
            spriteFlip = SpriteFlip.NONE;
            direction.add(Direction.Left);
            direction.remove(Direction.Right);
            isMoving = true;
        } else if (inputManager.isDown((PlayerInputDefinitions.RIGHT))) {
            position.setX(x + speed);
            spriteFlip = SpriteFlip.HORIZONTAL;
            direction.add(Direction.Right);
            direction.remove(Direction.Left);
            isMoving = true;
        }

        if(inputManager.isDown(PlayerInputDefinitions.UP)) {
            position.setY(y - speed);
            direction.add(Direction.Up);
            direction.remove(Direction.Down);
            isMoving = true;
        } else if (inputManager.isDown(PlayerInputDefinitions.DOWN)) {
            position.setY(y + speed);
            direction.add(Direction.Down);
            direction.remove(Direction.Up);
            isMoving = true;
        }

        if(!isMoving) {
            direction.clear();
            direction.add(Direction.Left);
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

        currentWeapon.update();

    }

    public void checkCollision(List<Actor> enemies) {
        currentWeapon.checkCollision(enemies);
        for(Actor enemy : enemies) {
            if(enemy.isAlive()) {
                if(enemy.collides(getHitbox())) {
                    //System.out.println("CRASH");

                    if(isAlive()){
                        onCollides(1);

                        if(getHitPoints() == 0) {
                            SceneManager.INSTANCE.changeToScene(SceneName.GameOver);
                            return;
                        }

                        //System.out.println("CRASH");

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

    public void checkPowerUpCollision(List<PowerUp> powerUps) {
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