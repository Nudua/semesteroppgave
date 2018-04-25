package com.groupname.game.entities.enemies;

import com.groupname.framework.graphics.Sprite;
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
     *
     * @param sprite
     * @param position
     * @param position1
     * @param player
     */
    public BossEnemy(Sprite sprite, Vector2D position, Vector2D position1, Player player) {
        super(sprite, position);
        this.position = position1;
        this.player = player;
    }
}
