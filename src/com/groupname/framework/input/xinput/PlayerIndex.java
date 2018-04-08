package com.groupname.framework.input.xinput;

public enum PlayerIndex {
    One(0),
    Two(1),
    Three(2),
    Four(3);

    private int index;

    public int getIndex() {
        return index;
    }

    PlayerIndex(int index) {
        this.index = index;
    }
}
