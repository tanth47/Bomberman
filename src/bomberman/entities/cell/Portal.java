package bomberman.entities.cell;
/**
 * @author Taaan
 */

import bomberman.Board;
import bomberman.entities.Entity;
import bomberman.graphics.Sprite;
import bomberman.entities.character.Bomber;

public class Portal extends Cell {
    protected Board board;

    /**
     * Constructor.
     * @param x tọa độ x
     * @param y tọa độ y
     * @param sprite sprite
     * @param board map
     */
    public Portal(int x, int y, Sprite sprite, Board board) {
        super(x, y, sprite);
        this.board = board;
    }

    /**
     * Xử lý va chạm với Entity khác.
     * @param e check va trạm với Entity e
     * @return false nếu van chạm
     */
    @Override
    public boolean collide(Entity e) {
        // xử lý khi Bomber đi vào
        if (e instanceof Bomber) {
            if (!board.detectNoEnemies())
                return false;

            if (e.getXCell() == getX() && e.getYCell() == getY()) {
                if (board.detectNoEnemies())
                    board.nextLevel();
            }

            return true;
        }
        return false;
    }

}
