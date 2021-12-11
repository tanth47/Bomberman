/**
 * @author Kyoraku
 */

package bomberman.entities.character.enemy;

import bomberman.Board;
import bomberman.Game;
import bomberman.entities.character.enemy.ai.AIMedium;
import bomberman.graphics.Sprite;

public class Oneal extends Enemy {
	/**
	 * constructor.
	 * @param x int x
	 * @param y int y
	 * @param board board
	 */
	public Oneal(int x, int y, Board board) {
		super(x, y, board, Sprite.oneal_dead, Game.getBomberSpeed(), 200);
		sprite = Sprite.oneal_left1;
		ai = new AIMedium(board.getBomber(), this, board);
	}

	/**
	 * override function to choose sprite.
	 */
	@Override
	protected void chooseSprite() {
		switch(direction) {
			case 0:
				if (moving) {
					sprite = Sprite.movingSprite(Sprite.oneal_up1, Sprite.oneal_up2, Sprite.oneal_up3, animate, 60);
				} else {
					sprite = Sprite.oneal_up1;
				}
				break;
			case 1:
				if (moving) {
					sprite = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, animate, 60);
				} else {
					sprite = Sprite.oneal_left1;
				}
				break;
			case 2:
				if (moving) {
					sprite = Sprite.movingSprite(Sprite.oneal_down1, Sprite.oneal_down2, Sprite.oneal_down3, animate, 60);
				} else {
					sprite = Sprite.oneal_down1;
				}
				break;
			case 3:
				if (moving) {
					sprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animate, 60);
				} else {
					sprite = Sprite.oneal_left1;
				}
				break;
		}
	}
}