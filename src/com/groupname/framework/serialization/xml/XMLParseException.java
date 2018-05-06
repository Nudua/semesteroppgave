package com.groupname.framework.serialization.xml;

/**
 * An exception used for XML Parsing issues.
 */
public class XMLParseException extends Exception {

    /**
     * Creates a new instance without any custom message specified.
     */
    public XMLParseException() {
    }

    /**
     * Creates a new instance with a custom message specified.
     *
     * @param message the message to use.
     */
    public XMLParseException(String message) {
        super(message);
    }

}
