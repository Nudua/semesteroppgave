package com.groupname.framework.serialization;

/**
 * Represents an Exception (checked) that gets used by the ObjectSerialized when an error occurs
 * while serializing or de-serializing an object.
 */
public class ObjectSerializerException extends Exception {
    /**
     * Creates a new instance without any custom message specified.
     */
    public ObjectSerializerException() {
    }

    /**
     * Creates a new instance with a custom message specified.
     *
     * @param message the message to use.
     */
    public ObjectSerializerException(String message) {
        super(message);
    }
}
