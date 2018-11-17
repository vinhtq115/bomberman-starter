package uet.oop.bomberman.entities.character;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;
import uet.oop.bomberman.level.Coordinates;

import java.util.Iterator;
import java.util.List;

public class Bomber extends Character {

    private List<Bomb> _bombs;
    protected Keyboard _input;

    /**
     * nếu giá trị này < 0 thì cho phép đặt đối tượng Bomb tiếp theo,
     * cứ mỗi lần đặt 1 Bomb mới, giá trị này sẽ được reset về 0 và giảm dần trong mỗi lần update()
     */
    protected int _timeBetweenPutBombs = 0;

    public Bomber(int x, int y, Board board) {
        super(x, y, board);
        _bombs = _board.getBombs();
        _input = _board.getInput();
        _sprite = Sprite.player_right;
    }

    @Override
    public void update() {
        clearBombs();
        if (!_alive) {
            afterKill();
            return;
        }

        if (_timeBetweenPutBombs < -7500) _timeBetweenPutBombs = 0;
        else _timeBetweenPutBombs--;

        animate();

        calculateMove();

        detectPlaceBomb();
    }

    @Override
    public void render(Screen screen) {
        calculateXOffset();

        if (_alive)
            chooseSprite();
        else
            _sprite = Sprite.player_dead1;

        screen.renderEntity((int) _x, (int) _y - _sprite.SIZE, this);
    }

    public void calculateXOffset() {
        int xScroll = Screen.calculateXOffset(_board, this);
        Screen.setOffset(xScroll, 0);
    }

    /**
     * Kiểm tra xem có đặt được bom hay không? nếu có thì đặt bom tại vị trí hiện tại của Bomber
     */
    private void detectPlaceBomb() {
        if(_input.space && _timeBetweenPutBombs < 0 && Game.getBombRate() >= 1) {
            placeBomb(Coordinates.pixelToTile(_x + _sprite.getSize() / 2), Coordinates.pixelToTile(_y - _sprite.getSize() / 2)); // Place bomb based on the center of player
            _timeBetweenPutBombs = 30;
            Game.addBombRate(-1);
        }
    }

    protected void placeBomb(int x, int y) {
        _board.addBomb(new Bomb(x, y, _board));
    }

    private void clearBombs() {
        Iterator<Bomb> bs = _bombs.iterator();

        Bomb b;
        while (bs.hasNext()) {
            b = bs.next();
            if (b.isRemoved()) {
                bs.remove();
                Game.addBombRate(1);
            }
        }

    }

    @Override
    public void kill() {
        if (!_alive) return;
        _alive = false;
    }

    @Override
    protected void afterKill() {
        if (_timeAfter > 0) --_timeAfter;
        else {
            _board.endGame();
        }
    }

    @Override
    protected void calculateMove() {
        double xa = 0, ya = 0; // x velocity, y velocity
        // Update velocity based on keyboard input
        if (_input.left)
            xa -= Game.getBomberSpeed();
        if (_input.right)
            xa += Game.getBomberSpeed();
        if (_input.up)
            ya -= Game.getBomberSpeed();
        if (_input.down)
            ya += Game.getBomberSpeed();
        if (xa != 0 || ya != 0) {
            _moving = true;
            move(xa, ya);
        }
        else
            _moving = false;
    }

    @Override
    public boolean canMove(double x, double y) {
        // Bomber: 16x12 (HxW)
        Entity e1 = _board.getEntity(Coordinates.pixelToTile(x), Coordinates.pixelToTile(y - 16), this);
        Entity e2 = _board.getEntity(Coordinates.pixelToTile(x + 11), Coordinates.pixelToTile(y - 16), this);
        Entity e3 = _board.getEntity(Coordinates.pixelToTile(x), Coordinates.pixelToTile(y - 1), this);
        Entity e4 = _board.getEntity(Coordinates.pixelToTile(x + 11), Coordinates.pixelToTile(y - 1), this);
        if (!e1.collide(this) || !e2.collide(this) || !e3.collide(this) || !e4.collide(this))
            return false;
        return true;
    }

    @Override
    public void move(double xa, double ya) {
        if (canMove(_x, _y + ya)) {
            _y += ya;
        }
        if (canMove(_x + xa, _y)) { // Allow sliding like NES version
            _x += xa;
        }
        // Update direction: 0 - up, 1 - right, 2 - down, 3 - left
        if (xa > 0) // Right
            _direction = 1;
        if (xa < 0) // Left
            _direction = 3;
        if (ya > 0) // Down
            _direction = 2;
        if (ya < 0) // Up
            _direction = 0;
    }

    /**
     * Check collision. Also,
     * @param e Entity
     * @return  false if collide with something
     *          true if not
     */
    @Override
    public boolean collide(Entity e) {
        if (e instanceof Enemy) {
            this.kill();
            return true;
        }
        if (e instanceof Flame) {
            this.kill();
            return false;
        }

        return true;
    }

    private void chooseSprite() {
        switch (_direction) {
            case 0:
                _sprite = Sprite.player_up;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, _animate, 20);
                }
                break;
            case 1:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
            case 2:
                _sprite = Sprite.player_down;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, _animate, 20);
                }
                break;
            case 3:
                _sprite = Sprite.player_left;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, _animate, 20);
                }
                break;
            default:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
        }
    }
}
