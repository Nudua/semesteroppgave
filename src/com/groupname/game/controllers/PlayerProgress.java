package com.groupname.game.controllers;

import com.groupname.framework.serialization.SerializationException;
import com.groupname.framework.util.Alerts;
import com.groupname.game.data.AppSettings;
import com.groupname.game.data.SaveData;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

/**
 * This class is used to load and save the players progression through the game.
 *
 * The user can pick which file to save and load from via the filesystem.
 */
public class PlayerProgress {

    private static final String DEFAULT_FILENAME = "save.xml";

    // Picks a file to save or to load from the filesystem.
    private File pickFile(boolean save) {
        FileChooser fileChooser = new FileChooser();

        String title = save ? "Save player progress" : "Load player progress";

        fileChooser.setTitle(title);
        fileChooser.setInitialFileName(DEFAULT_FILENAME);
        fileChooser.setInitialDirectory(Paths.get(".").toFile());

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Save Data (*.xml)", "*.xml"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All types (*.*)", "*.*"));

        return save ? fileChooser.showSaveDialog(null) : fileChooser.showOpenDialog(null);
    }

    /**
     * Attempts to save the specified saveData to the location that the user selects.
     *
     * @param saveData the saveData to save.
     */
    public void save(SaveData saveData) {
        Objects.requireNonNull(saveData);

        File targetFile = pickFile(true);

        // No file was chosen
        if(targetFile == null) {
            Alerts.showError("No file was selected, aborting ...");
            return;
        }

        AppSettings appSettings = AppSettings.INSTANCE;

        appSettings.setSaveData(saveData);

        try {
            appSettings.saveSaveData(targetFile.toPath());
            Alerts.showAlert("Save data was saved successfully ...");
        } catch (SerializationException exception) {
            Alerts.showError("Unable to save the file :(");
        }
    }

    /**
     * Attempts to load saveData from the specified file that the user selects.
     *
     * @return an optional containing the saveData if successful, otherwise an Optional.empty() will be returned.
     */
    public Optional<SaveData> load() {
        File saveFile = pickFile(false);

        if(saveFile == null) {
            Alerts.showError("No file was selected, aborting ...");
            return Optional.empty();
        }

        AppSettings appSettings = AppSettings.INSTANCE;

        try {
            appSettings.loadSaveData(saveFile.toPath());
            return Optional.of(appSettings.getSaveData());
        } catch (SerializationException exception) {
            Alerts.showError("Unable to load save file (file is corrupt)");
            return Optional.empty();
        }
    }
}
