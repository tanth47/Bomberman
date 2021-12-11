/**
 * @author Kyoraku
 */

package bomberman.entities.character.enemy.ai;

import bomberman.entities.character.Bomber;
import bomberman.entities.character.enemy.Enemy;
import javafx.util.Pair;

import java.util.LinkedList;

import bomberman.Board;

public class AIMedium extends AI {
    Bomber bomber;
    Enemy e;
    Board board;
    public static final int inf = 99999999;
    public static final int[] diX = new int[4];
    public static final int[] diY = new int[4];

    /**
     * constructor.
     * @param bomber bomber
     * @param e enemy
     * @param board board
     */
    public AIMedium(Bomber bomber, Enemy e, Board board) {
        this.bomber = bomber;
        this.e = e;
        this.board = board;
        diX[0] = 0;
        diY[0] = -1;
        diX[1] = 1;
        diY[1] = 0;
        diX[2] = 0;
        diY[2] = 1;
        diX[3] = -1;
        diY[3] = 0;
    }

    /**
     * override function to calculate direction.
     * @return int direction
     */
    @Override
    public int calculateDirection() {
        // TODO: cài đặt thuật toán tìm đường đi
        Pair<Integer, Integer> temp = shortestPath(bomber.getXCell(), bomber.getYCell());
        if (temp.getKey() > 6) {
            return -1;
        }

        return temp.getValue();
    }

    /**
     * function to check invalid position.
     * @param vtX int pos x
     * @param vtY int pos y
     * @return boolean true or false
     */
    public boolean invalidPosition(int vtX, int vtY) {
        if (vtX < 0 || vtY < 0 || vtX >= board.getWidth() || vtY >= board.getHeight()) {
            return false;
        }
        if (!(board.getEntityAt(vtX, vtY).collide(e))) {
            return false;
        }

        return true;
    }

    /**
     * function using bfs algorithm to find shortest path to bomber.
     * @param sX start x
     * @param sY start y
     * @param eX end x
     * @param eY end y
     * @return int distance
     */
    public int bfs(int sX, int sY, int eX, int eY) {
        if (sX == eX && sY == eY) {
            return 0;
        }
        int m = board.getHeight();
        int n = board.getWidth();
        boolean ok[][] = new boolean[n][m];
        LinkedList<Characteristic> q = new LinkedList<>();

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                ok[i][j] = false;
            }
        }
        ok[sX][sY] = true;
        q.add(new Characteristic(sX, sY, 0));
        while (!q.isEmpty()) {
            Characteristic top = q.getFirst();

            q.remove(0);
            for (int i = 0; i < 4; ++i) {
                int ix = diX[i] + top.coordinateX;
                int iy = diY[i] + top.coordinateY;

                if (invalidPosition(ix, iy) && !ok[ix][iy]) {
                    if (ix == eX && iy == eY) {
                        return 1 + top.dis;
                    }
                    q.add(new Characteristic(ix, iy, 1 + top.dis));
                    ok[ix][iy] = true;
                }
            }
        }

        return inf;
    }

    /**
     * function to find all direction and prepare to find shortest path.
     * @param dichX end x
     * @param dichY end y
     * @return pair
     */
    public Pair<Integer, Integer> shortestPath(int dichX, int dichY) {
        int dir = -1;
        int res = inf;

        for (int i = 0; i < 4; ++i) {
            int dirX = diX[i] + e.getXCell();
            int dirY = diY[i] + e.getYCell();

            if (invalidPosition(dirX, dirY)) {
                int temp = bfs(dirX, dirY, dichX, dichY);

                if (temp != inf && temp < res) {
                    res = temp;
                    dir = i;
                }
            }
        }

        return new Pair(res, dir);
    }
}