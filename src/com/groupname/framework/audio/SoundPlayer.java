package com.groupname.framework.audio;

import com.groupname.framework.concurrency.TaskRunner;
import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;


/**
 * This Singleton is used to play background music and sound effect clips
 * It supports playing multiple sound effects at the same time.
 *
 * Users must call the stop() method when they are done using this instance.
 */
public enum SoundPlayer {
    INSTANCE;

    private static final String MUSIC_MAIN = "punch-deck-by-force.mp3";
    private static final String MUSIC_EDITOR = "punch-deck-by-force.mp3";
    private static final String MUSIC_CREDITS = "punch-deck-by-force.mp3";

    private final TaskRunner soundEffectsThread;
    private final Map<SoundEffect, AudioClip> soundEffects;
    private final Map<MusicTrack, Media> musicTracks;
    private MediaPlayer mediaPlayer;

    SoundPlayer() {
        soundEffects = new HashMap<>();
        musicTracks = new HashMap<>();

        soundEffectsThread = new TaskRunner(Executors.newFixedThreadPool(5));
    }

    /**
     * Loads the music and all the sound effects used for this instance.
     */
    public void load() {
        //punch-deck-by-force.wav
        AudioClip shoot = new AudioClip(Content.getResourcePath("test.mp3", ResourceType.SoundEffect));
        soundEffects.put(SoundEffect.Shoot, shoot);

        musicTracks.put(MusicTrack.Main, new Media(Content.getResourcePath(MUSIC_MAIN, ResourceType.Music)));
        musicTracks.put(MusicTrack.Editor, new Media(Content.getResourcePath(MUSIC_MAIN, ResourceType.Music)));
        musicTracks.put(MusicTrack.Credits, new Media(Content.getResourcePath(MUSIC_MAIN, ResourceType.Music)));
    }

    /**
     * Plays the default musicTrack which is MusicTrack.Main
     */
    public void playMusic() {
        playMusic(MusicTrack.Main);
    }


    /**
     * Plays the specified MusicTrack.
     *
     * @param track the MusicTrack to play.
     */
    public void playMusic(MusicTrack track) {
        Objects.requireNonNull(track);

        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }

        mediaPlayer = new MediaPlayer(musicTracks.get(track));
        mediaPlayer.play();
    }

    /**
     * Attempts to stop playing music if there are any music playing.
     */
    public void stopMusic() {
        if(mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.stop();
        }
    }

    /**
     * Submits a SoundEffect to be played on a separate thread.
     *
     * @param soundEffect the SoundEffect to be played on a separate thread.
     */
    public void playSoundEffect(SoundEffect soundEffect) {
        Objects.requireNonNull(soundEffect);

        soundEffectsThread.submit(() -> {
            soundEffects.get(soundEffect).play();
        });
    }

    /**
     * Stops the ExecutorService used by this instance to play SoundEffects.
     * No soundeffects may be played after calling this method.
     * This method must be called before closing any application to prevent it from stalling.
     *
     * @throws InterruptedException if the ExecutorService was interrupted while attempting to stop.
     */
    public void stop() throws InterruptedException {
        soundEffectsThread.stop();
    }

    /**
     * An enum that describes all of the SoundEffects available for play by this class.
     */
    public enum SoundEffect {
        /**
         * Shooting sound effect.
         */
        Shoot,
    }

    /**
     * An enum that describes all of the MusicTracks available for play by this class.
     */
    public enum MusicTrack {
        /**
         * The Main theme of this game.
         */
        Main,
        /**
         * The editor theme of this game. (elevator-esque music)
         */
        Editor,
        /**
         * The credits theme of this game.
         */
        Credits
    }

    /**
     * Returns the TaskRunner used internally by this class, as well as
     * the list of soundEffects, musicTracks used and the mediaplayer if it is not null.
     *
     * @return the TaskRunner used internally by this class, as well as
     *         the list of soundEffects, musicTracks used and the mediaplayer if it is not null.
     */
    @Override
    public String toString() {
        return "SoundPlayer{" +
                "soundEffectsThread=" + soundEffectsThread +
                ", soundEffects=" + soundEffects +
                ", musicTracks=" + musicTracks +
                ", mediaPlayer=" + mediaPlayer +
                '}';
    }
}
