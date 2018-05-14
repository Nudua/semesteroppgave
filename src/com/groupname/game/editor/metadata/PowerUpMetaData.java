package com.groupname.game.editor.metadata;


/**
 * PowerUpMetaData extends ObjectMetaData and
 * gives us distinctive setters and getters for PowerUps.
 * Create an PowerUp based on the information we give it.
 */
public class PowerUpMetaData extends ObjectMetaData {
    private int amount = 1;

    /**
     * Creates a new instance of the object.
     *
     * @param name The name of the object.
     * @param type The type/class of the object.
     */
    public PowerUpMetaData(String name, Class<?> type) {
        super(name, type);
    }

    /**
     * Sets the amount this powerup gives.
     *
     * @param amount the amount this powerup gives.
     */
    public void setAmount(int amount) {
        this.amount = amount;
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
     * Returns a deep copy of this metadata.
     *
     * @return a deep copy of this metadata.
     */
    @Override
    public ObjectMetaData deepCopy() {
        PowerUpMetaData copy = new PowerUpMetaData(getName(), getType());
        copy.setPosition(getPosition());
        copy.setAmount(amount);
        return copy;
    }

    /**
     * Returns the String representation of this instance.
     *
     * @return String representation of this instance.
     */
    @Override
    public String toString() {
        return super.toString() + "PowerUpMetaData{" +
                "amount=" + amount +
                '}';
    }
}
