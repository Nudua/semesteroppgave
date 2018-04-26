package com.groupname.framework.io;

import com.groupname.framework.math.Vector2D;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class XMLWriterTests {

    @Test
    public void checkWriterWorks() throws IllegalAccessException, IOException {
        XMLWriter xmlWriter = new XMLWriter();
        xmlWriter.write(Paths.get("tests.xml"), new Vector2D());

        Files.exists(Paths.get("tests.xml"));
    }
}
