package com.groupname.framework.input.xinput.interop;

import com.groupname.framework.util.LibraryUtils;
import com.sun.jna.Platform;

/**
 * Factory class meant for creating an instance the XInputLibrary
 * by attempting to load the XInput dll version 1.4, 1.3 or 9.1.0 using JNA (Java Native Access).
 */
public final class XInputLibraryFactory {

    private static final String XINPUT_1_4 = "xinput1_4.dll";
    private static final String XINPUT_1_3 = "xinput1_3.dll";
    private static final String XINPUT_9_1_0 = "xinput9_1_0.dll";

    /**
     * Tries to create an instance of the XInputLibrary
     * by attempting to load the XInput dll versions 1.4, 1.3 or 9.1.0.
     *
     * Only supported on Windows Vista and above.
     * @return an instance of the XInputLibrary if successful, otherwise null will be returned.
     */
    public static XInputLibrary create() {
        // Unsupported on NIX
        if(!Platform.isWindows()) {
            return null;
        }

        XInputLibrary xInputLibrary = null;

        // Try loading XInputLibrary version 1.4
        xInputLibrary = LibraryUtils.load(XINPUT_1_4, XInputLibrary.class);

        // If that failed, try loading 1.3
        if(xInputLibrary == null) {
            xInputLibrary = LibraryUtils.load(XINPUT_1_3, XInputLibrary.class);
        }

        // If even that failed, try loading 9.1.0
        if(xInputLibrary == null) {
            xInputLibrary = LibraryUtils.load(XINPUT_9_1_0, XInputLibrary.class);
        }

        return xInputLibrary;
    }
}
