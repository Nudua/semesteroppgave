package com.groupname.framework.serialization.xml;

import com.groupname.framework.io.Content;
import com.groupname.framework.serialization.SaveData;
import com.groupname.framework.serialization.SerializationException;
import com.groupname.game.levels.Credits;

import java.nio.file.Paths;


public class XMLMain {

    public static void main (String[] args) {
        Content.setContentBaseFolder("/com/groupname/game/resources");

        XMLWriter xmlWriter = new XMLWriter();

        SaveData saveData = new SaveData(Credits.LEVEL_ID, 999);
        try {
            xmlWriter.write(Paths.get("saveFile1.xml"), saveData);
        } catch (SerializationException exception) {
            System.err.println(exception.getMessage());
        }


        XMLReader reader = new XMLReader();
        try {
            SaveData data1 = reader.read(Paths.get("saveFile1.xml"));
        } catch (SerializationException exception) {
            System.err.println(exception.getMessage());
        }

        // Rename SerializationException to SerializerException


    }
}
