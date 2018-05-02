package com.groupname.game.entities.projectiles;

import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.math.Direction;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.entities.Actor;

import java.util.Objects;

/*
    private final double speed = 15d;
    private final int damage = 1;
 */
public class EnemyWeapon extends WeaponBase {

    private final Actor target;
    private final Projectile projectile;
    private Vector2D targetPosition = new Vector2D();
    private Vector2D velocity = new Vector2D();

    public EnemyWeapon(Actor target, double speed, int damage) {
        super(speed, damage);
        this.target = Objects.requireNonNull(target);
        projectile = projectiles.get(0);
    }

    @Override
    public void fire(Vector2D startPosition, Direction direction) {
        Objects.requireNonNull(startPosition);

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

    private void updateProjectileLogic() {
        if (projectile.isAlive()) {
            projectile.update();
            calculateVelocity();

            Vector2D projectilePosition = projectile.getPosition();
            projectilePosition.add(velocity.getX(), velocity.getY());

            projectile.setPosition(projectilePosition);
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

    @Override
    public void draw(SpriteBatch spriteBatch) {
        projectile.draw(spriteBatch);
    }

    @Override
    public void update() {
        updateProjectileLogic();
    }
}
