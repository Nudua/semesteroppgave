package com.groupname.game.entities.projectiles;

import com.groupname.framework.audio.SoundPlayer;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.math.Direction;
import com.groupname.framework.math.Size;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.entities.Actor;

import java.util.Objects;

/**
 * This class represents a weapon with a single projectile.
 */
public class SingleBulletWeapon extends WeaponBase {

    private Direction bulletDirection = Direction.NONE;

    /**
     * Creates a new instance of this class with the specified speed (for the projectile)
     * and damage.
     *
     * @param speed the speed for the projectile.
     * @param damage the damage for the projectile.
     */
    public SingleBulletWeapon(double speed, int damage) {
        super(speed, damage);
    }

    /**
     * Fires a new projectile (if available) in the requested direction.
     *
     * @param startPosition the position from where the projectile will be fired.
     * @param direction the direction to fire the weapon if applicable.
     */
    @Override
    public void fire(Vector2D startPosition, Direction direction) {
        Objects.requireNonNull(startPosition);

        Projectile myOnlyBullet = projectiles.get(0);

        this.bulletDirection = Objects.requireNonNull(direction);

        if(!myOnlyBullet.isAlive()) {
            SoundPlayer.INSTANCE.playSoundEffect(SoundPlayer.SoundEffect.SHOOT);
            myOnlyBullet.setPosition(startPosition);
            myOnlyBullet.setAlive(true);
        }
    }

    private void updateProjectileLogic() {
        Projectile myOnlyBullet = projectiles.get(0);

        Vector2D position = myOnlyBullet.getPosition();
        Size screenBounds = new Size(1280, 720);

        double currentSpeed = speed;

        if(bulletDirection == Direction.RIGHT || bulletDirection == Direction.DOWN) {
            currentSpeed = speed;
        } else if(bulletDirection == Direction.LEFT || bulletDirection == Direction.UP) {
            currentSpeed = -speed;
        }

        if(bulletDirection == Direction.UP || bulletDirection == Direction.DOWN) {
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

        myOnlyBullet.setPosition(position);

        myOnlyBullet.update();
    }

    /**
     * Checks collision between an actor and the active projectiles.
     *
     * @param other the actor to check for collision.
     */
    @Override
    public void checkCollision(Actor other) {
        Projectile myOnlyBullet = projectiles.get(0);

        if(myOnlyBullet.isAlive() && other.isAlive()) {
            if(myOnlyBullet.collides(other.getHitbox())) {
                other.onCollides(damage);
                myOnlyBullet.setAlive(false);
            }
        }
    }

    /**
     * Draws the current projectile on the screen if it's alive.
     *
     * @param spriteBatch the spriteBatch used to draw.
     */
    @Override
    public void draw(SpriteBatch spriteBatch) {
        Objects.requireNonNull(spriteBatch);

        Projectile myOnlyBullet = projectiles.get(0);
        myOnlyBullet.draw(spriteBatch);
    }

    /**
     * Updates the logic for this weapon.
     * Calls the logic for the projectile.
     */
    @Override
    public void update() {
        updateProjectileLogic();
    }

    /**
     * Returns the String representation of this instance.
     *
     * @return String representation of this instance.
     */
    @Override
    public String toString() {
        return "SingleBulletWeapon{" +
                "bulletDirection=" + bulletDirection +
                '}';
    }
}
