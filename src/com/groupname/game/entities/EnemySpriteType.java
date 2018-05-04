package com.groupname.game.entities;

/**
 * This enum list is based on the enemies.png file.
 * The index after the name of the sprite type represent the Y coordinate on the spritesheet.
 * Since all the different sprites are below each other in the enemies.png is all we need to change the Y coordinate value
 * for changing sprite.
 * The different animation frames for each sprite is located horizontal.
 *
 * The coordinate system we use (x,y):
 * (0,0) | (1,0) | (2,0)
 * (0,1) | (1,1) | (2,1)
 * (0,2) | (1,2) | (2,2)
 * ...
 *
 */
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

    /**
     * Returns the Y coordinate of the EnemySpriteType.
     *
     * @return the Y coordinate of the EnemySpriteType.
     */
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
