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
 * This class extends Enemy. TowerEnemy is an enemy that stands still and shoots in just one direction.
 */
public class TowerEnemy extends Enemy {

    private Vector2D position;
    private Direction shootingDirection = Direction.Up;
    private SingleBulletWeapon currentWeapon;
    private Player player;
    private int shootingLeft;
    private int shootingRight;
    private boolean canShoot;

    /**
     * 
     * @param sprite
     * @param position
     * @param shootingDirection
     * @param player
     */
    public TowerEnemy(Sprite sprite, Vector2D position, Direction shootingDirection, Player player) {
        super(sprite, position);
        this.shootingDirection = Objects.requireNonNull(shootingDirection);
        this.position = new Vector2D(position);
        this.player = Objects.requireNonNull(player);
        createWeapon();
    }

    private void createWeapon() {
        currentWeapon = new SingleBulletWeapon();
        shootingLeft = (int) position.getX() - 20;
        shootingRight = (int) position.getX() + 20;
    }

    @Override
    public void setPosition(Vector2D position) {
        this.position = position;
    }


    @Override
    public void update() {
        super.update();

        if(!isAlive()) {
            return;
        }
        currentWeapon.update();

        // Look this over
        if(shootingLeft < player.getPosition().getX()) {
            canShoot = true;
        }
        if(shootingLeft > player.getPosition().getX()) {
            canShoot = false;
        }
        if (shootingRight > player.getPosition().getX()) {
            canShoot = true;
        }
        if(shootingRight < player.getPosition().getX()) {
            canShoot = false;
        }

        if(canShoot) {
            currentWeapon.fire(position);
        }

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

