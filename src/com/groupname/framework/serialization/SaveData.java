package com.groupname.framework.serialization;

import com.groupname.framework.util.Strings;

import java.security.InvalidParameterException;

// Immutable
public class SaveData {
    private final String currentLevel;
    private final long score;

    public SaveData() {
        currentLevel = "";
        score = 0;
    }

    public SaveData(String currentLevel, long score) {
        this.currentLevel = Strings.requireNonNullAndNotEmpty(currentLevel);
        if(score < 0) {
            throw new InvalidParameterException("Score cannot be negative");
        }
        this.score = score;
    }

    public String getCurrentLevel() {
        return currentLevel;
    }

    public long getScore() {
        return score;
    }
}
