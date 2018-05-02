package com.groupname.game.entities.projectiles;

import com.groupname.framework.core.UpdateDrawAble;
import com.groupname.framework.math.Direction;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.entities.Actor;
import com.groupname.game.entities.SpriteFactory;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public abstract class WeaponBase implements UpdateDrawAble {
    protected final double speed;
    protected final int damage;
    protected List<ProjectileEx> projectiles;

    public WeaponBase(double speed, int damage) {
        this.speed = speed;
        if(damage < 0) {
            throw new InvalidParameterException("Damage cannot be less than 0.");
        }
        this.damage = damage;

        projectiles = new ArrayList<>();
        createProjectiles();
    }

    protected void createProjectiles() {
        final int projectileCount = 10;

        SpriteFactory spriteFactory = new SpriteFactory();

        for(int i = 0; i < projectileCount; i++) {
            ProjectileEx projectile = new ProjectileEx(spriteFactory.createProjectile());
            projectiles.add(projectile);
        }
    }

    public abstract void fire(Vector2D startPosition, Direction direction);

    protected abstract void updateProjectileLogic();

    public void checkCollision(List<Actor> enemies) {
        for(Actor actor : enemies) {
            checkCollision(actor);
        }
    }

    // look over
    public boolean canFire() {
        return projectiles.stream().anyMatch(ProjectileEx::isAlive);
    }

    public void checkCollision(Actor other) {
        for(ProjectileEx projectile : projectiles) {
            if(!projectile.isAlive() || !other.isAlive()) {
                continue;
            }

            if(projectile.collides(other.getHitbox())) {
                other.onCollides(damage);
                projectile.setAlive(false);
            }
        }
    }
}
