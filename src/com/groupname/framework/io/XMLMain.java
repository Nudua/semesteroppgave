package com.groupname.framework.io;

import javafx.scene.shape.Path;

import java.nio.file.Paths;

public class XMLMain {

    public static void Main (String[] args) throws Exception {
        XMLWriter xmlWriter = new XMLWriter();

        XMLNode node = new XMLNode("Tor", "Håkon");

        xmlWriter.write(Paths.get("test.xml"), node);
    }
}
