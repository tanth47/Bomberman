/**
 * @author Kyoraku
 */

package bomberman.entities.character.enemy;

import bomberman.Board;
import bomberman.Game;
import bomberman.entities.character.enemy.ai.AIMedium;
import bomberman.graphics.Sprite;

public class Minvo extends Enemy {
    /**
     * constructor.
     * @param x int x
     * @param y int y
     * @param board board
     */
    public Minvo(int x, int y, Board board) {
        super(x, y, board, Sprite.minvo_dead, Game.getBomberSpeed() * 2, 800);
        sprite = Sprite.minvo_right1;
        ai = new AIMedium(board.getBomber(), this, board);
    }

    /**
     * override function to choose sprite.
     */
    @Override
    protected void chooseSprite() {
        switch (direction) {
            case 0:
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.minvo_right1, Sprite.minvo_right2, Sprite.minvo_right3, animate, 60);
                } else {
                    sprite = Sprite.minvo_left1;
                }
                break;
            case 1:
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.minvo_right1, Sprite.minvo_right2, Sprite.minvo_right3, animate, 60);
                } else {
                    sprite = Sprite.minvo_left1;
                }
                break;
            case 2:
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_left2, Sprite.minvo_left3, animate, 60);
                } else {
                    sprite = Sprite.minvo_left1;
                }
                break;
            case 3:
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_left2, Sprite.minvo_left3, animate, 60);
                } else {
                    sprite = Sprite.minvo_left1;
                }
                break;
        }
    }
}