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
import com.groupname.game.entities.Player;
import com.groupname.game.entities.SpriteFactory;
import javafx.scene.image.Image;

import java.util.Arrays;
import java.util.Objects;

public class EnemyWeapon implements Weapon {
    private static final String NAME = "Enemy Weapon!";
    private final double speed = 15d;
    private final int damage = 1;

    private Projectile projectile;
    private Vector2D targetPosition = new Vector2D();
    private Vector2D velocity = new Vector2D();

    private final Actor target;

    public EnemyWeapon(Actor target) {
        this.target = Objects.requireNonNull(target);
        createProjectiles();
    }

    @Override
    public void setDirection(Direction direction) {
        // Ignored in this implementation
    }

    private void createProjectiles() {
        SpriteFactory spriteFactory = new SpriteFactory();

        projectile = new Projectile(spriteFactory.createProjectile());
    }

    public void fire(Vector2D startPosition) {
        if (!projectile.isAlive() && target.isAlive()) {
            projectile.setPosition(startPosition);
            projectile.setAlive(true);
            targetPosition = new Vector2D(target.getPosition());

            //Vector2D aimDirection = subVector(projectile.getPosition(), player.getPosition());
            Vector2D aimDirection = target.getPosition().subtract(projectile.getPosition());

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
