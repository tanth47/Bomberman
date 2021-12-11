package  bomberman.entities.cell.item;

import bomberman.Board;
import bomberman.Game;

import bomberman.SoundPlayer;
import  bomberman.entities.Entity;
import bomberman.entities.character.Bomber;
import  bomberman.graphics.Sprite;

import java.io.File;
/**
 * @author Taaan
 */
public class BombItem extends Item {

	public BombItem(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	@Override
	public boolean collide(Entity e) {
		// xử lý Bomber ăn Item
		if(isRemoved()) return false;
		if(e instanceof Bomber) {
			Game.addBombRate(1);
			remove();
			Board.getItemSound.play();
			return true;
		}
		return false;
	}
	


}
