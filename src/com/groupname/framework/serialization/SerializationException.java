package com.groupname.framework.serialization;

/**
 * Represents an Exception (checked) that gets used if an error occurs
 * while serializing or de-serializing an object.
 */
public class SerializationException extends Exception {
    /**
     * Creates a new instance without any custom message specified.
     */
    public SerializationException() {
    }

    /**
     * Creates a new instance with a custom message specified.
     *
     * @param message the message to use.
     */
    public SerializationException(String message) {
        super(message);
    }
}
