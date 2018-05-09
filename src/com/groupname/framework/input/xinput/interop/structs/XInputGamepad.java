package com.groupname.framework.input.xinput.interop.structs;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

/**
 * The XInputGamepad structure represents the current state of a GamePad game controller.
 *
 * All the fields in this class has to be public to interface with jna's (native) Library interface.
 */
public class XInputGamepad extends Structure {

    // Bitmask of the controllers digital buttons see 'GamePadButton' for individual buttons.
    public int Buttons;

    // The current value of the left analog trigger. The Value is between -127 to 128.
    public byte LeftTrigger;

    // The current value of the right analog trigger. The Value is between -127 to 128.
    public byte RightTrigger;

    // Thumbsticks general info for 'ThumbLX', 'ThumbLY', 'ThumbRX' and 'ThumbRY'
    // Negative values represent a movement to the LEFT or DOWN on the thumbstick.
    // While positive values represent a movement to the RIGHT or UP.

    // LEFT Thumbstick on the x-axis. Values range between -32768 and 32767.
    public short ThumbLX;

    // LEFT Thumbstick on the y-axis. Values range between -32768 and 32767.
    public short ThumbLY;

    // RIGHT Thumbstick on the x-axis. Values range between -32768 and 32767.
    public short ThumbRX;

    // RIGHT Thumbstick on the y-axis. Values range between -32768 and 32767.
    public short ThumbRY;

    @SuppressWarnings("rawtypes")
    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("Buttons", "LeftTrigger", "RightTrigger", "ThumbLX", "ThumbLY",
                "ThumbRX", "ThumbRY");
    }
}
