package com.groupname.game.editor;

import com.groupname.framework.math.Vector2D;

public class PowerUpMetaData extends ObjectMetaData {
    private final int amount;

    public PowerUpMetaData(String name, Class<?> type, Vector2D position, int amount) {
        super(name, type, position);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
