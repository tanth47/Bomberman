/**
 * @author Kyoraku
 */

package bomberman.entities.character.enemy;

import bomberman.Board;
import bomberman.Game;
import bomberman.entities.character.enemy.ai.AIHigh;
import bomberman.graphics.Sprite;

public class Kondoria extends Enemy {
    /**
     * constructor.
     * @param x int x
     * @param y int y
     * @param board board
     */
    public Kondoria(int x, int y, Board board) {
        super(x, y, board, Sprite.kondoria_dead, Game.getBomberSpeed() / 1.5, 1000);
        sprite = Sprite.kondoria_right1;
        ai = new AIHigh(board.getBomber(), this, board);
    }

    /**
     * override function to choose sprite.
     */
    @Override
    protected void chooseSprite() {
        switch (direction) {
            case 0:
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3, animate, 60);
                } else {
                    sprite = Sprite.kondoria_left1;
                }
                break;
            case 1:
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3, animate, 60);
                } else {
                    sprite = Sprite.kondoria_left1;
                }
                break;
            case 2:
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, animate, 60);
                } else {
                    sprite = Sprite.kondoria_left1;
                }
                break;
            case 3:
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, animate, 60);
                } else {
                    sprite = Sprite.kondoria_left1;
                }
                break;
        }
    }
}