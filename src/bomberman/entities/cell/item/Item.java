package  bomberman.entities.cell.item;
/**
 * Class Item
 * @author Taaan
 */

import bomberman.SoundPlayer;
import  bomberman.entities.cell.Cell;
import  bomberman.graphics.Sprite;

import java.io.File;

public abstract class Item extends Cell {
	
	public Item(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
}
