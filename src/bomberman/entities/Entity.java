package  bomberman.entities;
/**
 * @author Taaan
 * @author Kyoraku
 */
import  bomberman.graphics.IRender;
import  bomberman.graphics.Screen;
import  bomberman.graphics.Sprite;
import  bomberman.LoadMap.Coordinates;

/**
 * Class dùng cho tất cả thực thể trong game (Bomber, Enemy, Grass,...).
 */
public abstract class Entity implements IRender {

	/**
	 * tọa độ tính theo pixel.
	 */
	protected double coordinateX, coordinateY;
	protected boolean isRemoved = false;
	protected Sprite sprite;

	/**
	 * Được gọi liên tục trong quá trình game chạy, update các trạng thái của Entity.
	 */
	@Override
	public abstract void update();

	/**
	 * Được gọi liên tục trong quá trình game chạy, draw Sprite cho Entity này.
	 */
	@Override
	public abstract void render(Screen screen);
	
	public void remove() {
		isRemoved = true;
	}
	
	public boolean isRemoved() {
		return isRemoved;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	/**
	 * Xử lý khi Entity này va chạm với Entity khác.
	 */
	
	public abstract boolean collide(Entity e);
	
	public double getX() {
		return coordinateX;
	}
	
	public double getY() {
		return coordinateY;
	}


	public int getXCell() {
		return Coordinates.pixelToCell(coordinateX + sprite.SIZE / 2);
	}

	public int getYCell() {
		return Coordinates.pixelToCell(coordinateY - sprite.SIZE / 2);
	}
}
