package com.groupname.framework.io;

import com.groupname.framework.math.Vector2D;
import com.groupname.framework.serialization.SerializationException;
import com.groupname.framework.serialization.xml.XMLWriter;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class XMLWriterTests {

    @Test
    public void checkWriterWorks() throws SerializationException {
        XMLWriter xmlWriter = new XMLWriter();
        xmlWriter.write(Paths.get("tests.xml"), new Vector2D());

        Files.exists(Paths.get("tests.xml"));
    }
}
