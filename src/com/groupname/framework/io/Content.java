package com.groupname.framework.io;

import com.groupname.framework.util.Strings;
import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.Objects;


public class Content {

    // Maybe set this at start?
    private static String contentBaseFolder;// = "/com/groupname/game/resources";

    public static void setContentBaseFolder(String path) {
        contentBaseFolder = path;
    }

    public static Image loadImage(String filename, ResourceType type) {
        // ResourceType has to be either a Sprite, SpriteSheet or a Background
        if(!(type == ResourceType.Sprite || type == ResourceType.SpriteSheet || type == ResourceType.Background)) {
            throw new IllegalArgumentException();
        }

        InputStream inputStream = loadFile(filename, type);

        return new Image(inputStream);
    }

    public static InputStream loadFile(String fileName, ResourceType type) {
        if(Strings.isNullOrEmpty(contentBaseFolder)) {
            throw new ContentNotFoundException("The basefolder has to be set before using this method, call setContentBaseFolder with a valid path first.");
        }

        Strings.requireNonNullAndNotEmpty(fileName);
        Objects.requireNonNull(type);

        String folder = getFolderPathFromResourceType(type);

        String fullPath = contentBaseFolder + folder + fileName;

        InputStream inputStream = Content.class.getResourceAsStream(fullPath);

        if(inputStream == null) { // Not sure about this approach, might make a runtime exception instead, because this is not really recoverable
            throw new ContentNotFoundException(String.format("Unable to locate the file: %s", fileName));
        }

        return inputStream;
    }

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
