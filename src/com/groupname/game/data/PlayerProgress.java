package com.groupname.game.data;

import com.groupname.framework.concurrency.TaskRunner;
import com.groupname.framework.serialization.SerializationException;
import com.groupname.framework.util.Alerts;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

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

        // Save the data off thread to keep the UI responsive
        final TaskRunner taskRunner = new TaskRunner();

        Supplier<Boolean> saveAction = () -> {
            try {
                appSettings.saveSaveData(targetFile.toPath());
                return true;
            } catch (SerializationException exception) {
                return false;
            }
        };

        Consumer<Boolean> saveResults = (success) -> {
            if(success) {
                Alerts.showAlert("Save data was saved successfully ...");
            } else {
                Alerts.showError("Unable to save the file :(");
            }

            // Stop the taskrunner
            try {
                taskRunner.stop();
            } catch (InterruptedException exception) {
                System.err.println("Error stopping the taskrunner.");
            }
        };

        taskRunner.submit(saveAction, saveResults);
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
