package com.groupname.game.entities.powerups;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.entities.Player;

import java.util.Objects;

/**
 * HeartPowerUp is an PowerUp that give life to the player.
 * The amount of life it gives is controllable.
 */
public class HeartPowerUp extends PowerUp {
    private final int hearts;

    /**
     * The constructor of the object.
     *
     * @param sprite the sprite of the object.
     * @param position the position of the object.
     * @param hearts the amount of life it gives the player.
     */
    public HeartPowerUp(Sprite sprite, Vector2D position, int hearts) {
        super(sprite, position);
        if(hearts <= 0){
            throw new NullPointerException();
        }
        this.hearts = hearts;
    }

    /**
     * Method that give life to the player when collecting the object.
     *
     * @param player the player that collects the object.
     */
    @Override
    public void onCollect(Player player) {
        super.onCollect(player);

        int newHitpoints = player.getHitPoints() + hearts;

        if(newHitpoints > player.getMaxHitpoints()) {
            newHitpoints = player.getMaxHitpoints();
        }

        player.setHitPoints(newHitpoints);
    }

    /**
     * Returns the String representation of this instance.
     *
     * @return String representation of this instance.
     */
    @Override
    public String toString() {
        return super.toString() +
                "HeartPowerUp{" +
                "hearts=" + hearts +
                '}';
    }
}
