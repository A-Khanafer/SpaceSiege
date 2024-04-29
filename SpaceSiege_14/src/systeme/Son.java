package systeme;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Son {

    // use this to open sound file
    Clip clip;
    URL soundURL[] = new URL[30];

    public Son() {
        soundURL[0] = getClass().getResource("/son/musiqueApp.wav");
        soundURL[1] = getClass().getResource("/son/gameover.wav");
        soundURL[2] = getClass().getResource("/son/bouttonClicker.wav");
        
    }

    public void setFile (int i) {

        try {

            AudioInputStream ais= AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        }catch (Exception e) {
        	
        }
    }
    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
    
    public void reset() {
    	clip.setFramePosition(0);
    }



}