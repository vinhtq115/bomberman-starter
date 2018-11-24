package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
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
		if (_bomber.getXTile() != _e.getXTile() && _bomber.getYTile() != _e.getYTile())
			return new AILow().calculateDirection();
		// Chase bomber
		if (_bomber.getXTile() == _e.getXTile()) {
			if (_bomber.getYTile() < _e.getYTile())
				return 0;
			else
				return 2;
		}
		else
			if (_bomber.getXTile() < _e.getXTile())
				return 3;
			else
				return 1;
	}

}
