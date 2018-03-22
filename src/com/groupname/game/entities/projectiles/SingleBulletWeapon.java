package com.groupname.game.entities.projectiles;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.SpriteSheet;
import com.groupname.framework.graphics.animation.AnimatedSprite;
import com.groupname.framework.graphics.animation.AnimationFrame;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import com.groupname.framework.math.Direction;
import com.groupname.framework.math.Size;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.entities.Actor;
import com.groupname.game.entities.Enemy;
import javafx.scene.image.Image;

import java.util.Arrays;
import java.util.List;

public class SingleBulletWeapon extends WeaponBase {

    public static final String NAME = "Single Bullet Weapon!";
    private final double speed = 20d;
    private final int damage = 1;
    private Projectile myOnlyBullet;

    public SingleBulletWeapon() {
        createProjectiles();
    }

    protected void createProjectiles() {
        Image bulletSheet = Content.loadImage("projectiles.png", ResourceType.SpriteSheet);
        SpriteSheet bulletSpriteSheet = new SpriteSheet("projectiles", bulletSheet);

        //Sprite myOnlyBulletSprite = new Sprite("MyOnlyBullet", bulletSpriteSheet, Sprite.createSpriteRegion(66,66));

        AnimationFrame frame1 = new AnimationFrame(Sprite.createSpriteRegion(0, 0, 66, 66), 6);
        AnimationFrame frame2 = new AnimationFrame(Sprite.createSpriteRegion(1, 0, 66, 66), 6);
        AnimationFrame frame3 = new AnimationFrame(Sprite.createSpriteRegion(3, 0, 66, 66), 6);
        AnimationFrame frame4 = new AnimationFrame(Sprite.createSpriteRegion(4, 0, 66, 66), 6);

        AnimatedSprite animatedSprite = new AnimatedSprite(bulletSpriteSheet, frame1.getSpriteRegion(), Arrays.asList(frame1, frame2, frame3, frame4));

        myOnlyBullet = new Projectile(animatedSprite);
    }

    @Override
    public void fire(Vector2D startPosition, Direction direction) {
        if(!isEnabled()) {
            return;
        }

        if(!myOnlyBullet.isAlive()) {
            myOnlyBullet.setPosition(startPosition);
            myOnlyBullet.setDirection(direction);
            myOnlyBullet.setAlive(true);
        }
    }

    @Override
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
