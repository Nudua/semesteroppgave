package com.groupname.framework.io;

import com.groupname.framework.serialization.xml.XMLNode;
import com.groupname.framework.util.EmptyStringException;
import org.junit.Test;
import static org.junit.Assert.*;

public class XMLNodeTests {

    @Test (expected = NullPointerException.class)
    public void xmlNodeParameterNotNull() {
        XMLNode xmlNode = new XMLNode(null, null);
    }

    @Test (expected = EmptyStringException.class)
    public void xmlNodeParameterNotEmpty() {
        XMLNode xmlNode = new XMLNode("","");
    }

    @Test
    public void checkGetName() {
        XMLNode xmlNode = new XMLNode("name","value");
        assertEquals(xmlNode.getName(), "name");
    }

    @Test
    public void checkGetValue() {
        XMLNode xmlNode = new XMLNode("name","value");
        assertEquals(xmlNode.getValue(), "value");
    }
}
