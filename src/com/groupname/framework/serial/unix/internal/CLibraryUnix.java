package com.groupname.framework.serial.unix.internal;

import com.sun.jna.Library;

/**
 * Preface: We're using JNA (Java Native Access) to map native calls to an Java interface that is easy to use.
 *
 * This interface is used make native calls to the unix c library, in this project it's primary use is to use the termios
 * function calls to do communications over the serial port.
 *
 * All these methods are automagically mapped by the JNA library to the corresponding native methods in the standard c library.
 *
 */
public interface CLibraryUnix extends Library {

    /**
     * Name of the native library.
     */
    String NAME = "c";

    /**
     * Set the output speed stored in the termios structure to the specified speed.
     *
     * @param termios the structure to update with the specified speed.
     * @param speed the output speed to set.
     * @return 0 if successful, -1 if there was an error.
     */
    int cfsetospeed(Termios termios, int speed);


    /**
     * Set the input speed stored in the termios structure to the specified speed.
     *
     * @param termios the structure to update with the specified speed.
     * @param speed the input speed to set.
     * @return 0 if successful, -1 if there was an error.
     */
    int cfsetispeed(Termios termios, int speed);

    /**
     * Gets the attributes coupled with the current fd (file descriptor)
     * and updates the specified termios structure with them.
     *
     * @param fd the file descriptor to query.
     * @param termios the structure to update.
     * @return 0 if successful, -1 if there was an error.
     */
    int tcgetattr(int fd, Termios termios);

    /**
     * Sets the attributes associated with the current termios to the specified file descriptor.
     *
     * @param fd the file descriptor to use.
     * @param optionalActions TCSANOW (0) means that the changes are applied immediately.
     * @param termios the structure to update.
     * @return 0 if successful, -1 if there was an error.
     */
    int tcsetattr(int fd, int optionalActions, Termios termios);

    /**
     * Open a FILE and returns a file descriptor for the specified fileName.
     *
     * @param fileName the file or device to open.
     * @param openFlags the FileOpenFlags to use for this file / device.
     * @return 0 if successful, -1 if there was an error.
     * @see FileOpenFlags
     */
    int open(String fileName, int openFlags);

    /**
     * Close the specified file descriptor.
     *
     * @param fd the file descriptor to close.
     * @return 0 if successful, -1 if there was an error.
     */
    int close(int fd);

    /**
     * Reads from the file descriptor specified and puts it into the specified byte[] array.
     *
     * @param fd the file descriptor to read from.
     * @param buff the buffer to read into.
     * @param nbytes the number of bytes to read.
     * @return number of bytes read, -1 if there was an error or 0 if there's nothing more to read.
     */
    long read(int fd, byte[] buff, int nbytes);
}
