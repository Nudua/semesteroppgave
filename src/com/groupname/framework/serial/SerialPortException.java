package com.groupname.framework.serial;

/**
 * This exception should be thrown whenever a problem when communicating over the serialport occurs.
 */
public class SerialPortException extends Exception {

    /**
     * Creates a new instance of this Exception with the specified message.
     *
     * @param message the message to attach to this Exception.
     */
    public SerialPortException(String message) {
        super(message);
    }
}
