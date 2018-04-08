package com.groupname.framework.input.xinput.interop;

import com.groupname.framework.input.xinput.interop.structs.XInputState;
import com.sun.jna.Library;

public interface XInputLibrary extends Library {

    // Internally this is called ERROR_SUCCESS = 0
    int ERROR_SUCCESS = 0;
    int ERROR_DEVICE_NOT_CONNECTED = 0x48F;

    int MAX_CONTROLLERS = 3;

    int XInputGetState(int packetNumber, XInputState XInputState);
}
