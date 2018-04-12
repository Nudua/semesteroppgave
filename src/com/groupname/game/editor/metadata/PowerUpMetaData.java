package com.groupname.game.editor.metadata;

import com.groupname.framework.math.Vector2D;

import java.util.Objects;

public class PowerUpMetaData extends ObjectMetaData {
    private final int amount;
    private PowerupSpriteType spriteType = PowerupSpriteType.Heart;

    public PowerUpMetaData(String name, Class<?> type, Vector2D position, int amount) {
        super(name, type, position);
        this.amount = amount;
    }

    public void setSpriteType(PowerupSpriteType spriteType) {
        this.spriteType = Objects.requireNonNull(spriteType);
    }

    public PowerupSpriteType getSpriteType() {
        return spriteType;
    }

    public int getAmount() {
        return amount;
    }
}
