package com.groupname.framework.serial.unix.internal;

/**
 * Flags for opening a file or device under Unix.
 */
public class FileOpenFlags {

    /**
     * Read only.
     */
    public static final int O_RDONLY = 0;

    /**
     * Read and write.
     */
    public static final int O_RDWR = 2;

    /**
     * Synchronized input / output.
     */
    public static final int O_SYNC = 1052672;

    /**
     * Something about detaching from a terminal control process.
     */
    public static final int O_NOCTTY = 256;
}
