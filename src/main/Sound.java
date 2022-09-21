package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

public class Sound {
    Clip clip;
    URL[] soundURL = new URL[30];

    public Sound() {
        soundURL[0] = Sound.class.getResource("/sound/BlueBoyAdventure.wav");
        soundURL[1] = Sound.class.getResource("/sound/coin.wav");
        soundURL[2] = Sound.class.getResource("/sound/powerup.wav");
        soundURL[3] = Sound.class.getResource("/sound/unlock.wav");
        soundURL[4] = Sound.class.getResource("/sound/fanfare.wav");
        soundURL[5] = Sound.class.getResource("/sound/hitmonster.wav");
        soundURL[6] = Sound.class.getResource("/sound/receivedamage.wav");
        soundURL[7] = Sound.class.getResource("/sound/swingweapon.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
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
}
