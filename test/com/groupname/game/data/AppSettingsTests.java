package com.groupname.game.data;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class AppSettingsTests {

    @Test
    public void getMusicVolumeIsConsistent() {
        AppSettings settings = AppSettings.INSTANCE;

        final double volume = 1.0d;

        settings.setMusicVolume(volume);
        assertEquals(settings.getMusicVolume(), volume, 0.0d);
    }

    @Test
    public void getSoundEffectVolumeIsConsistent() {
        AppSettings settings = AppSettings.INSTANCE;

        final double volume = 1.0d;

        settings.setSoundEffectVolume(volume);
        assertEquals(settings.getSoundEffectVolume(), volume, 0.0d);
    }

    @Test
    public void loadIsConsistent() throws IOException {
        AppSettings settings = AppSettings.INSTANCE;
        settings.load();

        double musicVolume = 0.5d;
        double soundEffectVolume = 0.7d;

        settings.setMusicVolume(musicVolume);
        settings.setSoundEffectVolume(soundEffectVolume);

        settings.save();

        // Update values, but don't save
        settings.setMusicVolume(0.9d);
        settings.setSoundEffectVolume(0.8d);

        assertEquals(settings.getMusicVolume(), 0.9d, 0.0d);
        assertEquals(settings.getSoundEffectVolume(), 0.8d, 0.0d);

        // Restore to the values we set before
        settings.load();

        assertEquals(settings.getMusicVolume(), musicVolume, 0.0d);
        assertEquals(settings.getSoundEffectVolume(), soundEffectVolume, 0.0d);
    }

}
