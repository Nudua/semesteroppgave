package com.groupname.game.entities;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.graphics.drawing.SpriteFlip;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.input.devices.KeyboardInput;
import com.groupname.framework.math.Direction;
import com.groupname.framework.math.Size;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.entities.Actor;
import com.groupname.game.entities.projectiles.LinearBulletProjectile;
import com.groupname.game.entities.projectiles.Projectile;
import com.groupname.game.input.PlayerInputDefinitions;

import java.util.EnumSet;
import java.util.Objects;

public class Player extends Actor {
    private final InputManager inputManager;
    private final Vector2D initialPosition;
    private double speed = 8.5d;
    private SpriteFlip spriteFlip = SpriteFlip.NONE;

    private Sprite projectileSprite;
    private Projectile bullet1;

    public Player(Sprite sprite, Vector2D position, int hitPoints, InputManager inputManager, Sprite projectileSprite) {
        super(sprite, position, hitPoints);
        this.inputManager = inputManager;
        initialPosition = new Vector2D(position);
        this.projectileSprite = Objects.requireNonNull(projectileSprite);

        createProjectiles();
    }

    private void createProjectiles() {
        bullet1 = new LinearBulletProjectile("bullet1", projectileSprite, new Vector2D(0,0), new Size(1280,720));
    }

    @Override
    public void onCollide() {

    }

    @Override
    public void reset() {
        position.set(initialPosition);
    }

    @Override
    public void onDeath() {

    }

    @Override
    public void update() {
        double x = position.getX();
        double y = position.getY();

        if(inputManager.isDown(PlayerInputDefinitions.LEFT)) {
            position.setX(x - speed);
            spriteFlip = SpriteFlip.NONE;
        } else if (inputManager.isDown((PlayerInputDefinitions.RIGHT))) {
            position.setX(x + speed);
            spriteFlip = SpriteFlip.HORIZONTAL;
        }

        if(inputManager.isDown(PlayerInputDefinitions.UP)) {
            position.setY(y - speed);
        } else if (inputManager.isDown(PlayerInputDefinitions.DOWN)) {
            position.setY(y + speed);
        }

        if(inputManager.isDown(PlayerInputDefinitions.SHOOT_RIGHT)) {
            if(!bullet1.isAlive()) {
                bullet1.fire(new Vector2D(position.getX() + sprite.getWidth() / 2, position.getY()), Direction.Right);
            }
        } else if(inputManager.isDown(PlayerInputDefinitions.SHOOT_LEFT)) {
            if(!bullet1.isAlive()) {
                bullet1.fire(new Vector2D(position.getX() + sprite.getWidth() / 2, position.getY()), Direction.Left);
            }
        } else if(inputManager.isDown(PlayerInputDefinitions.SHOOT_DOWN)) {
            if(!bullet1.isAlive()) {
                bullet1.fire(new Vector2D(position.getX() + sprite.getWidth() / 2, position.getY()), Direction.Down);
            }
        } else if(inputManager.isDown(PlayerInputDefinitions.SHOOT_UP)) {
            if(!bullet1.isAlive()) {
                bullet1.fire(new Vector2D(position.getX() + sprite.getWidth() / 2, position.getY()), Direction.Up);
            }
        }

        bullet1.update();
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(sprite,position, EnumSet.of(spriteFlip));

        if(bullet1.isAlive()) {
            bullet1.draw(spriteBatch);
        }
    }
}