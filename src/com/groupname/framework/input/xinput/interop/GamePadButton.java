package com.groupname.framework.input.xinput.interop;

/**
 * Represents all the digital buttons used by a XInput GamePad and their native bitmask value.
 * Names match the ones used by the Xinput dll natively.
 */
public enum GamePadButton {
    XINPUT_GAMEPAD_DPAD_UP(0x0001),
    XINPUT_GAMEPAD_DPAD_DOWN(0x0002),
    XINPUT_GAMEPAD_DPAD_LEFT(0x0004),
    XINPUT_GAMEPAD_DPAD_RIGHT(0x0008),
    XINPUT_GAMEPAD_START(0x0010),
    XINPUT_GAMEPAD_BACK(0x0020),
    XINPUT_GAMEPAD_LEFT_THUMB(0x0040),
    XINPUT_GAMEPAD_RIGHT_THUMB(0x0080),
    XINPUT_GAMEPAD_LEFT_SHOULDER(0x0100),
    XINPUT_GAMEPAD_RIGHT_SHOULDER(0x0200),
    XINPUT_GAMEPAD_A(0x1000),
    XINPUT_GAMEPAD_B(0x2000),
    XINPUT_GAMEPAD_X(0x4000),
    XINPUT_GAMEPAD_Y(0x8000),
    XINPUT_GAMEPAD_RIGHT_STICK_UP(0x10000),
    XINPUT_GAMEPAD_RIGHT_STICK_DOWN(0x20000),
    XINPUT_GAMEPAD_RIGHT_STICK_LEFT(0x30000),
    XINPUT_GAMEPAD_RIGHT_STICK_RIGHT(0x40000);

    private long bitmask;

    GamePadButton(long bitmask) {
        this.bitmask = bitmask;
    }

    /**
     * Gets the bitmask value of this enum constant.
     *
     * @return the bitmask value of this enum constant.
     */
    public long getBitmask() {
        return bitmask;
    }

    /**
     * Returns the bitmask value of this enum constant as a String.
     *
     * @return the bitmask value of this enum constant as a String.
     */
    @Override
    public String toString() {
        return "bitmask=" + bitmask;
    }
}
