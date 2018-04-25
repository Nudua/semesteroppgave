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



public enum SoundPlayer {
    INSTANCE;

    private final TaskRunner soundEffectsThread;
    private final Map<SoundEffect, AudioClip> soundEffects;
    private MediaPlayer mediaPlayer;

    SoundPlayer() {
        soundEffectsThread = new TaskRunner(Executors.newFixedThreadPool(5));
        //mediaPlayer = new MediaPlayer();
        soundEffects = new HashMap<>();
    }

    public void load() {
        //punch-deck-by-force.wav
        AudioClip shoot = new AudioClip(Content.getResourcePath("test.mp3", ResourceType.SoundEffect));
        soundEffects.put(SoundEffect.Shoot, shoot);

        mediaPlayer = new MediaPlayer(new Media(Content.getResourcePath("punch-deck-by-force.wav", ResourceType.Music)));
        mediaPlayer.play();
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
