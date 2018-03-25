package com.groupname.framework.serial;

/* Overview over our buttons and their bitwise value
  // Joystick
  Pin 2:    Up = 0x01
  Pin 3:    Down = 0x02
  Pin 4:    Left = 0x04
  Pin 5:    Right = 0x08

  // Buttons
  Pin 6:    Start = 0x10
  Pin 7:    Select = 0x20

  Pin 8:    ShootUp = 0x40
  Pin 9:    ShootDown = 0x80
  Pin 10:   ShootLeft = 0x100
  Pin 11:   ShootRight = 0x200

  See gamepad-arduino.txt for more information.
*/

public enum HitboxButton {
    // Definitions and values for the buttons
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

    public int getBitmask() {
        return bitmask;
    }
}
