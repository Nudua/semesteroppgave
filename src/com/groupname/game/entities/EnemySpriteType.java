package com.groupname.game.entities;

public enum EnemySpriteType {
    GreenBlob(0),
    PurpleBlob(1),
    BlueBlob(2),
    Bee(3),
    CrazyBee(4),
    Snail(5),
    GreenFish(6),
    PinkFish(7),
    GreenWorm(8),
    PinkWorm(9),
    Squareboss(10),
    Jellyfish(11);

    private int index;

    EnemySpriteType(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    /**
     * Returns the String representation of this instance.
     *
     * @return String representation of this instance.
     */
    @Override
    public String toString() {
        return "EnemySpriteType{" +
                "index=" + index +
                '}';
    }
}
