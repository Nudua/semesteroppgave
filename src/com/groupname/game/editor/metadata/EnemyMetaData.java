package com.groupname.game.editor.metadata;

import com.groupname.framework.core.Difficulty;
import com.groupname.game.entities.EnemySpriteType;

import java.util.Objects;


/**
 * EnemyMetaData extends ObjectMetaData and
 * gives us distinctive setters and getters for enemies.
 * Create an enemy based on the information we give it.
 */
public class EnemyMetaData extends ObjectMetaData {
    private Difficulty difficulty = Difficulty.EASY;
    private EnemySpriteType spriteType = EnemySpriteType.BLUE_BLOB;

    /**
         * Creates a new instance of the object.
     *
     * @param name of the object.
     * @param type /class of the object.
     */
    public EnemyMetaData(String name, Class<?> type) {
        super(name, type);
    }

    /**
     * Method for changing sprite for the object.
     * For example: GREEN_BLOB, PURPLE_BLOB, BLUE_BLOB.
     *
     * @param spriteType name of the SpriteType.
     */
    public void setSpriteType(EnemySpriteType spriteType) {
        this.spriteType = Objects.requireNonNull(spriteType);
    }

    /**
     * Returns the value of the SpriteType.
     *
     * @return the value of the SpriteType.
     */
    public EnemySpriteType getSpriteType() {
        return spriteType;
    }

    /**
     * Setter for the difficulty of the object.
     * For example: EASY, MEDIUM, HARD, IMPOSSIBLE.
     *
     * @param difficulty the difficulty.
     */
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = Objects.requireNonNull(difficulty);
    }

    /**
     * Returns the difficulty of the object.
     *
     * @return the difficulty of the object.
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Returns a new Object with the same name, type, position, difficulty and spriteType.
     *
     * @return a new Object with the same name, type, position, difficulty and spriteType.
     */
    @Override
    public ObjectMetaData deepCopy() {
        EnemyMetaData enemyMetaData = new EnemyMetaData(getName(), getType());
        enemyMetaData.setPosition(getPosition());
        enemyMetaData.setDifficulty(difficulty);
        enemyMetaData.setSpriteType(spriteType);
        return enemyMetaData;
    }

    /**
     * Returns the String representation of this instance.
     *
     * @return String representation of this instance.
     */
    @Override
    public String toString() {
        return super.toString() + "EnemyMetaData{" +
                "difficulty=" + difficulty +
                ", spriteType=" + spriteType +
                '}';
    }
}
