package com.groupname.framework.audio;

import org.junit.Test;
import static org.junit.Assert.*;

public class SoundPlayerTests {

    @Test(expected = NullPointerException.class)
    public void playMusicThrowsOnNullParameter() {
        SoundPlayer.INSTANCE.playMusic(null);
    }

    @Test(expected = NullPointerException.class)
    public void playSoundeffectThrowsOnNullParameter() {
        SoundPlayer.INSTANCE.playSoundEffect(null);
    }

}
