package com.groupname.framework.serial.unix.internal;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class Termios extends Structure {

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

    public int c_iflag;		                // input mode flags
    public int c_oflag;		                // output mode flags
    public int c_cflag;		                // control mode flags
    public int c_lflag;		                // local mode flags
    public short c_line;			        // line discipline
    public short[] c_cc = new short[Constants.NCCS];	// control characters
    public int c_ispeed;		            // input speed
    public int c_ospeed;		            // output speed

    @SuppressWarnings("rawtypes")
    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("c_iflag", "c_oflag", "c_cflag", "c_lflag", "c_line", "c_cc", "c_ispeed", "c_ospeed");
    }
}
