package bomberman.LoadMap;
/**
 * @author Taaan
 */

import bomberman.Game;

public class Coordinates {

    public static int pixelToCell(double i) {
        return (int) (i / Game.CELLS_SIZE);
    }

    public static int cellToPixel(int i) {
        return i * Game.CELLS_SIZE;
    }

    public static int cellToPixel(double i) {
        return (int) (i * Game.CELLS_SIZE);
    }


}
