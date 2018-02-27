package com.groupname.framework.audio;


import javafx.scene.media.AudioClip;

import javax.sound.sampled.*;
import java.io.*;
import java.net.URL;


public class SoundManager {

 private final AudioClip Crash;
 private final AudioClip Hit;
 private final AudioClip BG;

private Clip clip;
     SoundManager(String Sound) {

        Crash= new AudioClip("file:///C:/Users/phamd/Documents/java/src/lyd/background.wav");
        Hit= new AudioClip("file:///C:/Users/phamd/Documents/java/src/lyd/background.wav");
        BG= new AudioClip("file:///C:/Users/phamd/Documents/java/src/lyd/background.wav");

        try {
            URL url = this.getClass().getResource(Sound);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        }catch (IOException e){
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

    }

    public void play(final AudioClip clip){
        clip.play();
    }
    public void stop(final AudioClip clip){
        clip.stop();
    }

}