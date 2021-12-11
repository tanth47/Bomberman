/**
 * @author Kyoraku
 */

package bomberman.entities.character.enemy;

import bomberman.Board;
import bomberman.Game;
import bomberman.entities.character.enemy.ai.AILow;
import bomberman.graphics.Sprite;

public class Ovapes extends Enemy {
    /**
     * constructor.
     * @param x int x
     * @param y int y
     * @param board board
     */
    public Ovapes(int x, int y, Board board) {
        super(x, y, board, Sprite.balloom_dead, Game.getBomberSpeed(), 200);
        sprite = Sprite.ovapes_left1;
        ai = new AILow();
    }

    /**
     * override function to choose sprite.
     */
    @Override
    protected void chooseSprite() {
        switch(direction) {
            case 0:
                sprite = Sprite.movingSprite(Sprite.ovapes_right1, Sprite.ovapes_right2, Sprite.ovapes_right3, animate, 60);
                break;
            case 1:
                sprite = Sprite.movingSprite(Sprite.ovapes_right1, Sprite.ovapes_right2, Sprite.ovapes_right3, animate, 60);
                break;
            case 2:
                sprite = Sprite.movingSprite(Sprite.ovapes_left1, Sprite.ovapes_left2, Sprite.ovapes_left3, animate, 60);
                break;
            case 3:
                sprite = Sprite.movingSprite(Sprite.ovapes_left1, Sprite.ovapes_left2, Sprite.ovapes_left3, animate, 60);
                break;
        }
    }
}