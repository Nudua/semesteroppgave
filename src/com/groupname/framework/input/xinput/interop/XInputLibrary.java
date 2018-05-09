package com.groupname.framework.input.xinput.interop;

import com.groupname.framework.input.xinput.interop.structs.XInputState;
import com.sun.jna.Library;

/**
 * Preface: We're using JNA (Java Native Access) to map native dll calls to an Java interface that is easy to use.
 *
 * This interface is used make native calls to the xinput dll. (Only supported on Windows)
 *
 * Users should use the XInputLibraryFactory to get an concrete instance of this library
 * since it will attempt to load XInput versions 1.4, 1.3 or 9.1.0.
 *
 * Example:
 * XInputLibrary library = XInputLibraryFactory.create();
 *
 * Note: library will be null if we failed to load the library (probably on an unsupported platform)
 */
public interface XInputLibrary extends Library {

    /**
     * This code will be returned from XInputGetState when we successfully got the state from the specified GamePad.
     */
    int ERROR_SUCCESS = 0;

    /**
     * This code will be returned from XInputGetState if the GamePad requested to get the state from is not connected.
     */
    int ERROR_DEVICE_NOT_CONNECTED = 0x48F;


    /**
     * Attempts to update the specified XInputState with the state of the requested controller (packetNumber).
     *
     * @param packetNumber the controller to read from, see the PlayerIndex enum.
     * @param XInputState a reference to the XInputState to put the updated state into.
     * @return ERROR_SUCCESS (0) if the state was read successfully, otherwise ERROR_DEVICE_NOT_CONNECTED (0x48F) will be returned.
     */
    int XInputGetState(int packetNumber, XInputState XInputState);
}
