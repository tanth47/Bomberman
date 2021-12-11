/**
 * @author Kyoraku
 */

package bomberman.entities.character;

import bomberman.graphics.Screen;
import bomberman.entities.AnimatedEntity;
import bomberman.Board;
import bomberman.Game;

/**
 * Bomber và Enemy sẽ ở trong đây.
 */
public abstract class Character extends AnimatedEntity {
    protected Board board;
    protected int direction = -1;
    protected boolean alive = true;
    protected boolean moving = false;
    public int timeAfter = 40;

    /**
     * constructor.
     * @param x coordinate x
     * @param y coordinate y
     * @param board board
     */
    public Character(int x, int y, Board board) {
        coordinateX = x;
        coordinateY = y;
        this.board = board;
    }

    @Override
    public abstract void update();

    @Override
    public abstract void render(Screen screen);

    /**
     * check collision.
     * @param c character
     * @return boolean true or false
     */
    public boolean collisionWith(Character c) {
        if (coordinateX + 13 > c.getX() && coordinateX < c.getX() + 13 && coordinateY + 13 > c.getY() && coordinateY < c.getY() + 13) {
            return true;
        }

        return false;
    }

    /**
     * Tính toán hướng đi.
     */
    protected abstract void calculateMove();

    protected abstract void move(double xa, double ya);

    /**
     * Được gọi khi đối tượng bị tiêu diệt.
     */
    public abstract void killed();

    /**
     * Xử lý hiệu ứng bị tiêu diệt.
     */
    protected abstract void afterKilled();

    /**
     * Kiểm tra xem đối tượng có di chuyển tới vị trí đã tính toán hay không.
     *
     * @param x double x
     * @param y double y
     * @return boolean true or false
     */
    protected abstract boolean canMove(double x, double y);

    protected double getXMessage() {
        return (coordinateX * Game.SCALE) + (sprite.SIZE / 2 * Game.SCALE);
    }

    protected double getYMessage() {
        return (coordinateY * Game.SCALE) - (sprite.SIZE / 2 * Game.SCALE);
    }
}