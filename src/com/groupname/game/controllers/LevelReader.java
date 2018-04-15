package com.groupname.game.controllers;

import com.groupname.game.core.LevelMetaData;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class LevelReader {

    public LevelMetaData read(InputStream inputStream) throws IOException, ClassNotFoundException {
        try (InputStream stream = inputStream; ObjectInputStream objectInputStream = new ObjectInputStream(stream)) {
            return (LevelMetaData) objectInputStream.readObject();
        }
    }

    public LevelMetaData read(Path filePath) throws IOException, ClassNotFoundException {
        InputStream inputStream = Files.newInputStream(filePath, StandardOpenOption.READ);
        return read(inputStream);
    }
}