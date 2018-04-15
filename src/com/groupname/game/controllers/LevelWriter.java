package com.groupname.game.controllers;

import com.groupname.game.core.LevelMetaData;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class LevelWriter {
    public void write(LevelMetaData levelMetaData, Path filePath) throws IOException {
        try(OutputStream outputStream = Files.newOutputStream(filePath, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(levelMetaData);
        }
    }

}
