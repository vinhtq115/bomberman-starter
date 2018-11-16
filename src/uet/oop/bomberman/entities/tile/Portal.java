package uet.oop.bomberman.entities.tile;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class Portal extends Tile {

	private Board board; // For detectNoEnemies method

	public Portal(int x, int y, Sprite sprite, Board board) {
		super(x, y, sprite);
		this.board = board;
	}

	@Override
	public boolean collide(Entity e) {
		if (e instanceof Bomber) {
			if (!board.detectNoEnemies()) // Check if there are enemies left
				return false;
			if (e.getXTile() == getX() && e.getYTile() == getY()) // Check if player is standing in portal
				board.nextLevel();
			return true;
		}
		return false;
	}

}
