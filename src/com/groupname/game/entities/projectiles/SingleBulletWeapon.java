package com.groupname.game.entities.projectiles;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.math.Direction;
import com.groupname.framework.math.Size;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.entities.Actor;
import com.groupname.game.entities.Enemy;
import com.groupname.game.entities.Player;
import com.groupname.game.entities.SpriteFactory;

import java.util.List;

public class SingleBulletWeapon implements Weapon {

    public static final String NAME = "Single Bullet Weapon!";
    private final double speed = 20d;
    private final int damage = 1;
    private Projectile myOnlyBullet;

    public SingleBulletWeapon() {
        createProjectiles();
    }

    protected void createProjectiles() {
        SpriteFactory spriteFactory = new SpriteFactory();

        Sprite projectileSprite = spriteFactory.createProjectile();

        myOnlyBullet = new Projectile(projectileSprite);
    }

    @Override
    public void setDirection(Direction direction) {
        myOnlyBullet.setDirection(direction);
    }

    public boolean canFire() {
        return !myOnlyBullet.isAlive();
    }

    @Override
    public void fire(Vector2D startPosition) {
        if(!myOnlyBullet.isAlive()) {
            myOnlyBullet.setPosition(startPosition);
            myOnlyBullet.setAlive(true);
        }
    }

    public void checkCollision(List<Enemy> enemies) {
        for(Actor enemy : enemies) {
            if(myOnlyBullet.isAlive() && enemy.isAlive()) {
                if(myOnlyBullet.collides(enemy.getHitbox())) {
                    enemy.onCollides(damage);
                    myOnlyBullet.setAlive(false);
                }
            }
        }
    }

    public void checkCollisionPlayer(Player player) {
        if(myOnlyBullet.isAlive() && player.isAlive()) {
            if(myOnlyBullet.collides(player.getHitbox())) {
                player.onCollides(damage);
                myOnlyBullet.setAlive(false);
            }
        }
    }

    @Override
    public void update() {

        Vector2D position = myOnlyBullet.getPosition();
        Direction direction = myOnlyBullet.getDirection();
        Size screenBounds = new Size(1280, 720);

        double currentSpeed = speed;

        if(direction == Direction.Right || direction == Direction.Down) {
            currentSpeed = speed;
        } else if(direction == Direction.Left || direction == Direction.Up) {
            currentSpeed = -speed;
        }

        if(direction == Direction.Up || direction == Direction.Down) {
            position.addY(currentSpeed);
        } else {
            position.addX(currentSpeed);
        }

        //X-axis
        if(position.getX() + myOnlyBullet.getSprite().getWidth() > screenBounds.getWidth() || position.getX() < 0) {
            myOnlyBullet.setAlive(false);
        }

        //Y-axis
        if(position.getY() < 0 || position.getY() + myOnlyBullet.getSprite().getHeight() > screenBounds.getHeight()) {
            myOnlyBullet.setAlive(false);
        }

        myOnlyBullet.update();
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        myOnlyBullet.draw(spriteBatch);
    }
}
