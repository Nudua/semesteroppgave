package com.groupname.game.editor.metadata;

import com.groupname.framework.math.Vector2D;
import com.groupname.framework.util.Strings;
import com.groupname.game.data.AppSettings;
import com.groupname.game.editor.metadata.ObjectMetaData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class LevelMetaData implements Serializable {

    private static final String DEFAULT_BACKGROUND = "background4.png";

    private final String id; // Generate from AppSettings
    private final String name;
    private final ArrayList<ObjectMetaData> objectMetaDataList;

    private String backgroundImagePath = DEFAULT_BACKGROUND;

    public LevelMetaData(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = Strings.requireNonNullAndNotEmpty(name);
        objectMetaDataList = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // This one is mutable
    public ArrayList<ObjectMetaData> getObjectMetaDataList() {
        return objectMetaDataList;
    }


    public void setBackgroundImagePath(String backgroundImagePath) {
        // Check if file exists also
        this.backgroundImagePath = Strings.requireNonNullAndNotEmpty(backgroundImagePath);
    }

    public String getBackgroundImagePath() {
        return backgroundImagePath;
    }

    // Screentransitions, maybe just class type here?
    //private ScreenTransition startLevel;
    //private ScreenTransition endLevel;
}
