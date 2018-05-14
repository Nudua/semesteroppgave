package com.groupname.game.entities.enemies;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.math.Direction;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.entities.Enemy;
import com.groupname.game.entities.Player;
import com.groupname.game.entities.projectiles.SingleBulletWeapon;

import java.util.Objects;

/**
 * This class extends Enemy.
 * TowerEnemy is an enemy that stands still and shoots
 * at the player a long the axis.
 */
public class TowerEnemy extends Enemy {
    private Direction shootingDirection = Direction.UP;
    private SingleBulletWeapon currentWeapon;
    private Player player;

    private int shootingA;
    private int shootingB;
    private int shootingC;
    private int shootingD;
    private boolean canShoot;

    /**
     * The constructor for an TowerEnemy. Takes a sprite, a start position and the player.
     *
     * @param sprite Sets an default sprite for the enemy.
     * @param position Sets the start position, on the canvas.
     * @param player takes the player for accessing the players position.
     */
    public TowerEnemy(Sprite sprite, Vector2D position, Player player) {
        super(sprite, position);
        this.player = Objects.requireNonNull(player);
        createWeapon();
    }

    private void createWeapon() {
        currentWeapon = new SingleBulletWeapon(20, 1);
        shootingA = (int) position.getX() - 40;
        shootingB = (int) position.getX() + 40;
        shootingC = (int) position.getY() - 40;
        shootingD = (int) position.getY() + 40;
    }

    /**
     * The specific logic for this type of enemy.
     * The TowerEnemy shoots when the player is in range.
     */
    @Override
    public void update() {
        int playerX = (int)player.getPosition().getX();
        double playerY = player.getPosition().getY();
        boolean inRangeLeft = false;
        boolean inRangeRight = false;


        super.update();

        if(!isAlive()) {
            return;
        }
        currentWeapon.update();


        // Shoots up
        if(playerY < shootingC) {
            if (shootingA < playerX) {
                inRangeLeft = true;
            } else {
                inRangeLeft = false;
            }
            if (shootingB > playerX) {
                inRangeRight = true;
            } else {
                inRangeRight = false;
            }

            if(inRangeLeft && inRangeRight) {
                shootingDirection = Direction.UP;
                canShoot = true;
            } else {
                canShoot = false;
            }
        }

        // Shoots down
        if(playerY > shootingD) {
            if (shootingA < playerX) {
                inRangeLeft = true;
            } else {
                inRangeLeft = false;
            }
            if (shootingB > playerX) {
                inRangeRight = true;
            } else {
                inRangeRight = false;
            }

            if(inRangeLeft && inRangeRight) {
                shootingDirection = Direction.DOWN;
                canShoot = true;
            } else {
                canShoot = false;
            }
        }

        // Shoots left
        if(playerX < shootingA) {
            if(shootingC < playerY) {
                inRangeLeft = true;
            } else {
                inRangeLeft = false;
            }
            if(shootingD > playerY) {
                inRangeRight = true;
            } else {
                inRangeRight = false;
            }

            if(inRangeLeft && inRangeRight) {
                shootingDirection = Direction.LEFT;
                canShoot = true;
            } else {
                canShoot = false;
            }
        }

        // Shoots right
        if(playerX > shootingB) {
            if(shootingC < playerY) {
                inRangeLeft = true;
            } else {
                inRangeLeft = false;
            }
            if(shootingD > playerY) {
                inRangeRight = true;
            } else {
                inRangeRight = false;
            }

            if(inRangeLeft && inRangeRight) {
                shootingDirection = Direction.RIGHT;
                canShoot = true;
            } else {
                canShoot = false;
            }
        }


        if(canShoot) {
            currentWeapon.fire(position, shootingDirection);
        }

        currentWeapon.checkCollision(player);
    }

    /**
     * Draws TowerEnemy if it is alive.
     *
     * @param spriteBatch the spriteBatch used to draw this gameObject.
     */
    @Override
    public void draw(SpriteBatch spriteBatch) {
        if(isAlive()) {
            spriteBatch.draw(sprite, position);
            currentWeapon.draw(spriteBatch);
        }

    }

    /**
     * Resets the enemy.
     */
    @Override
    public void reset() {

    }

    /**
     * Returns the String representation of this instance.
     *
     * @return String representation of this instance.
     */
    @Override
    public String toString() {
        return super.toString() +
                "TowerEnemy{" +
                "shootingDirection=" + shootingDirection +
                ", currentWeapon=" + currentWeapon +
                ", player=" + player +
                ", shootingA=" + shootingA +
                ", shootingB=" + shootingB +
                ", shootingC=" + shootingC +
                ", shootingD=" + shootingD +
                ", canShoot=" + canShoot +
                '}';
    }
}

