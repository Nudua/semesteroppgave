package com.groupname.game.data;

import com.groupname.framework.util.Strings;

import java.security.InvalidParameterException;


/**
 * An immutable class that represents player progress and stats.
 *
 */
public class SaveData {
    private final String currentLevel;
    private final long score;

    /**
     * Creates an empty instance of this class.
     */
    public SaveData() {
        currentLevel = "";
        score = 0;
    }

    /**
     * Creates a new instance with the specified level and score.
     *
     * @param currentLevel the level id that the player is currently on, leave empty if no progress has been made.
     * @param score the current player's score.
     */
    public SaveData(String currentLevel, long score) {
        this.currentLevel = Strings.requireNonNullAndNotEmpty(currentLevel);
        if(score < 0) {
            throw new InvalidParameterException("Score cannot be negative");
        }
        this.score = score;
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
    public long getScore() {
        return score;
    }
}
