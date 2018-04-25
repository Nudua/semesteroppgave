package com.groupname.framework.audio;

import com.groupname.framework.concurrency.TaskRunner;
import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;


/**
 * This Singleton is used to play background music and sound effect clips
 * It supports playing multiple sound effects at the same time.
 *
 * Users should call the stop() method when they are done using this instance.
 */
public enum SoundPlayer {
    INSTANCE;

    private static final String MUSIC_FILENAME = "punch-deck-by-force.mp3";

    private final TaskRunner soundEffectsThread;
    private final Map<SoundEffect, AudioClip> soundEffects;
    private MediaPlayer mediaPlayer;

    SoundPlayer() {
        soundEffectsThread = new TaskRunner(Executors.newFixedThreadPool(5));
        soundEffects = new HashMap<>();
    }

    /**
     * Loads the music and all the sound effects used for this instance.
     */
    public void load() {
        //punch-deck-by-force.wav
        AudioClip shoot = new AudioClip(Content.getResourcePath("test.mp3", ResourceType.SoundEffect));
        soundEffects.put(SoundEffect.Shoot, shoot);

        mediaPlayer = new MediaPlayer(new Media(Content.getResourcePath(MUSIC_FILENAME, ResourceType.Music)));
    }

    /**
     * Plays the default music track
     */
    public void playMusic() {
        if(mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
            mediaPlayer.play();
        }
    }

    public void stopMusic() {
        if(mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.stop();
        }
    }

    public void playSoundEffect(SoundEffect soundEffect) {
        Objects.requireNonNull(soundEffect);

        soundEffectsThread.submit(() -> {
            soundEffects.get(soundEffect).play();
        });
    }

    public void stop() throws InterruptedException {
        soundEffectsThread.stop();
    }

    public enum SoundEffect {
        Shoot,
    }
}
