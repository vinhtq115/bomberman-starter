package uet.oop.bomberman.sounds;

import javax.sound.sampled.*;

public class Music {
    private static Music instance = null;

    private Thread thread;
    private Clip clip;
    private AudioInputStream audioIn;

    private boolean playing = false;

    private Music() {
        try {
            audioIn = AudioSystem.getAudioInputStream(Music.class.getResourceAsStream("/music/background.wav"));
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playBackground() {
        if (playing) // Optimize frame rate
            return;
        thread = new Thread(() -> {
            clip.setFramePosition(0); // Reset music playback
            clip.start();
        });
        thread.setDaemon(true);
        playing = true;
        thread.start();
    }

    public void pauseBackground() {
        if (!playing) // Optimize frame rate
            return;
        clip.stop();
        playing = false;
    }

    public static Music getInstance() {
        if (instance == null)
            instance = new Music();
        return instance;
    }
}
