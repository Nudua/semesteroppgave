package com.groupname.framework.input.xinput.interop.structs;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class XInputState extends Structure {
    public int PacketNumber;
    public XInputGamepad XInputGamepad;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("PacketNumber", "XInputGamepad");
    }
}
