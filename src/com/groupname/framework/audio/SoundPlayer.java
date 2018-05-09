package com.groupname.framework.audio;

import com.groupname.framework.concurrency.TaskRunner;
import com.groupname.framework.io.Content;
import com.groupname.framework.io.ResourceType;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
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

    //private static final String MUSIC_MAIN = "punch-deck-by-force.mp3";
    private static final String MUSIC_MAIN = "punch-deck-by-force.mp3";
    private static final String MUSIC_EDITOR = "elevator1.mp3";
    private static final String MUSIC_CREDITS = "intro.mp3";

    private final TaskRunner soundEffectsThread;
    private final Map<SoundEffect, AudioClip> soundEffects;
    private final Map<MusicTrack, Media> musicTracks;
    private MediaPlayer mediaPlayer;

    private static final double MAX_VOLUME = 1.0d;
    private static final double MIN_VOLUME = 0.0d;

    private double volumeMusic = MAX_VOLUME;
    private double volumeSoundEffect = MAX_VOLUME;

    private boolean initialized = false;

    SoundPlayer() {
        soundEffects = new HashMap<>();
        musicTracks = new HashMap<>();

        soundEffectsThread = new TaskRunner(Executors.newFixedThreadPool(5));
    }

    /**
     * Sets the volume for the music.
     *
     * Range 0.0d to 1.0d, values outside this range will be clamped.
     *
     * @param volume the new volume to set.
     */
    public void setVolumeMusic(double volume) {
        this.volumeMusic = clampVolume(volume);
    }

    /**
     * Sets the volume for the sound effects.
     *
     * Range 0.0d to 1.0d, values outside this range will be clamped.
     *
     * @param volume the new volume to set.
     */
    public void setVolumeSoundEffect(double volume) {
        this.volumeSoundEffect = clampVolume(volume);
    }

    // Clamp the volume between 0.0d (min) and 1.0d (max)
    private double clampVolume(double volume) {
        if(volume < MIN_VOLUME) {
            return MIN_VOLUME;
        } else if(volume > MAX_VOLUME) {
            return MAX_VOLUME;
        }

        // We're inside the range of min and max so just return
        return volume;
    }

    /**
     * Loads the music and all the sound effects used for this instance
     * and attempts to start playing the main theme of the game.
     *
     * If there is an issue will loading the media, audio will be disabled.
     *
     * @throws MediaException if the mediaformat is not supported on this system.
     */
    public void load() throws MediaException {
        AudioClip shoot = new AudioClip(Content.getResourcePath("test.mp3", ResourceType.SOUND_EFFECT));
        soundEffects.put(SoundEffect.SHOOT, shoot);

        musicTracks.put(MusicTrack.MAIN, new Media(Content.getResourcePath(MUSIC_MAIN, ResourceType.MUSIC)));
        musicTracks.put(MusicTrack.EDITOR, new Media(Content.getResourcePath(MUSIC_EDITOR, ResourceType.MUSIC)));
        musicTracks.put(MusicTrack.CREDITS, new Media(Content.getResourcePath(MUSIC_CREDITS, ResourceType.MUSIC)));

        mediaPlayer = new MediaPlayer(musicTracks.get(MusicTrack.MAIN));
        mediaPlayer.setVolume(volumeMusic);
        mediaPlayer.play();

        initialized = true;
    }

    /**
     * Plays the default musicTrack which is MusicTrack.MAIN
     */
    public void playMusic() {
        if(!initialized) {
            return;
        }

        playMusic(MusicTrack.MAIN);
    }


    /**
     * Plays the specified MusicTrack.
     *
     * @param track the MusicTrack to play.
     */
    public void playMusic(MusicTrack track) {
        if(!initialized) {
            return;
        }

        Objects.requireNonNull(track);

        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }

        Media media = musicTracks.get(track);

        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(volumeMusic);
        mediaPlayer.play();
    }

    /**
     * Attempts to stop playing music if there are any music playing.
     */
    public void stopMusic() {
        if(initialized && mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.stop();
        }
    }

    /**
     * Submits a SOUND_EFFECT to be played on a separate thread.
     *
     * @param soundEffect the SOUND_EFFECT to be played on a separate thread.
     */
    public void playSoundEffect(SoundEffect soundEffect) {
        if(!initialized) {
            return;
        }

        Objects.requireNonNull(soundEffect);

        soundEffectsThread.submit(() -> {
            if(soundEffects.containsKey(soundEffect)) {
                AudioClip effect = soundEffects.get(soundEffect);
                effect.setVolume(volumeSoundEffect);
                effect.play();
            }
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
        SHOOT
    }

    /**
     * An enum that describes all of the MusicTracks available for play by this class.
     */
    public enum MusicTrack {
        /**
         * The MAIN theme of this game.
         */
        MAIN,
        /**
         * The editor theme of this game. (elevator-esque music)
         */
        EDITOR,
        /**
         * The credits theme of this game.
         */
        CREDITS
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
