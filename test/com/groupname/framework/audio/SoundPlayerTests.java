package com.groupname.framework.audio;

import com.groupname.framework.io.Content;
import org.junit.BeforeClass;
import org.junit.Test;
import test.util.MockFX;

import static org.junit.Assert.*;

public class SoundPlayerTests {

    @BeforeClass
    public static void init() {
        MockFX.initFX();
        Content.setContentBaseFolder("/com/groupname/game/resources");
        SoundPlayer.INSTANCE.load();
    }

    @Test(expected = NullPointerException.class)
    public void playMusicThrowsOnNullParameter() {
        SoundPlayer.INSTANCE.playMusic(null);
    }

    @Test(expected = NullPointerException.class)
    public void playSoundeffectThrowsOnNullParameter() {
        SoundPlayer.INSTANCE.playSoundEffect(null);
    }

}
