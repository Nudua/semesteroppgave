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

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {

    }
}
