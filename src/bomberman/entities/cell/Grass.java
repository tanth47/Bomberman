package  bomberman.entities.cell;

/**
 * @author Taaan
 */
import  bomberman.entities.Entity;
import  bomberman.graphics.Sprite;

public class Grass extends Cell {

	/**
	 * Constructor.
	 * @param x tọa độ x
	 * @param y tọa độ y
	 * @param sprite sprite
	 */
	public Grass(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	/**
	 * Cho bất kì đối tượng khác đi qua
	 * @param e
	 * @return
	 */
	@Override
	public boolean collide(Entity e) {
		return true;
	}
}
