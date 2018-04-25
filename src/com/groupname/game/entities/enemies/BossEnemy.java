package com.groupname.game.entities.enemies;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.core.BoundsChecker;
import com.groupname.game.entities.Enemy;
import com.groupname.game.entities.Player;
import javafx.scene.shape.Rectangle;


import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class extends Enemy. BossEnemy is an enemy that moves slowly, shoots a little and is hard to kill.
 */
public class BossEnemy extends Enemy {
    //private SpreadGun currentWeapon;
    private Player player;
    private int counterMax = 200;
    private int delay = 100;
    private double slowSpeed = 0.1d;
    private double fastSpeed = 3.0d;
    private double positionX;
    private double positionY;
    private Vector2D basePosition;
    private Rectangle bossBounds;
    private BoundsChecker boundsChecker = new BoundsChecker();
    private Counter counter = new Counter(5);
    private Counter counterInAction = new Counter(2);



    /**
     * The constructure for an BossEnemy. Takes a sprite, start position and the player.
     *
     * @param sprite Sets an default sprite for the enemy.
     * @param position Sets the start position, on the canvas.
     * @param player takes the player for accessing the players position.
     */
    public BossEnemy(Sprite sprite, Vector2D position, Player player) {
        super(sprite, position);
        this.player = player;
        this.basePosition = position;
        createBossBounds();

    }

    private void createBossBounds() {
        double width = sprite.getWidth() + 400;
        double height = sprite.getHeight() + 200;
        bossBounds = new Rectangle((int)basePosition.getX() - 300, (int)basePosition.getY() - 100, (int)width, (int)height);
    }

    public Rectangle getBossBounds() {
        return bossBounds;
    }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(position.getX()+30, position.getY()+70, sprite.getWidth() - 60, sprite.getHeight()-70);
    }


    /**
     * The specific logic for this type of enemy.
     * The BossEnemy XXX.
     */
    @Override
    public void update() {
        super.update();
        positionX = position.getX();
        positionY = position.getY();
        double randomX = ThreadLocalRandom.current().nextDouble(-2.1d, 2.1d);
        double randomY = ThreadLocalRandom.current().nextDouble(-2.1d, 2.1d);
        counter.step();

        if(counter.isDone()){
            if(counterInAction.getCounter() < 60) {
                position.addX(-fastSpeed);
                position.addY(randomY);
                System.out.println("Hei");
            } if(counterInAction.getCounter() > 60) {
                position.addX(fastSpeed);
                position.addY(randomY);
                System.out.println("Hei på deg");
            }
            counterInAction.step();
            if(counterInAction.isDone()){
                counter.reset();
                counterInAction.reset();
            }
        } else {

            if(boundsChecker.isWithinBounds(this, bossBounds)){
                position.add(randomX, randomY);
            }
            if(position.getX() + sprite.getWidth() >= bossBounds.getX() + bossBounds.getWidth()){
                position.addX(-100);
            }
            if(position.getX() <= bossBounds.getX()){
                position.addX(100);
            }
            if(position.getY() + sprite.getHeight() >= bossBounds.getY() + bossBounds.getHeight()) {
                position.addY(-100);
            }
            if(position.getY() <= bossBounds.getY()){
                position.addY(100);
            }

        }


    }

    /**
     *
     * @param spriteBatch
     */
    @Override
    public void draw(SpriteBatch spriteBatch) {
        if(isAlive()) {
            spriteBatch.draw(sprite, position);
            //currentWeapon.draw(spriteBatch);
        }

    }

    /**
     * Method for resetting of the enemy.
     */
    @Override
    public void reset() {

    }
}
