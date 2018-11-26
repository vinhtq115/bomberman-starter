package uet.oop.bomberman.gui.menu;

import uet.oop.bomberman.gui.Frame;

import javax.swing.*;

class CheatMenu extends JMenu{
    Frame frame;

    CheatMenu(Frame frame) {
        super("Cheats");
        this.frame = frame;

        // Custom bomb rate
        JMenuItem customBomb = new JMenuItem("Custom bombs");
        customBomb.addActionListener(new MenuActionListener(frame));
        add(customBomb);
        // Custom speed
        JMenuItem customSpeed = new JMenuItem("Custom speed");
        customSpeed.addActionListener(new MenuActionListener(frame));
        add(customSpeed);
        // Custom bomb radius
        JMenuItem customRadius = new JMenuItem("Custom radius");
        customRadius.addActionListener(new MenuActionListener(frame));
        add(customRadius);
    }
}
