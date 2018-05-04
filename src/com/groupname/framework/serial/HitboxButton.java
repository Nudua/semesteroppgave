package com.groupname.framework.serial;

/**
 * This enum and it's bitwise values map directly to the buttons of our custom built hitbox InputAdapter.
 *
 *   // Joystick
 *   Pin 2:    UP = 0x01
 *   Pin 3:    DOWN = 0x02
 *   Pin 4:    LEFT = 0x04
 *   Pin 5:    RIGHT = 0x08
 *
 *   // Buttons
 *   Pin 6:    Start = 0x10
 *   Pin 7:    Select = 0x20
 *
 *   Pin 8:    ShootUp = 0x40
 *   Pin 9:    ShootDown = 0x80
 *   Pin 10:   ShootLeft = 0x100
 *   Pin 11:   ShootRight = 0x200
 *
 *   See gamepad-arduino.txt for more information.
 */
public enum HitboxButton {
    UP(0x01),
    DOWN(0x02),
    LEFT(0x04),
    RIGHT(0x08),

    START(0x10),
    SELECT(0x20),

    SHOOT_UP(0x40),
    SHOOT_DOWN(0x80),
    SHOOT_LEFT(0x100),
    SHOOT_RIGHT(0x200);

    private int bitmask;

    HitboxButton(int bitmask) {
        this.bitmask = bitmask;
    }

    /**
     * Returns the integer bitmask value of the current button.
     *
     * @return the integer bitmask value of the current button.
     */
    public int getBitmask() {
        return bitmask;
    }

    /**
     * Returns the bitmask value of this enum as a String.
     *
     * @return the bitmask value of this enum as a String.
     */
    @Override
    public String toString() {
        return String.format("bitmask=%d", bitmask);
    }
}
