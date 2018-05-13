package com.groupname.framework.io;

import com.groupname.framework.serialization.ObjectSerializer;
import com.groupname.framework.serialization.SerializationException;
import com.groupname.framework.util.Strings;
import com.groupname.game.editor.metadata.ObjectMetaData;
import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;

/**
 * Static helper class for loading different types of Content.
 * Users must call the setContentBaseFolder before using this class.
 * and the following subfolders within that basefolder has to follow this structure:
 * /graphics (Images, png and jpg are supported)
 *      /spritesheets
 *      /sprites
 *      /backgrunds
 * /audio (file)
 *      /music
 *      /soundeffects
 * /levels (file)
 * /metadata (file)
 */
public final class Content {

    // Maybe set this at start?
    private static String contentBaseFolder;

    /**
     * Sets the basefolder used for loading all the content for this class.
     * Users must call the setContentBaseFolder before using this class.
     *
     * @param path the basePath for where the content is located.
     */
    public static void setContentBaseFolder(String path) {
        contentBaseFolder = path;
    }

    /**
     * Attempts to load a new Image from the specified filename.
     * The ResourceType must be either ResourceType.SPRITE, ResourceType.SPRITE_SHEET or ResourceType.BACKGROUND.
     *
     * @param filename the filename of the Image to load.
     * @param type the resourceType to load. Must be ResourceType.SPRITE, ResourceType.SPRITE_SHEET or ResourceType.BACKGROUND.
     * @return the requested Image if successful.
     * @throws NullPointerException if the specified file was not found.
     */
    public static Image loadImage(String filename, ResourceType type) {
        ensureBaseFolderIsSet();
        // ResourceType has to be either a SPRITE, SPRITE_SHEET or a BACKGROUND
        if(!(type == ResourceType.SPRITE || type == ResourceType.SPRITE_SHEET || type == ResourceType.BACKGROUND)) {
            throw new IllegalArgumentException();
        }

        InputStream inputStream = loadFile(filename, type);

        return new Image(inputStream, -1, -1, true, false);
    }

    /**
     * Deserializes a List of ObjectMetaData from the specified file.
     *
     * @param fileName the file to load from.
     * @return a List of ObjectMetaData
     * @throws SerializationException if the file doesn't exist of there was an issue deserializing the list.
     */
    @SuppressWarnings("unchecked")
    public static List<ObjectMetaData> loadMetadata(String fileName) throws SerializationException {
        ensureBaseFolderIsSet();

        ObjectSerializer serializer = new ObjectSerializer();

        // A little messy, but required for generic List types
        Class<List<ObjectMetaData>> clazz = (Class<List<ObjectMetaData>>) ((Class)List.class);
        return serializer.read(loadFile(fileName, ResourceType.METADATA), clazz);
    }

    /**
     * Gets the fully qualified path of the requested filename.
     *
     * @param fileName the relative filename.
     * @param type the ResourceType of the requested file.
     * @return the fully qualified path of the requested file.
     */
    public static String getResourcePath(String fileName, ResourceType type) {
        ensureBaseFolderIsSet();

        Strings.requireNonNullAndNotEmpty(fileName);
        Objects.requireNonNull(type);

        String folder = getFolderPathFromResourceType(type);

        String fullPath = contentBaseFolder + folder + fileName;

        return Content.class.getResource(fullPath).toExternalForm();
    }

    private static void ensureBaseFolderIsSet() {
        if(Strings.isNullOrEmpty(contentBaseFolder)) {
            throw new ContentNotFoundException("The basefolder has to be set before using this method, call setContentBaseFolder with a valid path first.");
        }
    }

    /**
     * Attempts to load an InputStream for the requested content.
     *
     * The basefolder must be set before calling this method.
     * @param fileName the relative file of the resource to load.
     * @param type the ResourceType for the specified file.
     * @return the InputStream of the requested file.
     * @throws ContentNotFoundException if the requested content was not found.
     */
    public static InputStream loadFile(String fileName, ResourceType type) {
        ensureBaseFolderIsSet();

        Strings.requireNonNullAndNotEmpty(fileName);
        Objects.requireNonNull(type);

        String folder = getFolderPathFromResourceType(type);

        String fullPath = contentBaseFolder + folder + fileName;

        InputStream inputStream = Content.class.getResourceAsStream(fullPath);

        // Failed to load inputStream
        if(inputStream == null) {
            throw new ContentNotFoundException(String.format("Unable to locate the file: %s", fileName));
        }

        return inputStream;
    }

    // gets the full path from the specified ResourceType
    private static String getFolderPathFromResourceType(ResourceType resourceType) {
        switch (resourceType) {
            case SPRITE_SHEET:
                return "/graphics/spritesheets/";
            case SPRITE:
                return "/graphics/sprites/";
            case MUSIC:
                return "/audio/music/";
            case SOUND_EFFECT:
                return "/audio/soundeffects/";
            case BACKGROUND:
                return "/graphics/backgrounds/";
            case LEVEL:
                return "/levels/";
            case METADATA:
                return "/metadata/";
        }

        return Strings.EMPTY;
    }

}
