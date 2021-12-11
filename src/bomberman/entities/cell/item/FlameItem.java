package  bomberman.entities.cell.item;
/**
 * @author Taaan
 */
import bomberman.Board;
import bomberman.Game;
import bomberman.SoundPlayer;
import  bomberman.entities.Entity;
import bomberman.entities.character.Bomber;
import  bomberman.graphics.Sprite;

import java.io.File;

public class FlameItem extends Item {

	public FlameItem(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	@Override
	public boolean collide(Entity e) {
		if(isRemoved()) return false;
		
		if(e instanceof Bomber) {
			Game.addBombRadius(1);
			remove();
			Board.getItemSound.play();
			return true;
		}
		return false;
	}

}
