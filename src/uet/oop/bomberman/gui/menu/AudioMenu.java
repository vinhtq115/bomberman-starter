package uet.oop.bomberman.gui.menu;

import uet.oop.bomberman.gui.Frame;
import uet.oop.bomberman.sounds.Music;
import uet.oop.bomberman.sounds.Sound;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class AudioMenu extends JMenu {
    Frame frame;

    AudioMenu(Frame frame) {
        super("Audio");
        this.frame = frame;

        // Enable/disable music
        JCheckBoxMenuItem music = new JCheckBoxMenuItem("Music", true);
        music.addActionListener(musicActionListener);
        add(music);
        // Enable/disable sound effects
        JCheckBoxMenuItem sound = new JCheckBoxMenuItem("SFX", true);
        sound.addActionListener(soundActionListener);
        add(sound);
    }

    ActionListener musicActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            AbstractButton tick = (AbstractButton) e.getSource();
            boolean selected = tick.getModel().isSelected();
            if (selected) {
                Music.getInstance().enableMusic();
            }
            else {
                Music.getInstance().disableMusic();
            }
        }
    };

    ActionListener soundActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            AbstractButton tick = (AbstractButton) e.getSource();
            boolean selected = tick.getModel().isSelected();
            if (selected) {
                Sound.getInstance().enableSound();
            }
            else {
                Sound.getInstance().disableSound();
            }
        }
    };
}
