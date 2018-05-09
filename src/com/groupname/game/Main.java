package com.groupname.game;

import com.groupname.framework.audio.SoundPlayer;
import com.groupname.framework.io.Content;
import com.groupname.framework.serialization.SerializationException;
import com.groupname.game.scene.SceneManager;
import com.groupname.game.scene.SceneName;
import com.groupname.game.data.AppSettings;
import javafx.application.Application;
import javafx.scene.media.MediaException;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * The Main for our application.
 */
public class Main extends Application {

    /**
     * Starts the music, load the settings and takes you to the title screen.
     *
     * @param primaryStage the stage to show the application.
     * @throws Exception Throws exception if SoundPlayer or loadSettings fails.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Content.setContentBaseFolder("/com/groupname/game/resources");

        loadSettings();
        initSoundPlayer();

        SceneManager sceneManager = SceneManager.INSTANCE;
        sceneManager.setPrimaryStage(primaryStage);
        SceneManager.navigate(SceneName.TITLE);

        primaryStage.show();
    }

    private void initSoundPlayer() {
        SoundPlayer soundPlayer = SoundPlayer.INSTANCE;

        try {
            AppSettings settings = AppSettings.INSTANCE;

            soundPlayer.setVolumeMusic(settings.getMusicVolume());
            soundPlayer.setVolumeSoundEffect(settings.getSoundEffectVolume());

            soundPlayer.load();

            System.out.println("SoundPlayer loaded successfully");
        } catch (MediaException ex) {
            System.err.println("Unable load SoundPlayer, audio will be disabled...");
        }
    }

    private void loadSettings() {
        AppSettings settings = AppSettings.INSTANCE;

        try {
            settings.load();
        } catch (IOException exception) {
            System.err.println("Unable to load settings, restoring defaults");
        }
    }

    /**
     * Method that stops all the processes when the application is exiting.
     *
     * @throws Exception if there was an issue stopping the application.
     */
    @Override
    public void stop() throws Exception {
        System.out.println("Stopping audio subsystem...");

        stopMusic();
        saveSettings();
    }

    private void stopMusic() {
        SoundPlayer soundPlayer = SoundPlayer.INSTANCE;
        soundPlayer.stopMusic();
        try {
            soundPlayer.stop();
        } catch (InterruptedException ex) {
            System.err.println(ex.getMessage());
        }
    }

    // Stores general appsettings and saveData (player progress)
    private void saveSettings() {
        AppSettings settings = AppSettings.INSTANCE;

        System.out.println("Saving settings...");

        try {
            settings.save();
            System.out.println("Settings saved successfully...");
        } catch (IOException ex) {
            System.err.println("Unable to store settings..");
        }

        try {
            settings.saveSaveData(Paths.get("save.xml"));
        } catch (SerializationException exception) {
            System.err.println("Unable to save player progress.");
        }
    }

    /**
     * Launch the application.
     *
     * @param args an array of command-line arguments for the application
     */
    public static void main(String[] args) {
        launch(args);
    }


}
