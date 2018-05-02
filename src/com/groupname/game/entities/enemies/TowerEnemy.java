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
 * This class extends Enemy. TowerEnemy is an enemy that stands still and shoots
 * at the player a long the axis.
 */
public class TowerEnemy extends Enemy {
    private Direction shootingDirection = Direction.Up;
    private SingleBulletWeapon currentWeapon;
    private Player player;
    private int shootingA;
    private int shootingB;
    private int shootingC;
    private int shootingD;

    private boolean canShoot;

    /**
     * The constructure for an TowerEnemy. Takes a sprite, a start position and the player.
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


        // Shoots UP
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
                shootingDirection = Direction.Up;
                canShoot = true;
            } else {
                canShoot = false;
            }
        }

        // Shoots DOWN
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
                shootingDirection = Direction.Down;
                canShoot = true;
            } else {
                canShoot = false;
            }
        }

        // Shoots LEFT
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
                shootingDirection = Direction.Left;
                canShoot = true;
            } else {
                canShoot = false;
            }
        }

        // Shoots RIGHT
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
                shootingDirection = Direction.Right;
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

    @Override
    public void draw(SpriteBatch spriteBatch) {
        if(isAlive()) {
            spriteBatch.draw(sprite, position);
            currentWeapon.draw(spriteBatch);
        }

    }

    @Override
    public void reset() {

    }
}

