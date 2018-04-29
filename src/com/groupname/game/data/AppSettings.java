package com.groupname.game.data;

import com.groupname.framework.settings.INIPreferences;
import com.groupname.framework.math.Size;
import com.groupname.framework.util.Strings;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

/*
class PlayerProgress {

    private final static String FIRST_LEVEL = "";

    private String currentLevel = FIRST_LEVEL;

    public void setCurrentLevel(String currentLevel) {
        this.currentLevel = currentLevel;
    }
}
*/

/**
 * Enum Singleton, representing the settings / preferences for this application
 * such as volume, game progress.
 */
public enum AppSettings {
    INSTANCE;

    public static final Size SCREEN_BOUNDS = new Size(1280, 720);
    public static final Rectangle LEVEL_BOUNDS = new Rectangle(80 * 2, 80 * 1, 1280 - 80 * 4, 720 - 80 * 2);

    // Constants
    private static final String FILENAME = "appsettings.ini";
    private static final double DEFAULT_VOLUME = 0.8d;

    // Keys for our preferences
    private static final String FIRSTRUN_KEY = "firstrun";
    private static final String FULLSCREEN_KEY = "fullscreen";
    private static final String CURRENT_LEVEL_KEY = "currentlevel";
    private static final String MUSIC_VOLUME_KEY = "musicvolume";
    private static final String SOUNDEFFECTS_VOLUME_KEY = "soundeffectsvolume";

    // Preferences
    private String currentLevel = Strings.EMPTY;
    private boolean firstRun = false;
    private boolean fullScreen = false;
    private double musicVolume = DEFAULT_VOLUME;
    private double soundEffectVolume = DEFAULT_VOLUME;

    private INIPreferences iniPreferences;

    // Getters and setters for our preferences
    // Will be empty if not set
    public String getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(String currentLevel) {
        this.currentLevel = Objects.requireNonNull(currentLevel);
    }

    public double getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(double musicVolume) {
        this.musicVolume = musicVolume;
    }

    public double getSoundEffectVolume() {
        return soundEffectVolume;
    }

    public void setSoundEffectVolume(double soundEffectVolume) {
        this.soundEffectVolume = soundEffectVolume;
    }

    public void save() throws IOException {
        iniPreferences.putBoolean(FIRSTRUN_KEY, false);
        iniPreferences.putBoolean(FULLSCREEN_KEY, false);
        iniPreferences.put(CURRENT_LEVEL_KEY, currentLevel);
        iniPreferences.putDouble(MUSIC_VOLUME_KEY, musicVolume);
        iniPreferences.putDouble(SOUNDEFFECTS_VOLUME_KEY, soundEffectVolume);

        iniPreferences.write();
    }

    public void load() throws IOException {
        iniPreferences = new INIPreferences(Paths.get(FILENAME));

        firstRun = iniPreferences.getBoolean(FIRSTRUN_KEY, true);
        fullScreen = iniPreferences.getBoolean(FULLSCREEN_KEY, false);
        currentLevel = iniPreferences.get(CURRENT_LEVEL_KEY);
        musicVolume = iniPreferences.getDouble(MUSIC_VOLUME_KEY, DEFAULT_VOLUME);
        soundEffectVolume = iniPreferences.getDouble(SOUNDEFFECTS_VOLUME_KEY, DEFAULT_VOLUME);
    }
}