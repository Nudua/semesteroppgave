package com.groupname.framework.serial;


import com.groupname.framework.serial.unix.SerialPortUnix;
import com.groupname.framework.serial.win.SerialPortWin;
import com.sun.jna.Platform;

/**
 * A static factory class for creating an instance of the SerialPort class
 * based on the platform that we're running on.
 */
public final class SerialPortFactory {

    /**
     * Creates a new instance of the Serial Port class.
     *
     * @return the platform specific version of the SerialPort class.
     * @throws SerialPortException if the platform is unsupported.
     */
    public static SerialPort create() throws SerialPortException {
        if(Platform.isWindows()) {
            return new SerialPortWin();
        } else if(Platform.isLinux() || Platform.isMac() || Platform.isFreeBSD()) {
            return new SerialPortUnix();
        }

        throw new SerialPortException("No Library was found for your platform.");
    }
}
