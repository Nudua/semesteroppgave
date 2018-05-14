package com.groupname.framework.serialization.xml;

import com.groupname.framework.util.Strings;

import java.util.Objects;

/**
 * This class represents an XML Node.
 * Example:
 * <name>Value</name>
 */
public class XMLNode {
    private final String name;
    private final String value;

    /**
     *XMLNode is the tags and its values that we store.
     * @param name tagname.
     * @param value the value to the tag.
     */
    public XMLNode(String name, String value) {
        this.name = Strings.requireNonNullAndNotEmpty(name);
        this.value = value;
    }

    /**
     * Getter for the name.
     * @return the name of the node.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the value
     * @return the value of the node.
     */
    public String getValue() {
        return value;
    }

    /**
     * The string representation is XMLNode where name = name and value = value
     * Where name is the name of the field you save, and value is the value of the field
     * @return a print of the XMLNode.
     */
    @Override
    public String toString() {
        return "XMLNode{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
