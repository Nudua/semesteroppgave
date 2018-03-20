package com.groupname.game.entities.powerups;

import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.entities.Player;

public class HeartPowerUp extends PowerUp {

    private final int hearts;

    public HeartPowerUp(Sprite sprite, Vector2D position, int hearts) {
        super(sprite, position);
        this.hearts = hearts;
    }

    // Make this a runnable instead? Strategy pattern
    @Override
    public void onCollect(Player player) {
        super.onCollect(player);

        int newHitpoints = player.getHitPoints() + hearts;

        if(newHitpoints > player.getMaxHitpoints()) {
            newHitpoints = player.getMaxHitpoints();
        }

        player.setHitPoints(newHitpoints);
    }
}
