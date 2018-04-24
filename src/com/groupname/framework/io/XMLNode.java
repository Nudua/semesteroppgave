package com.groupname.framework.io;

public class XMLNode {
    private final String name;
    private final String value;


    public XMLNode(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

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
