package com.groupname.game.editor.metadata;

import com.groupname.framework.core.Difficulty;
import com.groupname.framework.math.Vector2D;

import java.util.Objects;


// Abstract builder for these maybe?
// We need a bit more information
public class EnemyMetaData extends ObjectMetaData {
    private Difficulty difficulty = Difficulty.Easy;
    private EnemySpriteType spriteType = EnemySpriteType.Blob;

    public EnemyMetaData(String name, Class<?> type, Vector2D position) {
        super(name, type, position);
    }

    public void setSpriteType(EnemySpriteType spriteType) {
        this.spriteType = Objects.requireNonNull(spriteType);
    }

    public EnemySpriteType getSpriteType() {
        return spriteType;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = Objects.requireNonNull(difficulty);
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
}
