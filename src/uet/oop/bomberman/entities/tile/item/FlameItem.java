package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class FlameItem extends Item {

	public FlameItem(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	@Override
	public boolean collide(Entity e) {
	    if (isRemoved()) // Avoid re-add bomb radius
	        return true;
		if (e instanceof Bomber) {
            Game.addBombRadius(1); // Add 1 to bomb radius
            remove(); // Remove from rendering
			return true; // Allow only bomber to walk through
		}
		return false;
	}

}
