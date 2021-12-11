package  bomberman.entities.cell;
/**
 * @author Taaan
 */
import  bomberman.entities.Entity;
import  bomberman.graphics.Screen;
import  bomberman.graphics.Sprite;
import  bomberman.LoadMap.Coordinates;

/**
 * Các Entity không cần hoạt họa
 */
public abstract class Cell extends Entity {

	/**
	 * Constructor.
	 * @param x tọa độ X
	 * @param y tọa độ Y
	 * @param sprite sprite
	 */
	public Cell(int x, int y, Sprite sprite) {
		coordinateX = x;
		coordinateY = y;
		this.sprite = sprite;
	}

	/**
	 * Mặc định không cho bất cứ một đối tượng nào đi qua
	 * @param e check va trạm với Entity e
	 * @return false.
	 */
	@Override
	public boolean collide(Entity e) {
		return false;
	}

	/**
	 * Render Cell này lên Screen.
	 * @param screen screen
	 */
	@Override
	public void render(Screen screen) {
		screen.renderEntity( Coordinates.cellToPixel(coordinateX), Coordinates.cellToPixel(coordinateY), this);
	}

	@Override
	public void update() {}
}
