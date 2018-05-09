package com.groupname.framework.input.xinput;

/**
 * This checked exception should be thrown if a library was not found.
 */
public class LibraryNotFoundException extends Exception {
    /**
     * Creates a new instance of this Exception with the specified message.
     * @param message
     */
    public LibraryNotFoundException(String message) {
        super(message);
    }
}
