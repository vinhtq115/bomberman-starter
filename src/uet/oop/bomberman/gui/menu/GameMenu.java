package uet.oop.bomberman.gui.menu;

import uet.oop.bomberman.gui.Frame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

class GameMenu extends JMenu {
    Frame frame;

    GameMenu(Frame frame) {
        super("Game");
        this.frame = frame;
        // Reset game
        JMenuItem reset = new JMenuItem("Reset Game");
        reset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        reset.addActionListener(new MenuActionListener(frame));
        add(reset);
    }
}
