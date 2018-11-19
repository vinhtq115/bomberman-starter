package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class SpeedItem extends Item {

	public SpeedItem(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	@Override
	public boolean collide(Entity e) {
		if (isRemoved()) // Avoid re-add speed
			return true;
		if (e instanceof Bomber) {
			Game.addBomberSpeed(0.1); // Add speed
			remove(); // Remove from rendering
			return true; // Allow bomber to walk through
		}
		return false;
	}
}