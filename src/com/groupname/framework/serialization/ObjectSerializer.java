package com.groupname.framework.serialization;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

/**
 * A wrapper class that handles serialization and de-serializing of objects into a target Class
 * using the ObjectInput-, ObjectOutput streams.
 */
public class ObjectSerializer {

    /**
     * Attempts to deserialize the specified inputStream into the targetClass using Java's ObjectInputStream.
     *
     * @param inputStream the inputStream to deserialize.
     * @param targetClass the class to cast the deserialized file into.
     * @param <T> the target class type to read.
     * @return The deserialized object requested.
     * @throws ObjectSerializerException if the file does not exist or if it was not possible to cast the file into the requested class type.
     *                                   or if there was an unexpected io error while reading the file.
     */
    public <T> T read(InputStream inputStream, Class<T> targetClass) throws ObjectSerializerException {
        Objects.requireNonNull(inputStream);
        Objects.requireNonNull(targetClass);

        try (InputStream stream = inputStream; ObjectInputStream objectInputStream = new ObjectInputStream(stream)) {
            return targetClass.cast(objectInputStream.readObject());
        } catch (IOException | ClassNotFoundException | ClassCastException exception) {
            throw new ObjectSerializerException(exception.getMessage());
        }
    }

    /**
     * Attempts to deserialize the specified file into the targetClass using Java's ObjectInputStream.
     *
     * @param filePath the file to deserialize.
     * @param targetClass the class to cast the deserialized file into.
     * @param <T> the target class type to read.
     * @return The deserialized object requested.
     * @throws ObjectSerializerException if the file does not exist or if it was not possible to cast the file into the requested class type.
     *                                   or if there was an unexpected io error while reading the file.
     */
    public <T> T read(Path filePath, Class<T> targetClass) throws ObjectSerializerException {
        Objects.requireNonNull(filePath);
        Objects.requireNonNull(targetClass);

        try {
            InputStream inputStream = Files.newInputStream(filePath, StandardOpenOption.READ);
            return read(inputStream, targetClass);
        } catch (NoSuchFileException exception) {
            throw new ObjectSerializerException("File not found..");
        } catch (IOException exception) {
            throw new ObjectSerializerException(exception.getMessage());
        }
    }

    /**
     * Attempts to serialize a specified object to the given filePath using Java's ObjectOutputStream.
     *
     * @param data the object to serialize.
     * @param filePath the filePath to save the file.
     * @param <T> the class type of the object.
     * @throws ObjectSerializerException if there was an io error while writing the file.
     */
    public <T> void write(T data, Path filePath) throws ObjectSerializerException {
        Objects.requireNonNull(data);
        Objects.requireNonNull(filePath);

        try(OutputStream outputStream = Files.newOutputStream(filePath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(data);
        } catch (IOException exception) {
            throw new ObjectSerializerException(exception.getMessage());
        }
    }
}
