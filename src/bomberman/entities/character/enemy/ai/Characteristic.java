/**
 * @author Kyoraku
 */

package bomberman.entities.character.enemy.ai;

public class Characteristic {
    public int coordinateX;
    public int coordinateY;
    public int dis;

    /**
     * constructor.
     * @param coordinateX1 int coordinate x1
     * @param coordinateY1 int coordinate y1
     * @param dis1 int dis1
     */
    public Characteristic(int coordinateX1, int coordinateY1, int dis1) {
        coordinateX = coordinateX1;
        coordinateY = coordinateY1;
        dis = dis1;
    }
}