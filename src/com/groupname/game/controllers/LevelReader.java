package com.groupname.game.controllers;

import com.groupname.game.core.LevelMetaData;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class LevelReader {
    public LevelMetaData read(Path filePath) throws IOException, ClassNotFoundException {
        try(InputStream inputStream = Files.newInputStream(filePath, StandardOpenOption.READ);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            return (LevelMetaData)objectInputStream.readObject();
        }
    }
}
