package systeme;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * La classe Son est utilisée pour la lecture des fichiers audio.
 * @author Ahmad Khanafer
 */
public class Son {

	/** Utilisé pour ouvrir le fichier audio */
    private Clip clip;
    
    /** Tableau d'URL contenant les chemins des fichiers audio */
    private URL[] soundURL = new URL[30];

    /**
     * Constructeur de la classe Son. Initialise les URL des fichiers audio.
     */
    //Ahmad Khanafer
    public Son() {
        soundURL[0] = getClass().getResource("/son/musiqueApp.wav");
        soundURL[1] = getClass().getResource("/son/gameover.wav");
        soundURL[2] = getClass().getResource("/son/bouttonClicker.wav");
    }

    /**
     * Charge un fichier audio.
     * 
     * @param i l'index du fichier audio dans le tableau soundURL
     */
    //Ahmad Khanafer
    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Joue le fichier audio chargé.
     */
  //Ahmad Khanafer
    public void play() {
        clip.start();
    }

    /**
     * Joue le fichier audio en boucle.
     */
  //Ahmad Khanafer
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Arrête la lecture du fichier audio.
     */
  //Ahmad Khanafer
    public void stop() {
        clip.stop();
    }

    /**
     * Réinitialise la lecture du fichier audio au début.
     */
  //Ahmad Khanafer
    public void reset() {
        clip.setFramePosition(0);
    }

}