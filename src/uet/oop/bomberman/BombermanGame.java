package uet.oop.bomberman;

import uet.oop.bomberman.gui.Frame;

public class BombermanGame {
	
	public static void main(String[] args) {
		String osName = System.getProperty("os.name").toLowerCase();
		boolean isMacOs = osName.startsWith("mac os x");
		if (isMacOs) {
			System.setProperty("apple.laf.useScreenMenuBar", "true"); // If running on macOS, move Menu Bar to Mac menu bar
		}
		new Frame();
	}
}
