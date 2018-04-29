package com.groupname.game;

import com.groupname.framework.audio.SoundPlayer;
import com.groupname.framework.io.Content;
import com.groupname.game.scene.SceneManager;
import com.groupname.game.scene.SceneName;
import com.groupname.game.data.AppSettings;
import javafx.application.Application;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // We have to set this before running
        Content.setContentBaseFolder("/com/groupname/game/resources");

        loadSettings();
        initSoundPlayer();

        SceneManager sceneManager = SceneManager.INSTANCE;
        sceneManager.setPrimaryStage(primaryStage);
        SceneManager.navigate(SceneName.Title);

        primaryStage.show();
    }

    private void initSoundPlayer() {
        SoundPlayer soundPlayer = SoundPlayer.INSTANCE;

        try {
            soundPlayer.load();

            AppSettings settings = AppSettings.INSTANCE;

            soundPlayer.setVolumeMusic(settings.getMusicVolume());
            soundPlayer.setVolumeSoundEffect(settings.getSoundEffectVolume());
            soundPlayer.playMusic();

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

    @Override
    public void stop() throws Exception {
        System.out.println("Stopping audio subsystem...");

        SoundPlayer soundPlayer = SoundPlayer.INSTANCE;
        soundPlayer.stopMusic();
        try {
            soundPlayer.stop();
        } catch (InterruptedException ex) {
            System.err.println(ex.getMessage());
        }

        AppSettings settings = AppSettings.INSTANCE;

        System.out.println("Saving settings...");

        try {
            settings.save();
            System.out.println("Settings saved successfully...");
        } catch (IOException ex) {
            System.err.println("Unable to store settings..");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


}
