package com.groupname.game.entities.projectiles;

import com.groupname.framework.core.UpdateDrawAble;
import com.groupname.framework.math.Direction;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.entities.Actor;
import com.groupname.game.entities.SpriteFactory;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * The base class for all weapons, this class will automatically
 * create 10 projectiles and it has methods for checking collision
 * between the projectiles and Actor(s).
 */
public abstract class WeaponBase implements UpdateDrawAble {
    protected final double speed;
    protected final int damage;
    protected List<Projectile> projectiles;

    /**
     * This method must be called by a subclass to set the speed and damage for the weapon.
     *
     * @param speed the speed of the projectiles of the weapon.
     * @param damage the damage this weapon deals.
     */
    public WeaponBase(double speed, int damage) {
        this.speed = speed;
        if(damage < 0) {
            throw new InvalidParameterException("Damage cannot be less than 0.");
        }
        this.damage = damage;

        projectiles = new ArrayList<>();
        createProjectiles();
    }

    private void createProjectiles() {
        final int projectileCount = 10;

        SpriteFactory spriteFactory = new SpriteFactory();

        for(int i = 0; i < projectileCount; i++) {
            Projectile projectile = new Projectile(spriteFactory.createProjectile());
            projectiles.add(projectile);
        }
    }

    /**
     * Implementations must use this methods to fire new projectiles (if possible).
     *
     * @param startPosition the position from where the projectile will be fired.
     * @param direction the direction to fire the weapon if applicable.
     */
    public abstract void fire(Vector2D startPosition, Direction direction);

    /**
     * Checks collision between a list of enemies and the projectiles.
     *
     * @param enemies the list of actors to check for collision.
     */
    public void checkCollision(List<Actor> enemies) {
        for(Actor actor : enemies) {
            checkCollision(actor);
        }
    }

    /**
     * Returns whether there is a projectile available to fire.
     *
     * @return true if there is an projectile available to fire, false otherwise.
     */
    public boolean canFire() {
        return projectiles.stream().noneMatch(Projectile::isAlive);
    }

    /**
     * Checks collision between an actor and the active projectiles.
     *
     * @param other the actor to check for collision.
     */
    public void checkCollision(Actor other) {
        for(Projectile projectile : projectiles) {
            if(!projectile.isAlive() || !other.isAlive()) {
                continue;
            }

            if(projectile.collides(other.getHitbox())) {
                other.onCollides(damage);
                projectile.setAlive(false);
            }
        }
    }

    /**
     * Returns the String representation of this instance.
     *
     * @return String representation of this instance.
     */
    @Override
    public String toString() {
        return "WeaponBase{" +
                "speed=" + speed +
                ", damage=" + damage +
                ", projectiles=" + projectiles +
                '}';
    }
}
