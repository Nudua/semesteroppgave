package com.groupname.game.entities.enemies;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.entities.Enemy;
import com.groupname.game.entities.Player;

/**
 * This class extends Enemy. BossEnemy is an enemy that moves slowly, shoots a little and is hard to kill.
 */
public class BossEnemy extends Enemy {
    //private SpreadGun currentWeapon;
    private Player player;
    private int counter;
    private int delay = 120;
    private double slowSpeed = 0.1d;
    private double fastSpeed = 2.0d;
    private double positionX;
    private double positionY;

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

    }


    /**
     * The specific logic for this type of enemy.
     * The BossEnemy XXXXXX.
     */
    @Override
    public void update() {
        super.update();
        /*positionX = position.getX();
        positionY = position.getY();

        counter++;
        if(counter >= delay) {
            System.out.println("BØ");
            counter = 0;
        } else {


            if(positionX > (positionX - 30)){
                position.setX(positionX + slowSpeed);
            }
            if(positionY > (positionY - 20)) {
                position.setY(positionY + slowSpeed);
            }

        }

*/


    }

    /**
     *
     * @param spriteBatch jnjn
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
