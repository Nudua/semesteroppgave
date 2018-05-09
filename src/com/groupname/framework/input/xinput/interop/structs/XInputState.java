package com.groupname.framework.input.xinput.interop.structs;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

/**
 * Maps to the native XInputState structure (in the XInput dll).
 * All the fields are public because it's required by JNA.
 */
public class XInputState extends Structure {
    /**
     * The current GamePad. Matches PlayerIndex values.
     */
    public int PacketNumber;

    /**
     * The actual state of the XInput gamepad read.
     */
    public XInputGamepad XInputGamepad;

    /**
     * Returns a String list of the field order, required by JNA.
     *
     * @return a String list of the field order, required by JNA.
     */
    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("PacketNumber", "XInputGamepad");
    }

    /**
     * Returns the String representation of this object.
     *
     * @return the String representation of this object.
     */

    @Override
    public String toString() {
        return "XInputState{" +
                "PacketNumber=" + PacketNumber +
                ", XInputGamepad=" + XInputGamepad +
                '}';
    }
}
