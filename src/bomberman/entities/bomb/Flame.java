package bomberman.entities.bomb;
/**
 * @author Taaan
 */

import bomberman.Board;
import bomberman.entities.Entity;
import bomberman.entities.character.Character;
import bomberman.graphics.Screen;


public class Flame extends Entity {
    protected Board board;
    protected int direction;
    private int radius;
    protected int xCenter, yCenter;
    public FlameSegment[] flameSegments = new FlameSegment[1];

    /**
     * Constructor.
     * @param x tọa độ X
     * @param y tọa độ Y
     * @param direction hướng của ngọn lửa
     * @param radius bán kính của ngọn lửa
     * @param board map
     */
    public Flame(int x, int y, int direction, int radius, Board board) {
        xCenter = x;
        yCenter = y;
        coordinateX = x;
        coordinateY = y;
        this.direction = direction;
        this.radius = radius;
        this.board = board;
        createSegments();
    }

    /**
     * Tạo ra các Flame Segment.
     */
    private void createSegments() {
        flameSegments = new FlameSegment[FlameLength()];
        boolean isLast = false;

        int x = (int) coordinateX;
        int y = (int) coordinateY;
        for (int i = 0; i < flameSegments.length; i++) {
            if (i == flameSegments.length - 1) {
                isLast = true;
            }
            if (direction == 0) y--;
            if (direction == 1) x++;
            if (direction == 2) y++;
            if (direction == 3) x--;
            flameSegments[i] = new FlameSegment(x, y, direction, isLast);
        }
    }

    @Override
    public void update() {
    }

    /**
     * Vẽ các Flame Segment lên screen.
     * @param screen
     */
    @Override
    public void render(Screen screen) {
        for (FlameSegment tmp : flameSegments) {
            tmp.render(screen);
        }
    }

    /**
     * Kiếm tra va chạm với các Entity khác, nếu có thì kill các Entity khác.
     * @param e Entity khác.
     * @return false nếu va chạm
     */
    @Override
    public boolean collide(Entity e) {
        if (e instanceof Character) {
            ((Character) e).killed();
            return false;
        }
        return true;

    }

    /**
     * Calculate length of the flame.
     *
     * @return length
     */
    public int FlameLength() {
        int radius = 0;
        int x = (int) coordinateX;
        int y = (int) coordinateY;
        while (radius < this.radius) {
            if (direction == 0) y--;
            if (direction == 1) x++;
            if (direction == 2) y++;
            if (direction == 3) x--;
            Entity tmp = board.getEntity(x, y, null);
            if (tmp instanceof Character) {
                for (Character cur : board.characters) {
                    if (cur.getXCell() == x && cur.getYCell() == y) {
                        cur.collide(this);
                    }
                }
                tmp = board.getEntityAt(x, y);
                tmp.collide(this);
                ++radius;
                continue;
            }
            if (!tmp.collide(this)) {
                break;
            }
            ++radius;
        }
        return radius;
    }

    /**
     * Ham lay FlameSegment
     *
     * @param x vi tri tren board
     * @param y vi tri tren board
     * @return flameSegment
     */
    public FlameSegment flameSegmentAt(int x, int y) {
        for (FlameSegment tmp : flameSegments) {
            if (tmp.getX() == x && tmp.getY() == y)
                return tmp;
        }
        return null;
    }
}
