package com.groupname.game.data;

import com.groupname.framework.settings.INIPreferences;
import com.groupname.framework.math.Size;
import com.groupname.framework.util.Strings;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.nio.file.Paths;


class PlayerProgress {

    private final static String FIRST_LEVEL = "";

    private String currentLevel = FIRST_LEVEL;

    public void setCurrentLevel(String currentLevel) {
        this.currentLevel = currentLevel;
    }
}

public enum AppSettings {
    INSTANCE;

    private static final String FILENAME = "appsettings.ini";
    public static final Size SCREEN_BOUNDS = new Size(1280, 720);
    public static final Rectangle LEVEL_BOUNDS = new Rectangle(80 * 2, 80 * 1, 1280 - 80 * 4, 720 - 80 * 2);

    private String currentLevel = Strings.EMPTY;
    private boolean firstRun = false;
    private boolean fullScreen = false;

    private INIPreferences iniPreferences;

    // Will be empty if not set
    public String getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(String currentLevel) {
        this.currentLevel = currentLevel;
    }

    public void save() throws IOException {
        /*
        Preferences prefs = Preferences.userRoot().node("ourgame");
        prefs.putBoolean("firstRun", false);
        prefs.putBoolean("fullScreen", false);
        prefs.put("currentLevel", currentLevel);
        prefs.flush();
        */

        iniPreferences.putBoolean("firstrun", false);
        iniPreferences.putBoolean("fullscreen", false);
        iniPreferences.put("currentlevel", currentLevel);
        iniPreferences.write();
    }

    public void load() throws IOException {
        iniPreferences = new INIPreferences(Paths.get(FILENAME));

        firstRun = iniPreferences.getBoolean("firstrun", true);
        fullScreen = iniPreferences.getBoolean("fullscreen", false);
        currentLevel = iniPreferences.get("currentlevel");
    }

    /*
    // Make own exception
    public void save() throws IOException {
        Properties properties = new Properties();
        properties.put("firstRun", false);
        properties.put("fullScreen", false);
        properties.put("currentLevel", currentLevel);

        try(OutputStream outputStream = Files.newOutputStream(Paths.get(FILENAME), StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE)) {
            properties.storeToXML(outputStream, "");
        }
    }
    */

    /*
    public void load() throws IOException {
        if(!Files.exists(Paths.get(FILENAME))) {
            firstRun = true;
            System.out.println("First run");
            return;
        }

        try(InputStream stream = Files.newInputStream(Paths.get(FILENAME), StandardOpenOption.READ)) {
            Properties properties = new Properties();
            properties.loadFromXML(stream);

            // this is a bit lazy for my taste, fix later
            firstRun = (boolean)properties.get("firstRun");
            fullScreen = (boolean)properties.get("fullScreen");
            currentLevel = (int)properties.get("currentLevel");

            System.out.println("Loaded successfully, level= " + currentLevel);
        }
    }
    */

}
//AppSettings.INSTANCE.save();
//AppSettings.INSTANCE.load();
//AppSettings.SCREEN_BOUNDS;