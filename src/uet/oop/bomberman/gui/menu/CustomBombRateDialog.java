package uet.oop.bomberman.gui.menu;

import uet.oop.bomberman.gui.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class CustomBombRateDialog implements WindowListener, ActionListener {
    private Frame _frame;
    private JFrame _dialog;
    private JTextField value;

    CustomBombRateDialog(Frame frame) {
        _frame = frame;

        _dialog = new JFrame("Enter custom bomb rate");
        final JButton enter = new JButton("Enter");
        enter.addActionListener(this);

        JPanel pane = new JPanel(new BorderLayout());
        value = new JTextField();

        pane.add(new JLabel("Value: "), BorderLayout.WEST);
        pane.add(value, BorderLayout.CENTER);
        pane.add(enter, BorderLayout.PAGE_END);

        _dialog.getContentPane().add(pane);
        _dialog.setLocationRelativeTo(frame);
        _dialog.setSize(300, 100);
        _dialog.setVisible(true);
        _dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        _dialog.addWindowListener(this);
    }

    @Override
    public void windowOpened(WindowEvent e) {
        _frame.pause();
    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {
        _frame.resume();
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String val = value.getText();
        int i = Integer.parseInt(val);
        _frame.setBombRate(i);
        _dialog.dispose();
    }
}
