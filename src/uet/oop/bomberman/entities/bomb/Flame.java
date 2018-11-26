package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.graphics.Screen;

public class Flame extends Entity {

	protected Board _board;
	protected int _direction;
	private int _radius;
	protected int xOrigin, yOrigin;
	protected FlameSegment[] _flameSegments = new FlameSegment[0];

	/**
	 *
	 * @param x hoành độ bắt đầu của Flame
	 * @param y tung độ bắt đầu của Flame
	 * @param direction là hướng của Flame
	 * @param radius độ dài cực đại của Flame
	 */
	public Flame(int x, int y, int direction, int radius, Board board) {
		xOrigin = x;
		yOrigin = y;
		_x = x;
		_y = y;
		_direction = direction;
		_radius = radius;
		_board = board;
		createFlameSegments();
	}

	/**
	 * Tạo các FlameSegment, mỗi segment ứng một đơn vị độ dài
	 */
	private void createFlameSegments() {
		/**
		 * tính toán độ dài Flame, tương ứng với số lượng segment
		 */
		_flameSegments = new FlameSegment[calculatePermitedDistance()];

		/**
		 * biến last dùng để đánh dấu cho segment cuối cùng
		 */
		boolean last = false;

		int currentX = xOrigin, currentY = yOrigin;
		for (int i = 0; i < _flameSegments.length; i++) {
			switch (_direction) {
				case 0 : 	currentY--; // Up
							break;
				case 1 :	currentX++; // Right
							break;
				case 2 :	currentY++; // Down
							break;
				case 3 :	currentX--; // Left
							break;
			}
			// Mark last segment
			if (i == _flameSegments.length - 1) {
				last = true;
			}
			_flameSegments[i] = new FlameSegment(currentX, currentY, _direction, last);
		}
	}

	/**
	 * Tính toán độ dài của Flame, nếu gặp vật cản là Brick/Wall, độ dài sẽ bị cắt ngắn
	 * @return
	 */
	private int calculatePermitedDistance() {
		int distance = 0; // Distance of flame
		int currentX = xOrigin, currentY = yOrigin;
		while (distance < _radius) {
			switch (_direction) {
				case 0 : 	currentY--; // Up
							break;
				case 1 :	currentX++; // Right
							break;
				case 2 :	currentY++; // Down
							break;
				case 3 :	currentX--; // Left
							break;
			}
			Entity e = _board.getEntity(currentX, currentY, null); // Get entity at (currentX;currentY)

			if (e instanceof Character)
				distance++;
			if (!e.collide(this)) // If face wall, brick, bomber or enemy, stop increasing distance
				break;
			distance++;
		}
		return distance;
	}
	
	public FlameSegment flameSegmentAt(int x, int y) {
		for (int i = 0; i < _flameSegments.length; i++) {
			if(_flameSegments[i].getX() == x && _flameSegments[i].getY() == y)
				return _flameSegments[i];
		}
		return null;
	}

	@Override
	public void update() {}
	
	@Override
	public void render(Screen screen) {
		for (int i = 0; i < _flameSegments.length; i++) {
			_flameSegments[i].render(screen);
		}
	}

	@Override
	public boolean collide(Entity e) {
		return true;
	}
}
