package com.groupname.game.editor;

import com.groupname.game.editor.metadata.LevelMetaData;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Read the levels from file.
 * The files is stored as ".level".
 */
public class LevelReader {

    public LevelMetaData read(InputStream inputStream) throws LevelReaderException {
        try (InputStream stream = inputStream; ObjectInputStream objectInputStream = new ObjectInputStream(stream)) {
            return (LevelMetaData) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException exception) {
            throw new LevelReaderException(exception.getMessage());
        }
    }

    public LevelMetaData read(Path filePath) throws LevelReaderException {
        try {
            InputStream inputStream = Files.newInputStream(filePath, StandardOpenOption.READ);
            return read(inputStream);
        } catch (IOException exception) {
            throw new LevelReaderException(exception.getMessage());
        }
    }

}