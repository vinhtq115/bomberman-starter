package uet.oop.bomberman.level;

import uet.oop.bomberman.Game;

public class Coordinates {

	/**
	 * Convert pixel to tile
	 * @param i pixel
	 * @return tile
	 */
	public static int pixelToTile(double i) {
		return (int)(i / Game.TILES_SIZE);
	}

	/**
	 * Convert tile to pixel
	 * @param i tile
	 * @return pixel
	 */
	public static int tileToPixel(int i) {
		return i * Game.TILES_SIZE;
	}
	
	public static int tileToPixel(double i) {
		return (int)(i * Game.TILES_SIZE);
	}
	
	
}
