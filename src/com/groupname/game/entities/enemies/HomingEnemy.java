package com.groupname.game.entities.enemies;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.entities.Enemy;
import com.groupname.game.entities.Player;
import com.groupname.game.entities.projectiles.EnemyWeapon;
import com.groupname.game.entities.projectiles.SingleBulletWeapon;
import com.groupname.game.entities.projectiles.Weapon;

public class HomingEnemy extends Enemy {
    private final Vector2D startPosition;
    private double speed = 0.3d;
    private Player player;
    private EnemyWeapon currentWeapon;
    private int counter;
    private int delay = 60;

    public HomingEnemy(Sprite sprite, Vector2D position,int hitPoints, Player player) {
        super(sprite, position, hitPoints);
        startPosition = new Vector2D(position);
        this.player = player;

        createWeapon();
    }
    private void createWeapon() {
        currentWeapon = new EnemyWeapon(player);
    }


    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public void update() {
        super.update();
        currentWeapon.update(player);

        if(!isAlive()) {
            return;
        }
        double x = position.getX();
        double y = position.getY();

        double currentSpeed = speed;
        if(player.getPosition().getX() < getPosition().getX()) {
            position.setX(x - speed);
        } else if (player.getPosition().getX() > getPosition().getX()) {
            position.setX(x + speed);
        }

        if(player.getPosition().getY() < getPosition().getY()) {
            position.setY(y - speed);
        } else if (player.getPosition().getY() > getPosition().getY()) {
            position.setY(y + speed);
        }

        currentWeapon.checkCollision(player);

        counter++;
        if(counter >= delay) {
            currentWeapon.fire(position, player);
            counter = 0;
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
