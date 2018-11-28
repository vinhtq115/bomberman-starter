package uet.oop.bomberman.sounds;

import javax.sound.sampled.*;

public class Sound {

    private AudioInputStream[] audioIn = new AudioInputStream[7];
    private boolean enabled;
    private Clip[] clip = new Clip[7];
    private static Sound instance = null;

    private Sound() {
        enabled = true;
        try {
            audioIn[0] = AudioSystem.getAudioInputStream(Sound.class.getResourceAsStream("/sounds/move1.wav")); // Move by X
            audioIn[1] = AudioSystem.getAudioInputStream(Sound.class.getResourceAsStream("/sounds/move2.wav")); // Move by Y
            audioIn[2] = AudioSystem.getAudioInputStream(Sound.class.getResourceAsStream("/sounds/plant.wav")); // Plant bomb
            audioIn[3] = AudioSystem.getAudioInputStream(Sound.class.getResourceAsStream("/sounds/die.wav")); // Die
            audioIn[4] = AudioSystem.getAudioInputStream(Sound.class.getResourceAsStream("/sounds/after_dead.wav")); // After die
            audioIn[5] = AudioSystem.getAudioInputStream(Sound.class.getResourceAsStream("/sounds/loadlevel.wav")); // Load level sound
            audioIn[6] = AudioSystem.getAudioInputStream(Sound.class.getResourceAsStream("/sounds/explode.wav")); // Bomb explode sound
            for (int i = 0; i < 7; i++) {
                clip[i] = AudioSystem.getClip();
                clip[i].open(audioIn[i]);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Sound getInstance() {
        if (instance == null)
            instance = new Sound();
        return instance;
    }

    public void playX() {
        if (enabled) {
            try {
                clip[0].loop(Clip.LOOP_CONTINUOUSLY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    } // Play sound when bomber move horizontally

    public void stopX() {
        if (enabled) {
            clip[0].stop();
            clip[0].setFramePosition(0);
        }
    } // Stop sound

    public void playY() {
        if (enabled) {
            try {
                clip[1].loop(Clip.LOOP_CONTINUOUSLY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    } // Play sound when bomber move vertically

    public void stopY() {
        if (enabled) {
            clip[1].stop();
            clip[1].setFramePosition(0);
        }
    } // Stop sound

    public void playPlant() {
        if (enabled) {
            clip[2].setFramePosition(0);
            clip[2].start();
        }
    } // Play sound when bomber plants bomb

    public void playLoadLevel() {
        if (enabled) {
            clip[5].setFramePosition(0);
            clip[5].start();
        }
    } // Play sound when loading level

    public void playDie() {
        if (enabled) {
            clip[3].setFramePosition(0);
            clip[3].start();
        }
    } // Play sound when bomber die

    public void playAfterDead() {
        if (enabled) {
            clip[4].start();
        }
    } // Play sound when game end

    public void playBomb() {
        if (enabled) {
            clip[6].setFramePosition(0);
            clip[6].start();
        }
    } // Play sound when bomb explode

    public void enableSound() {
        enabled = true;
    }

    public void disableSound() {
        enabled = false;
    }
}
