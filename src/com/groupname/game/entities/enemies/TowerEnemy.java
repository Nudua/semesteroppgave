package com.groupname.game.entities.enemies;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.math.Direction;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.entities.Enemy;
import com.groupname.game.entities.Player;
import com.groupname.game.entities.projectiles.EnemyWeapon;
import com.groupname.game.entities.projectiles.OneDirectionWeapon;

public class TowerEnemy extends Enemy {

    private Vector2D position;
    private Direction direction = Direction.Up;
    private OneDirectionWeapon currentWeapon;
    private Player player;
    private int shootingLeft;
    private int shootingRight;
    private boolean ok;



    public TowerEnemy(Sprite sprite, Vector2D position, int hitPoints, Direction shootingDirection, Player player) {
        super(sprite, position, hitPoints);
        this.direction = shootingDirection;
        this.position = new Vector2D(position);
        this.player = player;
        createWeapon();

    }

    public void createWeapon() {
        currentWeapon = new OneDirectionWeapon();
        shootingLeft = (int) position.getX() - 20;
        shootingRight = (int) position.getX() + 20;
    }

    @Override
    public void setPosition(Vector2D position) {
        this.position = position;
    }


    public void setShootingDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void update() {
        super.update();

        if(!isAlive()) {
            return;
        }
        currentWeapon.update();


        if(shootingLeft < player.getPosition().getX()) {
            ok = true;
        }
        if(shootingLeft > player.getPosition().getX()) {
            ok = false;
        }
        if (shootingRight > player.getPosition().getX()) {
            ok = true;
        }
        if(shootingRight < player.getPosition().getX()) {
            ok = false;
        }

        if(ok) {
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

