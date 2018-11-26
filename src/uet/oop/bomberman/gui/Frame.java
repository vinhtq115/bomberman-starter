package uet.oop.bomberman.gui;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.gui.menu.Menu;

import javax.swing.*;
import java.awt.*;

/**
 * Swing Frame chứa toàn bộ các component
 */
public class Frame extends JFrame {
	
	public GamePanel _gamepane;
	private JPanel _containerpane;
	private InfoPanel _infopanel;
	
	private Game _game;

	public Frame() {
		setJMenuBar(new Menu(this)); // Create menu bar

		_containerpane = new JPanel(new BorderLayout());
		_gamepane = new GamePanel(this);
		_infopanel = new InfoPanel(_gamepane.getGame());
		
		_containerpane.add(_infopanel, BorderLayout.PAGE_START);
		_containerpane.add(_gamepane, BorderLayout.PAGE_END);
		
		_game = _gamepane.getGame();
		
		add(_containerpane);
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);	
		
		_game.start();
	}
	
	public void setTime(int time) {
		_infopanel.setTime(time);
	}
	
	public void setPoints(int points) {
		_infopanel.setPoints(points);
	}

	public void setLives(int lives) {
		_infopanel.setLives(lives);
	}

	public void resetGame() {
		_game.getBoard().resetGame();
	}

	public void setBombRate(int i) {
		_game.setBombRate(i);
	}

	public void setBomberSpeed(double i) {
		_game.setBomberSpeed(i);
	}

	public void setBombRadius(int i) {
		_game.setBombRadius(i);
	}

	public void pause() {
		_game.getBoard().pause();
	}

	public void resume() {
		_game.getBoard().resume();
	}
}
