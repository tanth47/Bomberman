/**
 * @author Kyoraku
 */

package bomberman.entities.character.enemy;

import bomberman.Board;
import bomberman.Game;
import bomberman.entities.character.enemy.ai.AILow;
import bomberman.graphics.Sprite;

public class Balloon extends Enemy {
    /**
     * constructor.
     * @param x int x
     * @param y int y
     * @param board board
     */
    public Balloon(int x, int y, Board board) {
        super(x, y, board, Sprite.balloom_dead, Game.getBomberSpeed() / 2, 100);
        sprite = Sprite.balloom_left1;
        ai = new AILow();
    }

    /**
     * override function to choose sprite.
     */
    @Override
    protected void chooseSprite() {
        switch (direction) {
            case 0:
                sprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, animate, 60);
                break;
            case 1:
                sprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, animate, 60);
                break;
            case 2:
                sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, animate, 60);
                break;
            case 3:
                sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, animate, 60);
                break;
        }
    }
}