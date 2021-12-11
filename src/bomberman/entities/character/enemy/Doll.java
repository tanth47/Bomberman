/**
 * @author Kyoraku
 */

package bomberman.entities.character.enemy;

import bomberman.Board;
import bomberman.Game;
import bomberman.entities.character.enemy.ai.AILow;
import bomberman.graphics.Sprite;

public class Doll extends Enemy {
    /**
     * constructor.
     * @param x int x
     * @param y int y
     * @param board board
     */
    public Doll(int x, int y, Board board) {
        super(x, y, board, Sprite.doll_dead, Game.getBomberSpeed(), 400);
        sprite = Sprite.doll_right1;
        ai = new AILow();
    }

    /**
     * override function to choose sprite.
     */
    @Override
    protected void chooseSprite() {
        switch (direction) {
            case 0:
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, animate, 60);
                } else {
                    sprite = Sprite.doll_left1;
                }
                break;
            case 1:
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, animate, 60);
                } else {
                    sprite = Sprite.doll_left1;
                }
                break;
            case 2:
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, animate, 60);
                } else {
                    sprite = Sprite.doll_left1;
                }
                break;
            case 3:
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, animate, 60);
                } else {
                    sprite = Sprite.doll_left1;
                }
                break;
        }
    }
}