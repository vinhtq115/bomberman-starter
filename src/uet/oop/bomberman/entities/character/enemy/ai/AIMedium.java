package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

public class AIMedium extends AI {
	Bomber _bomber;
	Enemy _e;
	
	public AIMedium(Bomber bomber, Enemy e) {
		_bomber = bomber;
		_e = e;
	}

	@Override
	public int calculateDirection() {
		int direction = 1;
		if (_bomber.getXTile() != _e.getXTile() && _bomber.getYTile() != _e.getYTile())
			direction = new AILow().calculateDirection();
		// Chase bomber
		if (_bomber.getXTile() == _e.getXTile()) {
			if (_bomber.getYTile() < _e.getYTile())
				direction = 0;
			else
				direction = 2;
		}
		else if (_bomber.getYTile() == _e.getYTile()) {
			if (_bomber.getXTile() < _e.getXTile())
				direction = 3;
			else
				direction = 1;
		}
		// Avoid bomb
		switch (direction) {
			case 0: { // Up
				for (int i = 0; i <= Game.getBombRadius(); i++) {
					if (_e.getBoard().getBombAt(Coordinates.pixelToTile(_e.getX()), Coordinates.pixelToTile(_e.getY() - Game.TILES_SIZE - _e.getSpeed()) - i) != null) {
						direction = 2;
						break;
					}
				}
				break;
			}
			case 1: { // Right
				for (int i = 0; i <= Game.getBombRadius(); i++) {
					if (_e.getBoard().getBombAt(i + Coordinates.pixelToTile(_e.getX() + _e.getSprite().getRealWidth() + _e.getSpeed()), _e.getYTile()) != null) {
						direction = 3;
						break;
					}
				}
				break;
			}
			case 2: { // Down
				for (int i = 0; i <= Game.getBombRadius(); i++) {
					if (_e.getBoard().getBombAt(Coordinates.pixelToTile(_e.getX()), Coordinates.pixelToTile(_e.getY() - Game.TILES_SIZE + Sprite.bomb.getRealHeight() + _e.getSpeed()) + i) != null) {
						direction = 0;
						break;
					}
				}
				break;
			}
			case 3: { // Left
				for (int i = 0; i <= Game.getBombRadius(); i++) {
					if (_e.getBoard().getBombAt(Coordinates.pixelToTile(_e.getX() - _e.getSpeed()) - i, _e.getYTile()) != null) {
						direction = 1;
						break;
					}
				}
				break;
			}
		}
		return direction;
	}
}
