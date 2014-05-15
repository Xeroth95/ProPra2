package de.hhu.propra14.team132.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * Created by fabian on 15.05.14.
 */
public class SoundEngine {
    public synchronized void play(final File audioFile) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream audioInputStream=AudioSystem.getAudioInputStream(audioFile);
                    clip.open(audioInputStream);
                    clip.start();
                }
                catch (Exception e) {
                    System.err.println("Error playing file: "+audioFile.getName());
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
