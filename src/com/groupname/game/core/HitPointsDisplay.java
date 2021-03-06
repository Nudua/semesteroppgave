package com.groupname.game.core;

import com.groupname.framework.core.UpdateDrawAble;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.animation.AnimatedSprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.entities.Player;
import com.groupname.game.entities.SpriteFactory;

import java.util.Arrays;
import java.util.Objects;

/**
 * Class for displaying hearts representing the hit points of the player.
 */
public class HitPointsDisplay implements UpdateDrawAble {
    private static final int onIndex = 0;
    private static final int offIndex = 1;

    private Sprite[] heart;
    private final Player player;
    private int currentHitpoints;
    private int maxHitpoints;
    private Vector2D startPosition;

    /**
     * Creates a new instance of this class.
     *
     * @param player the player of the game.
     */
    public HitPointsDisplay(Player player) {
        this.player = Objects.requireNonNull(player);
        startPosition = new Vector2D(5,5);
        createSprite();
    }

    private void createSprite() {
        SpriteFactory spriteFactory = new SpriteFactory();
        heart = spriteFactory.createHeart();
    }

    /**
     * Updates the data fields with data from player.
     */
    @Override
    public void update() {
        maxHitpoints = player.getMaxHitpoints();
        currentHitpoints = player.getHitPoints();
    }

    /**
     * Draws the hearts based on the HitPoints.
     *
     * @param spriteBatch the spriteBatch used for drawing any sprites.
     */
    @Override
    public void draw(SpriteBatch spriteBatch) {
        Objects.requireNonNull(spriteBatch);

        for(int i = 0; i < maxHitpoints; i++) {
            int spriteIndex = i >= currentHitpoints ? offIndex : onIndex;

            Vector2D position = new Vector2D(startPosition.getX() + i * heart[spriteIndex].getWidth(), startPosition.getY());
            
            spriteBatch.draw(heart[spriteIndex], position);
        }
    }

    /**
     * Returns the String representation of this instance.
     *
     * @return String representation of this instance.
     */
    @Override
    public String toString() {
        return "HitPointsDisplay{" +
                "heart=" + Arrays.toString(heart) +
                ", player=" + player +
                ", currentHitpoints=" + currentHitpoints +
                ", maxHitpoints=" + maxHitpoints +
                ", startPosition=" + startPosition +
                '}';
    }
}
