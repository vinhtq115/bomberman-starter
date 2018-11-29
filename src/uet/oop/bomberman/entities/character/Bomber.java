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
import uet.oop.bomberman.sounds.Sound;

import java.util.ArrayList;
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
            placeBomb(Coordinates.pixelToTile(_x + _sprite.getSize() / 2), Coordinates.pixelToTile(_y + _sprite.getSize() / 2 - Game.TILES_SIZE)); // Place bomb based on the center of player
            _timeBetweenPutBombs = 30;
            Game.addBombRate(-1);
        }
    }

    protected void placeBomb(int x, int y) {
        Sound.getInstance().playPlant();
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
        // Stop any remaining loop sounds
        Sound.getInstance().stopX();
        Sound.getInstance().stopY();

        if (!_alive) return;

        Sound.getInstance().playDie(); // PLay bomber die sound

        _alive = false;
        // Reduce lives by 1
        _board.addLives(-1);
    }

    @Override
    protected void afterKill() {
        if (_timeAfter > 0) --_timeAfter;
        else {
            // Reset bomb before restarting level
            Game.addBombRate(_bombs.size());
            _bombs = new ArrayList<>();
            if (_board.getLives() == 0)
                _board.endGame();
            else
                _board.loadLevel(_board._level);
        }
    }

    public boolean isAlive() {
        return _alive;
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
            if (_input.left || _input.right) {
                Sound.getInstance().stopY();
                Sound.getInstance().playX();
            }
            else if (_input.up || _input.down) {
                Sound.getInstance().stopX();
                Sound.getInstance().playY();
            }
        }
        else {
            _moving = false;
            Sound.getInstance().stopX();
            Sound.getInstance().stopY();
        }
    }

    @Override
    public boolean canMove(double xa, double ya) {
        // Bomber: 16x12 (HxW)
        Entity[] e = new Entity[4];
        e[0] = _board.getEntity(Coordinates.pixelToTile(_x + xa), Coordinates.pixelToTile(_y + ya) - 1, this); // e[0]: upper left corner
        e[1] = _board.getEntity(Coordinates.pixelToTile(_x + xa + _sprite.getRealWidth()), Coordinates.pixelToTile(_y + ya) - 1, this); // e[1]: upper right corner
        e[2] = _board.getEntity(Coordinates.pixelToTile(_x + xa), Coordinates.pixelToTile(_y + ya - 1), this); // e[2]: lower left corner
        e[3] = _board.getEntity(Coordinates.pixelToTile(_x + xa + _sprite.getRealWidth()), Coordinates.pixelToTile(_y + ya - 1), this); // e[3]: lower right corner
        // Move to center if only 1 corner is blocked
        if (ya < 0) { // Up
            if (!e[0].collide(this) && !e[1].collide(this)) // Both corner is blocked
                return false;
            else if (!e[0].collide(this) && e[1].collide(this)) { // Only upper left is blocked
                if (Coordinates.tileToPixel(e[0].getX()) + Game.TILES_SIZE - _x <= _sprite.getRealWidth() / 2 + 2)
                    move(1, 0);
                else
                    return false;
            }
            else if (e[0].collide(this) && !e[1].collide(this)) { // Only upper right is blocked
                if (_x + _sprite.getRealWidth() - Coordinates.tileToPixel(e[1].getX()) <= _sprite.getRealWidth() / 2 + 2)
                    move(-1, 0);
                else
                    return false;
            }
        }
        else if (ya > 0) { // Down
            if (!e[2].collide(this) && !e[3].collide(this)) // Both corner is blocked
                return false;
            else if (!e[2].collide(this) && e[3].collide(this)) { // Only lower left is blocked
                if (Coordinates.tileToPixel(e[2].getX()) + Game.TILES_SIZE - _x <= _sprite.getRealWidth() / 2 + 2)
                    move(1, 0);
                else
                    return false;
            }
            else if (e[2].collide(this) && !e[3].collide(this)) { // Only lower right is blocked
                if (_x + _sprite.getRealWidth() - Coordinates.tileToPixel(e[3].getX()) <= _sprite.getRealWidth() / 2 + 2)
                    move(-1, 0);
                else
                    return false;
            }
        }
        else if (xa > 0) { // Right
            if (!e[1].collide(this) && !e[3].collide(this))
                return false;
            else if (!e[1].collide(this) && e[3].collide(this)) { // Only upper right is blocked
                if (Coordinates.tileToPixel(e[1].getY() + 1) + Game.TILES_SIZE - _y <= _sprite.getRealHeight() / 2 + 2)
                    move(0, 1);
                else
                    return false;
            }
            else if (e[1].collide(this) && !e[3].collide(this)) { // Only lower right is blocked
                if (_y + _sprite.getRealHeight() - Coordinates.tileToPixel(e[3].getY() + 1) <= _sprite.getRealHeight() / 2 + 2)
                    move(0, -1);
                else
                    return false;
            }
        }
        else { // Left
            if (!e[0].collide(this) && !e[2].collide(this))
                return false;
            else if (!e[0].collide(this) && e[2].collide(this)) { // Only upper left is blocked
                if (Coordinates.tileToPixel(e[0].getY() + 1) + Game.TILES_SIZE - _y <= _sprite.getRealHeight() / 2 + 2)
                    move(0, 1);
                else
                    return false;
            }
            else if (e[0].collide(this) && !e[2].collide(this)) { // Only lower left is blocked
                if (_y + _sprite.getRealHeight() - Coordinates.tileToPixel(e[2].getY() + 1) <= _sprite.getRealHeight() / 2 + 2)
                    move(0, -1);
                else
                    return false;
            }
        }

        return true;
    }

    @Override
    public void move(double xa, double ya) {
        if (canMove(0, ya)) {
            _y += ya;
        }
        if (canMove(xa, 0)) { // Allow sliding like NES version
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
        if (e instanceof Enemy || e instanceof Flame) {
            this.kill();
            return true;
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
