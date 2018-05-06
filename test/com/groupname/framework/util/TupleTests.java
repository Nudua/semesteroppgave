package com.groupname.framework.util;

import com.groupname.framework.serialization.xml.XMLNode;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TupleTests {

    @Test(expected = NullPointerException.class)
    public void item1CannotBeNull() {
        new Tuple<String, String>(null, "secondItem");
    }

    @Test(expected = NullPointerException.class)
    public void item2CannotBeNull() {
        new Tuple<String, String>("FirstItem", null);
    }

    @Test
    public void getItem1IsConsistent() {
        String item1 = "firstitem";

        Tuple<String, Integer> tuple = new Tuple<>(item1, 128);

        assertSame(item1, tuple.getItem1());
    }

    @Test
    public void getItem2IsConsistent() {
        XMLNode secondItem = new XMLNode("node", "value");

        Tuple<String, XMLNode> tuple = new Tuple<>("first", secondItem);

        assertSame(secondItem, tuple.getItem2());
    }
}
