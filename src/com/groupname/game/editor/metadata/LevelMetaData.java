package com.groupname.game.editor.metadata;

import com.groupname.framework.util.Strings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Create a level based on the information we feed it with.
 */
public class LevelMetaData implements Serializable {

    private static final String DEFAULT_BACKGROUND = "background4.png";

    private final String id;
    private final String name;
    private final List<ObjectMetaData> objectMetaDataList;

    private String backgroundImagePath = DEFAULT_BACKGROUND;

    /**
     * Creates a new instance of the object.
     *
     * @param name is the name of the level.
     */
    public LevelMetaData(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = Strings.requireNonNullAndNotEmpty(name);
        objectMetaDataList = new ArrayList<>();
    }

    /**
     * Returns the id of the specific level.
     *
     * @return the id of the specific level.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the name of the specific level.
     *
     * @return the name of the specific level.
     */
    public String getName() {
        return name;
    }

    /**
     * Mutable ArrayList.
     * Returns a list of objects.
     *
     * @return a list of objects.
     */
    public List<ObjectMetaData> getObjectMetaDataList() {
        return objectMetaDataList;
    }


    /**
     * Sets the path to the background image.
     *
     * @param backgroundImagePath the path to the image.
     */
    public void setBackgroundImagePath(String backgroundImagePath) {
        this.backgroundImagePath = Strings.requireNonNullAndNotEmpty(backgroundImagePath);
    }

    /**
     * Returns the path to the background image.
     *
     * @return the path to the background image.
     */
    public String getBackgroundImagePath() {
        return backgroundImagePath;
    }

    /**
     * Returns the String representation of this instance.
     *
     * @return String representation of this instance.
     */
    @Override
    public String toString() {
        return "LevelMetaData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", objectMetaDataList=" + objectMetaDataList +
                ", backgroundImagePath='" + backgroundImagePath + '\'' +
                '}';
    }
}
