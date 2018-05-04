package com.groupname.framework.io;

import com.groupname.framework.util.EmptyStringException;
import javafx.scene.image.Image;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.*;

public class ContentTest {

    private final String validSpriteSheet = "player1.png";
    private final String validSprite = "sprite1.png";
    private final String validSoundEffect = "test.mp3";
    private final String validMusicFile = "punch-deck-by-force.mp3";

    @Test(expected = NullPointerException.class)
    public void loadFilenameCannotBeNull(){
        Content.loadFile(null,ResourceType.SPRITE);
    }

    @Test(expected = EmptyStringException.class)
    public void loadFileNameCannotBeEmpty(){
        Content.loadFile("",ResourceType.SPRITE);
    }

    @Test(expected = NullPointerException.class)
    public void loadFileResourceTypeCannotBeNull(){
        Content.loadFile(validSpriteSheet,null);
    }


    @Test(expected = NullPointerException.class)
    public void loadImageNameCannotBeNull(){
        Content.loadImage(null,ResourceType.SPRITE);
    }

    @Test(expected = EmptyStringException.class)
    public void loadImageNameCannotBeEmpty(){
        Content.loadImage("",ResourceType.SPRITE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void loadImageResourceTypeCannotBeNull(){
        Content.loadImage(validSpriteSheet,null);
    }

    @Test
    public void loadsValidSpriteSheetImage() {
        Image image = Content.loadImage(validSpriteSheet, ResourceType.SPRITE_SHEET);
        assertNotNull(image);
    }

    @Test
    public void loadsValidSpriteSheetInputStream() {
        InputStream inputStream = Content.loadFile(validSpriteSheet, ResourceType.SPRITE_SHEET);
        assertNotNull(inputStream);
    }

    @Test
    public void loadsValidSpriteImage() {
        Image image = Content.loadImage(validSprite, ResourceType.SPRITE);
        assertNotNull(image);
    }

    @Test
    public void loadsValidSpriteInputStream() {
        InputStream inputStream = Content.loadFile(validSprite, ResourceType.SPRITE);
        assertNotNull(inputStream);
    }

    @Test
    public void loadsValidMusicInputStream() {
        InputStream inputStream = Content.loadFile(validMusicFile, ResourceType.MUSIC);
        assertNotNull(inputStream);
    }

    @Test
    public void loadsValidSoundEffectInputStream() {
        InputStream inputStream = Content.loadFile(validSoundEffect, ResourceType.SOUND_EFFECT);
        assertNotNull(inputStream);
    }
}
