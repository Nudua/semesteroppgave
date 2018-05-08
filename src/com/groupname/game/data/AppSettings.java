package com.groupname.game.data;

import com.groupname.framework.serialization.SerializationException;
import com.groupname.framework.serialization.xml.XMLReader;
import com.groupname.framework.serialization.xml.XMLWriter;
import com.groupname.framework.settings.INIPreferences;
import com.groupname.framework.math.Size;
import com.groupname.framework.util.Strings;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;


/**
 * Enum Singleton that ensures that only one instance of this class is instantiated,
 * representing the settings / preferences for this application such as volume, game progress.
 *
 * Users can access this singleton by using:
 * AppSettings appsettings = AppSettings.INSTANCE;
 */
public enum AppSettings {
    INSTANCE;

    /**
     * The default viewport of this game.
     */
    public static final Size SCREEN_BOUNDS = new Size(1280, 720);

    /**
     * The default bounds of the levels used by this application.
     */
    public static final Rectangle LEVEL_BOUNDS = new Rectangle(80 * 2, 80 * 1, 1280 - 80 * 4, 720 - 80 * 2);

    // Constants
    private static final String APPSETTINGS_INI = "appsettings.ini";
    private static final double DEFAULT_VOLUME = 0.8d;
    private static final String SAVEDATA_FILENAME = "save.xml";

    // Keys for our preferences
    private static final String FIRSTRUN_KEY = "firstrun";
    private static final String FULLSCREEN_KEY = "fullscreen";
    private static final String CURRENT_LEVEL_KEY = "currentlevel";
    private static final String MUSIC_VOLUME_KEY = "musicvolume";
    private static final String SOUNDEFFECTS_VOLUME_KEY = "soundeffectsvolume";

    // SaveData
    private SaveData saveData = new SaveData();

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

    public boolean isFirstRun() {
        return firstRun;
    }

    public void setCurrentLevel(String currentLevel) {
        this.currentLevel = Objects.requireNonNull(currentLevel);
    }

    /**
     * Returns the music volume.
     *
     * @return the music volume.
     */
    public double getMusicVolume() {
        return musicVolume;
    }

    /**
     * Sets the music volume.
     *
     * @param musicVolume the new volume to set.
     */
    public void setMusicVolume(double musicVolume) {
        this.musicVolume = musicVolume;
    }

    /**
     * Returns the sound effect volume.
     *
     * @return the sound effect volume.
     */
    public double getSoundEffectVolume() {
        return soundEffectVolume;
    }

    /**
     * Sets the sound effect volume.
     *
     * @param soundEffectVolume the new volume to set.
     */
    public void setSoundEffectVolume(double soundEffectVolume) {
        this.soundEffectVolume = soundEffectVolume;
    }

    /**
     * Returns the saveData used by this application. (used to store player progress)
     *
     * @return the saveData used by this application. (used to store player progress)
     */
    public SaveData getSaveData() {
        return saveData;
    }

    /**
     * Sets the saveData field to the specified saveData.
     * @param saveData the saveData to set.
     */
    public void setSaveData(SaveData saveData) {
        this.saveData = Objects.requireNonNull(saveData);
    }

    /**
     * Updates and saves the current settings used by this class into the file 'appsettings.ini'.
     *
     * @throws IOException if there was an issue saving the file.
     */
    public void save() throws IOException {
        iniPreferences.putBoolean(FIRSTRUN_KEY, false);
        iniPreferences.putBoolean(FULLSCREEN_KEY, false);
        iniPreferences.put(CURRENT_LEVEL_KEY, currentLevel);
        iniPreferences.putDouble(MUSIC_VOLUME_KEY, musicVolume);
        iniPreferences.putDouble(SOUNDEFFECTS_VOLUME_KEY, soundEffectVolume);

        iniPreferences.write();
    }

    /**
     * Loads the current save data from file.
     *
     * @throws SerializationException if there was an issue de-serializing the file.
     */
    public void loadSaveData() throws SerializationException {
        XMLReader xmlReader = new XMLReader();
        saveData = xmlReader.read(Paths.get(SAVEDATA_FILENAME));
    }

    public void saveSaveData() throws SerializationException {
        XMLWriter xmlWriter = new XMLWriter();
        xmlWriter.write(Paths.get(SAVEDATA_FILENAME), saveData);
    }

    /**
     * Attempts to load the settings from 'appsettings.ini' if no values exists defaults will be used.
     *
     * @throws IOException if there was an error while loading the settings from the file.
     */
    public void load() throws IOException {
        iniPreferences = new INIPreferences(Paths.get(APPSETTINGS_INI));

        firstRun = iniPreferences.getBoolean(FIRSTRUN_KEY, true);
        fullScreen = iniPreferences.getBoolean(FULLSCREEN_KEY, false);
        currentLevel = iniPreferences.get(CURRENT_LEVEL_KEY);
        musicVolume = iniPreferences.getDouble(MUSIC_VOLUME_KEY, DEFAULT_VOLUME);
        soundEffectVolume = iniPreferences.getDouble(SOUNDEFFECTS_VOLUME_KEY, DEFAULT_VOLUME);
    }

    /**
     * Returns the String representation of this object.
     *
     * @return the String representation of this object.
     */
    @Override
    public String toString() {
        return "AppSettings{" +
                "currentLevel='" + currentLevel + '\'' +
                ", firstRun=" + firstRun +
                ", fullScreen=" + fullScreen +
                ", musicVolume=" + musicVolume +
                ", soundEffectVolume=" + soundEffectVolume +
                ", iniPreferences=" + iniPreferences +
                '}';
    }
}