package com.groupname.game.editor.metadata;

import java.util.Objects;

/**
 * PowerUpMetaData extends ObjectMetaData and
 * giv us distinctive setters and getters for PowerUps.
 * Create an PowerUp based on the information we give it.
 */
public class PowerUpMetaData extends ObjectMetaData {
    private final int amount;
    private PowerupSpriteType spriteType = PowerupSpriteType.Heart;

    /**
     * Creates a new instance of the object.
     *
     * @param name The name of the object.
     * @param type The type/class of the object.
     * @param amount the amount of for example live the player get.
     */
    public PowerUpMetaData(String name, Class<?> type, int amount) {
        super(name, type);
        this.amount = amount;
    }

    /**
     * Method for changing sprite for the object.
     *
     * @param spriteType name of the SpriteType.
     */
    public void setSpriteType(PowerupSpriteType spriteType) {
        this.spriteType = Objects.requireNonNull(spriteType);
    }

    /**
     * Returns the value of the SpriteType.
     *
     * @return the value of the SpriteType.
     */
    public PowerupSpriteType getSpriteType() {
        return spriteType;
    }

    /**
     * Returns the value of the amount the player gets when collecting object.
     *
     * @return the value of the amount the player gets when collecting object.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Returns the String representation of this instance.
     *
     * @return String representation of this instance.
     */
    @Override
    public String toString() {
        return "PowerUpMetaData{" +
                "amount=" + amount +
                ", spriteType=" + spriteType +
                '}';
    }
}
