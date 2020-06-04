package uet.oop.bomberman.sounds;

import javax.sound.sampled.*;

public class Music {
    private static Music instance = null;
    private boolean soundEnabled;

    private Thread thread;
    private Clip clip;
    private AudioInputStream audioIn;

    private boolean playing = false;

    private Music() {
        try {
            soundEnabled = true;
            audioIn = AudioSystem.getAudioInputStream(getClass().getResource("/music/background.wav"));
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playBackground() {
        if (playing || !soundEnabled) // Optimize frame rate
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

    public void disableMusic() {
        soundEnabled = false;
        pauseBackground();
    }

    public void enableMusic() {
        soundEnabled = true;
    }
}
