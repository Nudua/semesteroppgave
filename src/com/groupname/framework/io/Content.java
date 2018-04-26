package com.groupname.framework.io;

import com.groupname.framework.util.Strings;
import javafx.scene.image.Image;

import java.io.InputStream;
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
 */
public final class Content {

    // Maybe set this at start?
    private static String contentBaseFolder;// = "/com/groupname/game/resources";

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
     * The ResourceType must be either ResourceType.Sprite, ResourceType.SpriteSheet or ResourceType.Background.
     *
     * @param filename the filename of the Image to load.
     * @param type the resourceType to load. Must be ResourceType.Sprite, ResourceType.SpriteSheet or ResourceType.Background.
     * @return the requested Image if successful.
     * @throws NullPointerException if the specified file was not found.
     */
    public static Image loadImage(String filename, ResourceType type) {
        // ResourceType has to be either a Sprite, SpriteSheet or a Background
        if(!(type == ResourceType.Sprite || type == ResourceType.SpriteSheet || type == ResourceType.Background)) {
            throw new IllegalArgumentException();
        }

        InputStream inputStream = loadFile(filename, type);

        return new Image(inputStream, -1, -1, true, false);
    }

    /**
     * Gets the fully qualified path of the requested filename.
     *
     * @param fileName the relative filename.
     * @param type the ResourceType of the requested file.
     * @return the fully qualified path of the requested file.
     */
    public static String getResourcePath(String fileName, ResourceType type) {
        if(Strings.isNullOrEmpty(contentBaseFolder)) {
            throw new ContentNotFoundException("The basefolder has to be set before using this method, call setContentBaseFolder with a valid path first.");
        }

        Strings.requireNonNullAndNotEmpty(fileName);
        Objects.requireNonNull(type);

        String folder = getFolderPathFromResourceType(type);

        String fullPath = contentBaseFolder + folder + fileName;

        return Content.class.getResource(fullPath).toExternalForm();
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
        if(Strings.isNullOrEmpty(contentBaseFolder)) {
            throw new ContentNotFoundException("The basefolder has to be set before using this method, call setContentBaseFolder with a valid path first.");
        }

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
            case SpriteSheet:
                return "/graphics/spritesheets/";
            case Sprite:
                return "/graphics/sprites/";
            case Music:
                return "/audio/music/";
            case SoundEffect:
                return "/audio/soundeffects/";
            case Background:
                return "/graphics/backgrounds/";
            case Level:
                return "/levels/";
        }

        return Strings.EMPTY;
    }

}
