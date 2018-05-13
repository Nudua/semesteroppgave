package com.groupname.framework.serial;

/**
 * This exception should be thrown whenever a problem when communicating over the serialport occurs.
 */
public class SerialPortException extends Exception {
    public SerialPortException(String message) {
        super(message);
    }
}
