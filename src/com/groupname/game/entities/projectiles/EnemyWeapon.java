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
import com.groupname.game.entities.Player;
import javafx.scene.image.Image;

import java.util.Arrays;

public class EnemyWeapon {
    private static final String NAME = "Enemy Weapon!";
    private final double speed = 15d;
    private final int damage = 1;

    private Projectile projectile;
    private Vector2D targetPosition = new Vector2D();
    private Vector2D velocity = new Vector2D();


    public EnemyWeapon() {
        createProjectiles();
    }

    private void createProjectiles() {
        Image bulletSheet = Content.loadImage("projectiles.png", ResourceType.SpriteSheet);
        SpriteSheet bulletSpriteSheet = new SpriteSheet("projectiles", bulletSheet);

        AnimationFrame frame1 = new AnimationFrame(Sprite.createSpriteRegion(4, 0, 66, 66), 6);
        AnimationFrame frame2 = new AnimationFrame(Sprite.createSpriteRegion(3, 0, 66, 66), 6);
        AnimationFrame frame3 = new AnimationFrame(Sprite.createSpriteRegion(2, 0, 66, 66), 6);
        AnimationFrame frame4 = new AnimationFrame(Sprite.createSpriteRegion(1, 0, 66, 66), 6);

        AnimatedSprite animatedSprite = new AnimatedSprite(bulletSpriteSheet, frame1.getSpriteRegion(), Arrays.asList(frame1, frame2, frame3, frame4));

        projectile = new Projectile(animatedSprite);
    }

    public void fire(Vector2D startPosition, Player player) {
        if (!projectile.isAlive() && player.isAlive()) {
            projectile.setPosition(startPosition);
            projectile.setAlive(true);
            targetPosition = new Vector2D(player.getPosition());

            //Vector2D aimDirection = subVector(projectile.getPosition(), player.getPosition());
            Vector2D aimDirection = player.getPosition().subtract(projectile.getPosition());

            // set the position well off the screen
            targetPosition.set(targetPosition.getX() + aimDirection.getX() * 4, targetPosition.getY() + aimDirection.getY() * 4);
        }
    }


    private void calculateVelocity() {
        Vector2D aimDirection = targetPosition.subtract(projectile.getPosition());

        double normalizedX = aimDirection.getX() / Math.sqrt(Math.pow(aimDirection.getX(), 2) + Math.pow(aimDirection.getY(), 2));
        double normalizedY = aimDirection.getY() / Math.sqrt(Math.pow(aimDirection.getX(), 2) + Math.pow(aimDirection.getY(), 2));

        /*
        double hypotenuse = Math.sqrt((aimDirection.getX() * aimDirection.getX()) + (aimDirection.getY() * aimDirection.getY()));
        System.out.println("Hyp:" + hypotenuse);
        */

        //if (hypotenuse > 10) {
            velocity = new Vector2D(speed * normalizedX, speed * normalizedY);
        //}
    }

    public void checkCollision(Player player) {
        if(projectile.isAlive() && player.isAlive()) {
            if(projectile.collides(player.getHitbox())) {
                player.onCollides(damage);
                projectile.setAlive(false);
            }
        }
    }


    public void update() {
        if (projectile.isAlive()) {
            projectile.update();
            calculateVelocity();
            projectile.getPosition().add(velocity.getX(), velocity.getY());
        }
    }

    public void draw(SpriteBatch spriteBatch) {
        projectile.draw(spriteBatch);
    }

}
