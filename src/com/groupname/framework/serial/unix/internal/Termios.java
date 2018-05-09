package com.groupname.framework.serial.unix.internal;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

/**
 * This class defines the attributes used for serial port communications using the Termios Unix API for terminal I/O.
 *
 * All the fields in this class have to be public because they are automatically mapped
 * and set by the JNA (Java Native Access) library to match the corresponding Unix Termios structure.
 *
 * The names also match the native naming conventions and therefore ignore standard java naming conventions.
 */
public class Termios extends Structure {

    /**
     * Constants used by the Termios Structure.
     */
    public static class Constants {
        public static int CS8 = 48;
        public static int CSIZE = 48;
        public static int IGNBRK = 1;
        public static int IXON = 1024;
        public static int IXOFF = 4096;
        public static int IXANY = 2048;
        public static int CLOCAL = 2048;
        public static int CREAD = 128;
        public static int PARENB = 256;
        public static int PARODD = 512;
        public static int CSTOPB = 64;
        public static int CRTSCTS = (int)(2147483648L);
        public static int TCSANOW = 0;
        public static int VMIN = 6;
        public static int VTIME = 5;

        private static final int NCCS = 32;
    }

    // Input flags
    public int c_iflag;
    // Output flags
    public int c_oflag;
    // Control flags
    public int c_cflag;
    // Local flags
    public int c_lflag;
    // line flags
    public short c_line;
    // Control chars
    public short[] c_cc = new short[Constants.NCCS];
    // Input speed (bounds)
    public int c_ispeed;
    // Output speed (bounds)
    public int c_ospeed;

    /**
     * Returns all the fields in this structure in the order matching the native order of the Termios Structure.
     *
     * @return all the fields in this structure in the order matching the native order of the Termios Structure.
     */
    @SuppressWarnings("rawtypes")
    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("c_iflag", "c_oflag", "c_cflag", "c_lflag", "c_line", "c_cc", "c_ispeed", "c_ospeed");
    }

    /**
     * Returns the String representation of this object.
     *
     * @return the String representation of this object.
     */
    @Override
    public String toString() {
        return "Termios{" +
                "c_iflag=" + c_iflag +
                ", c_oflag=" + c_oflag +
                ", c_cflag=" + c_cflag +
                ", c_lflag=" + c_lflag +
                ", c_line=" + c_line +
                ", c_cc=" + Arrays.toString(c_cc) +
                ", c_ispeed=" + c_ispeed +
                ", c_ospeed=" + c_ospeed +
                '}';
    }
}
