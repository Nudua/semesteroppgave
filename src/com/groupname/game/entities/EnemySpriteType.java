package com.groupname.game.entities;

public enum EnemySpriteType {
    GREEN_BLOB(0),
    PURPLE_BLOB(1),
    BLUE_BLOB(2),
    BEE(3),
    CRAZY_BEE(4),
    SNAIL(5),
    GREEN_FISH(6),
    PINK_FISH(7),
    GREEN_WORM(8),
    PINK_WORM(9),
    SQUAREBOSS(10),
    JELLYFISH(11);

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
