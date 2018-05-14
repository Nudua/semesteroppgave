package com.groupname.game.data;

import com.groupname.game.entities.Player;

import java.security.InvalidParameterException;
import java.util.Objects;


/**
 * An immutable class that represents player progress and stats.
 *
 */
public class SaveData {
    private final String currentLevel;
    private final int hitpoints;

    /**
     * Creates an empty instance of this class.
     */
    public SaveData() {
        currentLevel = "";
        hitpoints = Player.DEFAULT_HITPOINTS;
    }

    /**
     * Creates a new instance with the specified level and hitpoints.
     *
     * @param currentLevel the level id that the player is currently on, leave empty if no progress has been made.
     * @param hitpoints the current hitpoints of the player.
     */
    public SaveData(String currentLevel, int hitpoints) {
        this.currentLevel = Objects.requireNonNull(currentLevel);

        if(hitpoints < 0) {
            throw new InvalidParameterException("Hitpoints cannot be negative");
        }
        this.hitpoints = hitpoints;
    }

    /**
     * Returns the current level id that the player is on.
     *
     * @return the current level id that the player is on.
     */
    public String getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Returns the player score.
     *
     * @return the player score.
     */
    public int getHitpoints() {
        return hitpoints;
    }

    /**
     * Returns the String representation of this object.
     *
     * @return the String representation of this object.
     */
    @Override
    public String toString() {
        return "SaveData{" +
                "currentLevel='" + currentLevel + '\'' +
                ", hitpoints=" + hitpoints +
                '}';
    }
}
