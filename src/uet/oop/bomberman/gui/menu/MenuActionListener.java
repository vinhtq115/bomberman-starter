package uet.oop.bomberman.gui.menu;

import uet.oop.bomberman.gui.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * For listening to menu action and execute command
 */
public class MenuActionListener implements ActionListener {
    private Frame _frame;

    public MenuActionListener(Frame frame) {
        _frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Reset Game
        if (e.getActionCommand().equals("Reset Game")) {
            _frame.resetGame(); // Perform reset game
        }
        // Custom bomb rate
        if (e.getActionCommand().equals("Custom bombs")) {
            new CustomBombRateDialog(_frame);
        }
        // Custom bomb radius
        if (e.getActionCommand().equals("Custom radius")) {
            new CustomBombRadiusDialog(_frame);
        }
        // Custom bomber speed
        if (e.getActionCommand().equals("Custom speed")) {
            new CustomBomberSpeedDialog(_frame);
        }
    }
}
