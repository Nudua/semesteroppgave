package com.groupname.framework.io;

/**
 * This Exception should be thrown when Content was not found.
 */
public class ContentNotFoundException extends RuntimeException {

    /**
     * Creates a new instance of this Exception with the specified message.
     *
     * @param message the message to attach to this Exception.
     */
    public ContentNotFoundException(String message) {
        super(message);
    }
}
