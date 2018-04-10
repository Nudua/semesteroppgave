package com.groupname.game.entities.projectiles;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.SpriteSheet;
import com.groupname.framework.graphics.animation.AnimatedSprite;
import com.groupname.framework.graphics.animation.AnimationFrame;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import com.groupname.framework.math.Size;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.data.AppSettings;
import com.groupname.game.entities.Actor;
import com.groupname.game.entities.Enemy;
import com.groupname.game.entities.Player;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import javax.swing.text.html.parser.Entity;
import java.util.Arrays;
import java.util.List;

public class EnemyWeapon {
    private static final String NAME = "Enemy Weapon!";
    private final double speed = 10d;
    private final int damage = 1;
    private Projectile bulletKillingPlayer;
    private Vector2D playerCurrentPosition = new Vector2D();
    private Vector2D velocity = new Vector2D();


    public EnemyWeapon(Player player) {
        createProjectiles();
    }

    protected void createProjectiles() {

        Image bulletSheet = Content.loadImage("projectiles.png", ResourceType.SpriteSheet);
        SpriteSheet bulletSpriteSheet = new SpriteSheet("projectiles", bulletSheet);

        AnimationFrame frame1 = new AnimationFrame(Sprite.createSpriteRegion(4, 0, 66, 66), 6);
        AnimationFrame frame2 = new AnimationFrame(Sprite.createSpriteRegion(3, 0, 66, 66), 6);
        AnimationFrame frame3 = new AnimationFrame(Sprite.createSpriteRegion(2, 0, 66, 66), 6);
        AnimationFrame frame4 = new AnimationFrame(Sprite.createSpriteRegion(1, 0, 66, 66), 6);

        AnimatedSprite animatedSprite = new AnimatedSprite(bulletSpriteSheet, frame1.getSpriteRegion(), Arrays.asList(frame1, frame2, frame3, frame4));

        bulletKillingPlayer = new Projectile(animatedSprite);
    }

    private boolean xPositive;
    private boolean yPositive;

    public void fire(Vector2D startPosition, Player player) {
        if (!bulletKillingPlayer.isAlive()) {
            bulletKillingPlayer.setPosition(startPosition);
            bulletKillingPlayer.setAlive(true);
            playerCurrentPosition = player.getPosition();

            int addBulletLengthX = AppSettings.SCREEN_BOUNDS.getWidth() - (int)player.getPosition().getX();
            int addBulletLengthY = AppSettings.SCREEN_BOUNDS.getHeight() - (int)player.getPosition().getY();


            if (player.getPosition().getX() < startPosition.getX()) {
                playerCurrentPosition.addX(-addBulletLengthX);
                xPositive = false;
            } else {
                playerCurrentPosition.addX(addBulletLengthX);
                xPositive = true;
            }
            if (player.getPosition().getY() < startPosition.getY()) {
                playerCurrentPosition.addY(-addBulletLengthY);
                yPositive = false;
            } else {
                playerCurrentPosition.addY(addBulletLengthY);
                yPositive = true;
            }
        }
    }


        private void mousePressedEvent(Vector2D targetPosition) {
            Vector2D playerPosition, mousePosition, aimDirection;
            double normalizedX, normalizedY;
            //Bullet bullet = new Bullet();
            //playerPosition = new Vector2D(player.getView().getTranslateX()+160, player.getView().getTranslateY()+65);
            //System.out.println("x: " + player.getView().getTranslateX() + "   " + "y: " + player.getView().getTranslateY());

            mousePosition = new Vector2D(targetPosition.getX(), targetPosition.getY());
            //System.out.println("x: " + e.getX() + "  " + "y: " + e.getY());


            aimDirection = subVector(bulletKillingPlayer.getPosition(), mousePosition);

            normalizedX = aimDirection.getX() / Math.sqrt(Math.pow(aimDirection.getX(), 2) + Math.pow(aimDirection.getY(), 2));
            normalizedY = aimDirection.getY() / Math.sqrt(Math.pow(aimDirection.getX(), 2) + Math.pow(aimDirection.getY(), 2));

            //System.out.println(normalizedX + "  " + normalizedY);
            velocity = new Vector2D( speed * normalizedX, speed * normalizedY);
            //addBullet(bullet, player.getView().getTranslateX()+160, player.getView().getTranslateY()+65);
        }

        private Vector2D subVector(Vector2D vector1, Vector2D vector2) {
            double newX = vector2.getX() - vector1.getX();
            double newY = vector2.getY() - vector1.getY();
            return new Vector2D(newX, newY);
        }



    public void checkCollision(Player player) {
        if(bulletKillingPlayer.isAlive() && player.isAlive()) {
            if(bulletKillingPlayer.collides(player.getHitbox())) {
                player.onCollides(damage);
                bulletKillingPlayer.setAlive(false);
            }
        }
    }


    public void update(Player player) {
        Vector2D position = bulletKillingPlayer.getPosition();
        Size screenBounds = new Size(1280, 720);

        if (bulletKillingPlayer.isAlive()) {
            bulletKillingPlayer.update();
            mousePressedEvent(playerCurrentPosition);
            bulletKillingPlayer.getPosition().add(velocity.getX(), velocity.getY());
        }


        double currentSpeed = speed;
        double x = bulletKillingPlayer.getPosition().getX();
        double y = bulletKillingPlayer.getPosition().getY();

        double playerX = playerCurrentPosition.getX();
        double playerY = playerCurrentPosition.getY();






        //X-axis
        if(position.getX() + bulletKillingPlayer.getSprite().getWidth() > screenBounds.getWidth() || position.getX() < 0) {
            bulletKillingPlayer.setAlive(false);
        }

        //Y-axis
        if(position.getY() < 0 || position.getY() + bulletKillingPlayer.getSprite().getHeight() > screenBounds.getHeight()) {
            bulletKillingPlayer.setAlive(false);
        }

        bulletKillingPlayer.update();
    }

    public void draw(SpriteBatch spriteBatch) {
        bulletKillingPlayer.draw(spriteBatch);
    }

}
