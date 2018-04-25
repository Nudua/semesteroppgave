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
    private Vector2D position;
    //private SpreadGun currentWeapon;
    private Player player;
    private int counter;
    private int delay = 120;

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
     * Setter that sets the position of the enemy.
     * @param position an Vector2D that is an position on the canvas.
     */
    @Override
    public void setPosition(Vector2D position) {
        this.position = position;
    }

    /**
     * The specific logic for this type of enemy.
     * The BossEnemy XXXXXX.
     */
    @Override
    public void update() {
        super.update();






        counter++;
        if(counter >= delay) {
            System.out.println("BØ");
            counter = 0;
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
