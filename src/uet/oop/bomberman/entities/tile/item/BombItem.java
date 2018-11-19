package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class BombItem extends Item {

	public BombItem(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	@Override
	public boolean collide(Entity e) {
		if (e instanceof Bomber) {
			if (e.getXTile() == _x && e.getYTile() == _y) { // If bomber walk into bomb item
				Game.addBombRate(1); // Add 1 to bomb rate
				remove(); // Remove from rendering
			}
			return true; // Allow only bomber to walk through
		}
		return false;
	}
	


}
