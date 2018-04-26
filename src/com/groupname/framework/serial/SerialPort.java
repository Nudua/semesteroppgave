package com.groupname.framework.serial;

import com.groupname.framework.serial.SerialPortException;

import java.security.InvalidParameterException;

/**
 * This interface is used to read data from an open SerialPort.
 *
 * Implementations must throw the SerialPortException if any errors occur when
 * trying to open the specified port or while reading from the port.
 */
public interface SerialPort {

    /**
     * Returns the state of the serial port.
     *
     * @return true if the port is open, false otherwise.
     */
    boolean isOpen();


    /**
     * Implementations must use this method to open the serial port so it is possible to read from it.
     *
     * @throws SerialPortException if there was an error while attempting to open the port.
     */
    void open() throws SerialPortException;


    /**
     * Implementations must use this method to read from an open serial port.
     *
     * @param buffer the byte array to read the data into.
     * @param numBytesToRead the bytes to read from the serial port.
     * @return the amount of bytes actually read.
     * @throws InvalidParameterException if the buffer is null or the buffer's length is less than numBytesToRead.
     * @throws SerialPortException if the serial port was not opened. @hint call open() first.
     */
    long read(byte[] buffer, int numBytesToRead) throws SerialPortException;

    /**
     * Implementations must clean up and close an already open serial port here.
     *
     * @throws SerialPortException if a port was not open.
     */
    void close() throws SerialPortException;
}
