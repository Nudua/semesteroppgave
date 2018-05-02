package com.groupname.game.entities;

public enum EnemySpriteType {
    GreenBlob(0),
    PurpleBlob(1),
    BlueBlob(2),
    Bee(3),
    CrazyBee(4),
    Snail(5),
    GreenFish(7),
    PinkFish(8),
    GreenWorm(9),
    PinkWorm(10),
    Squareboss(11),
    Jellyfish(12);

    private int index;

    EnemySpriteType(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
