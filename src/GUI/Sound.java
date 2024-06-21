/* Full name: Nguyen Binh Phuong Huy
* Student ID: ITCSIU21189
* Data Structure & Algorithms - Final Project */

package GUI;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;


// Define the following class to add music & sound effects to GUI.FlappyBird Game
public class Sound {
    Clip clip;
    URL[] soundURL = new URL[30];

    // Import sound files using constructor
    public Sound() {
        soundURL[0] = getClass().getResource("/music/FlappyBird.wav");
        soundURL[1] = getClass().getResource("/sound/die.wav");
        soundURL[2] = getClass().getResource("/sound/flap.wav");
        soundURL[3] = getClass().getResource("/sound/point.wav");
        soundURL[4] = getClass().getResource("/sound/swoosh.wav");
        soundURL[5] = getClass().getResource("/sound/hit.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Method to play the sound
    public void play() {
        clip.start();
    }

    // Method to loop the sound continuously
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    // Method to stop the sound
    public void stop() {
        clip.stop();
    }
}
