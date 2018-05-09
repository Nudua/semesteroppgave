package com.groupname.framework.input.xinput;

/**
 * This enum is used by XInput to determine which controller is used.
 */
public enum PlayerIndex {
    ONE(0),
    TWO(1),
    THREE(2),
    FOUR(3);

    private int index;

    /**
     * Returns the index of this enum constant.
     *
     * @return the index of this enum constant.
     */
    public int getIndex() {
        return index;
    }

    PlayerIndex(int index) {
        this.index = index;
    }

    /**
     * Returns the index of this enum constant as a String.
     *
     * @return the index of this enum constant as a String.
     */
    @Override
    public String toString() {
        return Integer.toString(index);
    }
}
