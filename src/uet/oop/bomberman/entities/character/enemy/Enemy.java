package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Message;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.character.enemy.ai.AI;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

import java.awt.*;

public abstract class Enemy extends Character {

	protected int _points;
	
	protected double _speed;
	protected AI _ai;

	protected final double MAX_STEPS;
	protected final double rest;
	protected double _steps;
	
	protected int _finalAnimation = 30;
	protected Sprite _deadSprite;
	
	public Enemy(int x, int y, Board board, Sprite dead, double speed, int points) {
		super(x, y, board);
		
		_points = points;
		_speed = speed;
		
		MAX_STEPS = Game.TILES_SIZE / _speed;
		rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
		_steps = MAX_STEPS;
		
		_timeAfter = 20;
		_deadSprite = dead;
	}
	
	@Override
	public void update() {
		animate();
		
		if(!_alive) {
			afterKill();
			return;
		}
		
		if(_alive)
			calculateMove();
	}
	
	@Override
	public void render(Screen screen) {
		
		if(_alive)
			chooseSprite();
		else {
			if(_timeAfter > 0) {
				_sprite = _deadSprite;
				_animate = 0;
			} else {
				_sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, _animate, 60);
			}
				
		}
			
		screen.renderEntity((int)_x, (int)_y - _sprite.SIZE, this);
	}
	
	@Override
	public void calculateMove() {
		double xa = 0, ya = 0;
		if (_steps <= 0) {
			_steps = MAX_STEPS; // Limit number of enemy's steps
			_direction = _ai.calculateDirection(); // Generate new direction
		}

		switch (_direction) {
			case 0 : { // Up
				ya -= _speed;
				break;
			}
			case 1 : { // Right
				xa += _speed;
				break;
			}
			case 2 : { // Down
				ya += _speed;
				break;
			}
			case 3 : { // Left
				xa -= _speed;
				break;
			}
		}

		if (canMove(xa, ya)) {
			_steps--;
			move(xa, ya);
			_moving = true;
		}
		else {
			_steps = 0; // For reset direction
			_moving = false;
		}
	}
	
	@Override
	public void move(double xa, double ya) {
		if(!_alive) return;
		_y += ya;
		_x += xa;
	}

	@Override
	public boolean canMove(double xa, double ya) {
		Entity[] e = new Entity[4];
		e[0] = _board.getEntity(Coordinates.pixelToTile(_x + xa), Coordinates.pixelToTile(_y + ya - Game.TILES_SIZE), this); // e[0]: upper left corner
		e[1] = _board.getEntity(Coordinates.pixelToTile(_x + xa + _sprite.getRealWidth() - 1), Coordinates.pixelToTile(_y + ya - Game.TILES_SIZE), this); // e[1]: upper right corner
		e[2] = _board.getEntity(Coordinates.pixelToTile(_x + xa), Coordinates.pixelToTile(_y + ya - 1), this); // e[2]: lower left corner
		e[3] = _board.getEntity(Coordinates.pixelToTile(_x + xa + _sprite.getRealWidth() - 1), Coordinates.pixelToTile(_y + ya - Game.TILES_SIZE), this); // e[3]: lower right corner
		if (!e[0].collide(this) || !e[1].collide(this) || !e[2].collide(this) || !e[3].collide(this))
			return false;
		return true;
	}

	@Override
	public boolean collide(Entity e) {
		if (e instanceof Flame) {
			this.kill();
			return true; // Let flame go through enemy
		}
		if (e instanceof Enemy) {
			return false;
		}
		if (e instanceof Bomber) {
			Bomber bomber = (Bomber) e;
			bomber.kill();
			return false;
		}
		return true;
	}
	
	@Override
	public void kill() {
		if(!_alive) return;
		_alive = false;
		
		_board.addPoints(_points);

		Message msg = new Message("+" + _points, getXMessage(), getYMessage(), 2, Color.white, 14);
		_board.addMessage(msg);
	}
	
	
	@Override
	protected void afterKill() {
		if(_timeAfter > 0) --_timeAfter;
		else {
			if(_finalAnimation > 0) --_finalAnimation;
			else
				remove();
		}
	}
	
	protected abstract void chooseSprite();

	public double getSpeed() {
		return _speed;
	}
}
