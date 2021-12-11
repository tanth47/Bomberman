package  bomberman.entities.cell.destroyable;
/**
 * @author Taaan
 */

import bomberman.entities.cell.destroyable.DestroyableCell;
import  bomberman.graphics.Screen;
import  bomberman.graphics.Sprite;
import  bomberman.LoadMap.Coordinates;

public class Brick extends DestroyableCell {
	
	public Brick(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	
	@Override
	public void update() {
		super.update();
	}
	
	@Override
	public void render(Screen screen) {
		int x = Coordinates.cellToPixel(coordinateX);
		int y = Coordinates.cellToPixel(coordinateY);
		
		if(destroyed) {
			sprite = movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2);
			screen.renderEntityWithSpriteBelow(x, y, this, spriteBelow);
		}
		else {
			screen.renderEntity(x, y, this);
		}
	}
	
}
