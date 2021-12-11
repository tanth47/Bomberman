package  bomberman.entities.cell.destroyable;
/**
 * @author Taaan
 */
import  bomberman.entities.Entity;
import bomberman.entities.bomb.Flame;
import bomberman.entities.cell.Cell;
import bomberman.entities.character.enemy.Kondoria;
import bomberman.entities.character.enemy.Minvo;
import bomberman.entities.character.enemy.Ovapes;
import  bomberman.entities.cell.Cell;
import  bomberman.graphics.Sprite;

/**
 * Đối tượng cố định có thể bị phá hủy
 */
public class DestroyableCell extends Cell {

	private final int MAX_ANIMATE = 60;
	private int animate = 0;
	protected boolean destroyed = false;
	protected int timeRemain = 20;
	protected Sprite spriteBelow = Sprite.grass;
	
	public DestroyableCell(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	
	@Override
	public void update() {
		if(destroyed) {
			if(animate < MAX_ANIMATE) animate++; else animate = 0;
			if(timeRemain > 0)
				timeRemain--;
			else
				remove();
		}
	}

	public void destroy() {
		destroyed = true;
	}
	
	@Override
	public boolean collide(Entity e) {
		if(e instanceof Flame)
			destroy();
		if(e instanceof Kondoria || e instanceof Ovapes || e instanceof Minvo) return true;
		return false;
	}
	
	public void addBelow(Sprite sprite) {
		spriteBelow = sprite;
	}
	
	protected Sprite movingSprite(Sprite normal, Sprite x1, Sprite x2) {
		int calc = animate % 30;
		
		if(calc < 10) {
			return normal;
		}
			
		if(calc < 20) {
			return x1;
		}
			
		return x2;
	}
	
}
