package uet.oop.bomberman.gui.menu;

import uet.oop.bomberman.gui.Frame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

public class HelpMenu extends JMenu {
    Frame frame;

    HelpMenu(Frame frame) {
        super("Help");
        this.frame = frame;

        // Bomber
        JMenuItem bomberHelp = new JMenuItem("Bomber");
        bomberHelp.addActionListener(helpActionListener);
        add(bomberHelp);
        // Enemy
        JMenuItem enemyHelp = new JMenuItem("Enemy");
        enemyHelp.addActionListener(helpActionListener);
        add(enemyHelp);
        // Items
        JMenuItem itemHelp = new JMenuItem("Items");
        itemHelp.addActionListener(helpActionListener);
        add(itemHelp);
    }

    ActionListener helpActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Bomber")) {
                showHTML("bomber.html");
            }
            if (e.getActionCommand().equals("Enemy")) {
                showHTML("enemies.html");
            }
            if (e.getActionCommand().equals("Items")) {
                showHTML("items.html");
            }
        }
    };

    public void showHTML(String fileName) {
        JTextPane textPane = new JTextPane();
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(textPane);
        JFrame frame = new JFrame();
        frame.getContentPane().add(scrollPane);
        frame.pack();
        frame.setSize(400, 500);
        frame.setVisible(true);
        textPane.setEditable(false);

        // Open URL
        try {
            URL url = getClass().getResource("/help/" + fileName);
            textPane.setPage(url);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
