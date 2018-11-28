package uet.oop.bomberman.gui.menu;

import uet.oop.bomberman.gui.Frame;

import javax.swing.*;

public class Menu extends JMenuBar {
    public Menu(Frame frame) {
        add(new GameMenu(frame));
        add(new AudioMenu(frame));
        add(new CheatMenu(frame));
    }
}
