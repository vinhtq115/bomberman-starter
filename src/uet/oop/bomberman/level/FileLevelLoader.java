package uet.oop.bomberman.level;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Balloon;
import uet.oop.bomberman.entities.character.enemy.Doll;
import uet.oop.bomberman.entities.character.enemy.Oneal;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sounds.Music;
import uet.oop.bomberman.sounds.Sound;

import java.io.*;
import java.net.URL;

public class FileLevelLoader extends LevelLoader {

	/**
	 * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá trị kí tự đọc được
	 * từ ma trận bản đồ trong tệp cấu hình
	 */
	private static char[][] _map;
	
	public FileLevelLoader(Board board, int level) throws LoadLevelException {
		super(board, level);
	}
	
	@Override
	public void loadLevel(int level) throws LoadLevelException{
		Music.getInstance().pauseBackground();
		Sound.getInstance().playLoadLevel();
		String filename = "/levels/Level" + Integer.toString(level) + ".txt";
		URL location = this.getClass().getResource(filename);
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(location.openStream()));
			String info = bufferedReader.readLine();
			String[] split = info.split(" ");
			_level = Integer.parseInt(split[0]);
			_height = Integer.parseInt(split[1]);
			_width = Integer.parseInt(split[2]);
			_map = new char[_height][_width];
			for (int i = 0; i < _height; i++) {
				for (int j = 0; j < _width; j++) {
					_map[i][j] = (char) bufferedReader.read();
				}
				bufferedReader.readLine();
			}
			bufferedReader.close();
		}
		catch (IOException e) {
			System.out.println("Can't load level " + level);
		}
	}

	@Override
	public void createEntities() {
		for (int x = 0; x < _width; x++) {
			for (int y = 0; y < _height; y++) {
				switch (_map[y][x]) {
					// Tiles
					case '#' :	_board.addEntity(x + y * _width, new Wall(x, y, Sprite.wall)); // Add wall
								break;
					case '*' :	_board.addEntity(x + y * _width, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new Brick(x, y, Sprite.brick))); // Add brick
								break;
					case 'x' :	_board.addEntity(x + y * _width, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new Portal(x, y, Sprite.portal, _board), new Brick(x, y, Sprite.brick))); // Add portal
								break;
					// Character
					case 'p' :	_board.addCharacter(new Bomber(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
								Screen.setOffset(0, 0);
								_board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass)); // Add player
								break;
					case '1' :	_board.addCharacter(new Balloon(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board)); // Add balloon
								_board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
								break;
					case '2' :	_board.addCharacter(new Oneal(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board)); // Add oneal
								_board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
								break;
					case '3' :	_board.addCharacter(new Doll(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board)); // Add doll
								_board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
								break;
					// Items
					case 'b' :	_board.addEntity(x + y * _width, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new BombItem(x, y, Sprite.powerup_bombs), new Brick(x, y, Sprite.brick))); // Add bomb
								break;
					case 'f' :	_board.addEntity(x + y * _width, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new FlameItem(x, y, Sprite.powerup_flames), new Brick(x, y, Sprite.brick))); // Add flame
								break;
					case 's' :	_board.addEntity(x + y * _width, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new SpeedItem(x, y, Sprite.powerup_speed), new Brick(x, y, Sprite.brick))); // Add speed
								break;
					// Grass
					default	 :	_board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
								break;
				}
			}
		}
	}

}
