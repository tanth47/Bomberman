package  bomberman.entities.cell;
/**
 * @author Taaan
 */

import  bomberman.graphics.Sprite;

public class Wall extends Cell {

	/**
	 * Constructor.
	 * @param x tọa độ X
	 * @param y tọa độ Y
	 * @param sprite Sprite.
	 */
	public Wall(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

}
