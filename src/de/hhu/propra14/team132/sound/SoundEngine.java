package de.hhu.propra14.team132.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

/**
 * Created by fabian on 15.05.14.
 */
public class SoundEngine {
	private static File introSound;
	private static File clickSound;
	
	public static void init(File introSound, File clickSound) {
		SoundEngine.introSound 	= introSound;
		SoundEngine.clickSound	= clickSound;
	}
	
    public final synchronized void play(final File audioFile, final float fxVolumePercentage) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream audioInputStream=AudioSystem.getAudioInputStream(audioFile);
                    clip.open(audioInputStream);
                    FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    int fxVolume= (int) ((int)volume.getMinimum()+((volume.getMaximum()-volume.getMinimum())/100*fxVolumePercentage));//calculate volume: 0% = -80db, 100%=6db
                    volume.setValue(fxVolume);
                    clip.start();
                }
                catch (Exception e) {
                    System.err.println("Error playing file: "+audioFile.getName());
                    e.printStackTrace();
                }
            }
        }).start();
    }
    
    public static synchronized void playIntro(final float fxVolumePercentage) {
    	new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream audioInputStream=AudioSystem.getAudioInputStream(SoundEngine.introSound);
                    clip.open(audioInputStream);
                    FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    int fxVolume= (int) ((int)volume.getMinimum()+((volume.getMaximum()-volume.getMinimum())/100*fxVolumePercentage));//calculate volume: 0% = -80db, 100%=6db
                    volume.setValue(fxVolume);
                    clip.start();
                }
                catch (Exception e) {
                    System.err.println("Error playing intro music (File : \"" + SoundEngine.introSound.getName() + "\")");
                    e.printStackTrace();
                }
            }
        }).start();
    }
    
    public static synchronized void playClick(final float fxVolumePercentage) {
    	new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream audioInputStream=AudioSystem.getAudioInputStream(SoundEngine.clickSound);
                    clip.open(audioInputStream);
                    FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    int fxVolume= (int) ((int)volume.getMinimum()+((volume.getMaximum()-volume.getMinimum())/100*fxVolumePercentage));//calculate volume: 0% = -80db, 100%=6db
                    volume.setValue(fxVolume);
                    clip.start();
                }
                catch (Exception e) {
                    System.err.println("Error playing intro music (File : \"" + SoundEngine.clickSound.getName() + "\")");
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
