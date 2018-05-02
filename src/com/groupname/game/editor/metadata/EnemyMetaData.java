package com.groupname.game.editor.metadata;

import com.groupname.framework.core.Difficulty;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.entities.EnemySpriteType;

import java.io.Serializable;
import java.util.Objects;


/**
 * EnemyMetaData extends ObjectMetaData and
 * giv us distinctive setters and getters for enemies.
 */
public class EnemyMetaData extends ObjectMetaData {
    private Difficulty difficulty = Difficulty.Easy;
    private EnemySpriteType spriteType = EnemySpriteType.BlueBlob;

    /**
     * Create a new instance of EnemyMetaData
     *
     * @param name of the object.
     * @param type /class of the object.
     */
    public EnemyMetaData(String name, Class<?> type) {
        super(name, type);
    }

    /**
     * Method for changing sprite for the object.
     * For example: GreenBlob, PurpleBlob, BlueBlob.
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
     * For example: Easy, Medium, Hard, Impossible.
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
}
