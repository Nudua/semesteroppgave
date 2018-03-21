package com.groupname.framework.io;

import com.groupname.framework.util.EmptyStringException;
import javafx.scene.image.Image;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.*;

public class ContentTest {

    private final String validSpriteSheet = "player1.png";
    private final String validSprite = "sprite1.png";
    //private final String validSoundEffect = "kabom.wav";
    //private final String validMusicFile = "track1.mp3";

    @Test(expected = NullPointerException.class)
    public void loadFilenameCannotBeNull(){
        Content.loadFile(null,ResourceType.Sprite);
    }

    @Test(expected = EmptyStringException.class)
    public void loadFileNameCannotBeEmpty(){
        Content.loadFile("",ResourceType.Sprite);
    }

    @Test(expected = NullPointerException.class)
    public void loadFileResourceTypeCannotBeNull(){
        Content.loadFile(validSpriteSheet,null);
    }


    @Test(expected = NullPointerException.class)
    public void loadImageNameCannotBeNull(){
        Content.loadImage(null,ResourceType.Sprite);
    }

    @Test(expected = EmptyStringException.class)
    public void loadImageNameCannotBeEmpty(){
        Content.loadImage("",ResourceType.Sprite);
    }

    @Test(expected = IllegalArgumentException.class)
    public void loadImageResourceTypeCannotBeNull(){
        Content.loadImage(validSpriteSheet,null);
    }

    @Test
    public void loadsValidSpriteSheetImage() {
        Image image = Content.loadImage(validSpriteSheet, ResourceType.SpriteSheet);
        assertNotNull(image);
    }

    @Test
    public void loadsValidSpriteSheetInputStream() {
        InputStream inputStream = Content.loadFile(validSpriteSheet, ResourceType.SpriteSheet);
        assertNotNull(inputStream);
    }

    @Test
    public void loadsValidSpriteImage() {
        Image image = Content.loadImage(validSprite, ResourceType.Sprite);
        assertNotNull(image);
    }

    @Test
    public void loadsValidSpriteInputStream() {
        InputStream inputStream = Content.loadFile(validSprite, ResourceType.Sprite);
        assertNotNull(inputStream);
    }

    // Uncomment when we actually have some audio files
    /*
    @Test
    public void loadsValidSoundEffectInputStream() {
        InputStream inputStream = Content.loadFile(validSoundEffect, ResourceType.SoundEffect);
        assertNotNull(inputStream);
    }

    @Test
    public void loadsValidMsuicInputStream() {
        InputStream inputStream = Content.loadFile(validMusicFile, ResourceType.SoundEffect);
        assertNotNull(inputStream);
    }
    */
}
