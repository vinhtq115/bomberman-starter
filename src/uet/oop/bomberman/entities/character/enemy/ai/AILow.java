package uet.oop.bomberman.entities.character.enemy.ai;

import java.util.Random;

public class AILow extends AI {

	@Override
	public int calculateDirection() {
		Random rand = new Random();
		return rand.nextInt(4);
	}

}
