package com.groupname.framework.util;

/**
 * This Exception should be thrown whenever a String that cannot be equal
 * to the "" (Strings.EMPTY) string.
 */
public class EmptyStringException extends RuntimeException {

    /**
     * Creates a new instance of this Exception with no message.
     */
    public EmptyStringException() {
        super();
    }

    /**
     * Creates a new instance of this exception with the specified message.
     *
     * @param message
     */
    public EmptyStringException(String message) {
        super(message);
    }

    /**
     * Returns a string representation of this exception.
     *
     * @return @return a string representation of this exception.
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
