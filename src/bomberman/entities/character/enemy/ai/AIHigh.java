/**
 * @author Kyoraku
 */

package bomberman.entities.character.enemy.ai;

import bomberman.entities.bomb.Bomb;
import bomberman.entities.character.Bomber;
import bomberman.entities.character.enemy.Enemy;
import bomberman.entities.cell.destroyable.Brick;
import bomberman.entities.cell.Wall;
import bomberman.Board;
import bomberman.Game;

import java.util.LinkedList;

import javafx.util.Pair;

public class AIHigh extends AI {
    Bomber bomber;
    Enemy e;
    Board board;
    public static final int inf = 99999999;
    public static final int[] diX = new int[4];
    public static final int[] diY = new int[4];
    public boolean ok[][];

    /**
     * constructor.
     * @param bomber bomber
     * @param e enemy
     * @param board board
     */
    public AIHigh(Bomber bomber, Enemy e, Board board) {
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
        ok = new boolean[this.board.getWidth()][this.board.getHeight()];
    }

    /**
     * override function to calculate direction moving.
     * @return int direction
     */
    @Override
    public int calculateDirection() {
        Pair<Integer, Integer> temp = shortestPath(bomber.getXCell(), bomber.getYCell());

        if (temp.getKey() == inf) {
            return -1;
        }

        return temp.getValue();

    }

    /**
     * function to calculate explosion distance.
     * @param posX int pos x
     * @param posY int pos y
     * @param dir int direction
     * @return int distance
     */
    public int explosionDistance(int posX, int posY, int dir) {
        int rad = 0;

        while (Game.getBombRadius() > rad) {
            if (dir == 3) {
                --posX;
            } else if (dir == 1) {
                ++posX;
            } else if (dir == 0) {
                --posY;
            } else if (dir == 2) {
                ++posY;
            }
            if (board.getEntityAt(posX, posY) instanceof Wall) {
                break;
            }
            ++rad;
            if (board.getEntityAt(posX, posY) instanceof Brick) {
                break;
            }
        }

        return rad;
    }

    /**
     * function to check invalid position.
     * @param vtX pos X
     * @param vtY pos Y
     * @return boolean true or false
     */
    public boolean invalidPosition(int vtX, int vtY) {
        if (vtX < 0 || vtY < 0 || vtX >= board.getWidth() || vtY >= board.getHeight()) {
            return false;
        }
        if (!(board.getEntityAt(vtX, vtY).collide(e))) {
            return false;
        }
        for (Bomb i : board.getBombs()) {
            if (i.getX() == vtX && i.getY() == vtY) {
                return false;
            }
        }

        return true;
    }

    /**
     * function using bfs algorithm to find shortest path.
     * @param sX start X
     * @param sY start Y
     * @param eX end X
     * @param eY end Y
     * @return int distance
     */
    public int bfs(int sX, int sY, int eX, int eY) {
        if (sX == eX && sY == eY) {
            return 0;
        }
        LinkedList<Characteristic> q = new LinkedList<>();

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
     * function using bfs algorithm to calculate shortest path to out of flame bomb.
     * @param sX start X
     * @param sY start Y
     * @return int distance
     */
    public int bfsEscapeBomb(int sX, int sY) {
        LinkedList<Characteristic> q = new LinkedList<>();
        boolean[][] ch = new boolean[board.getWidth()][board.getHeight()];

        for (int i = 0; i < board.getWidth(); ++i) {
            for (int j = 0; j < board.getHeight(); ++j) {
                ch[i][j] = false;
            }
        }
        ch[sX][sY] = true;
        q.add(new Characteristic(sX, sY, 0));
        while (!q.isEmpty()) {
            Characteristic top = q.getFirst();

            q.remove(0);
            for (int i = 0; i < 4; ++i) {
                int ix = diX[i] + top.coordinateX;
                int iy = diY[i] + top.coordinateY;

                if (invalidPosition(ix, iy) && !ch[ix][iy]) {
                    if (!ok[ix][iy]) {
                        return 1 + top.dis;
                    }
                    q.add(new Characteristic(ix, iy, 1 + top.dis));
                    ch[ix][iy] = true;
                }
            }
        }

        return inf;
    }

    /**
     * function to get all direction and find shortes path,.
     * @param dichX int end x
     * @param dichY int end y
     * @return pair<distance, direction> </distance,>
     */
    public Pair<Integer, Integer> shortestPath(int dichX, int dichY) {
        int dir = -1;
        int res = inf;

        for (int d = 0; d < 4; ++d) {
            int ix = diX[d] + e.getXCell();
            int iy = diY[d] + e.getYCell();
            int temp = inf;

            if (invalidPosition(ix, iy)) {
                for (int i = 0; i < board.getWidth(); ++i) {
                    for (int j = 0; j < board.getHeight(); ++j) {
                        ok[i][j] = false;
                    }
                }
                for (Bomb i : board.getBombs()) {
                    int bombX = (int) i.getX();
                    int bombY = (int) i.getY();
                    int flame = explosionDistance(bombX, bombY, 0);

                    for (int j = bombY; j >= bombY - flame; --j) {
                        ok[bombX][j] = true;
                    }
                    flame = explosionDistance(bombX, bombY, 1);
                    for (int j = bombX; j <= flame + bombX; ++j) {
                        ok[j][bombY] = true;
                    }
                    flame = explosionDistance(bombX, bombY, 2);
                    for (int j = bombY; j <= flame + bombY; ++j) {
                        ok[bombX][j] = true;
                    }
                    flame = explosionDistance(bombX, bombY, 3);
                    for (int j = bombX; j >= bombX - flame; --j) {
                        ok[j][bombY] = true;
                    }
                }
                if (ok[e.getXCell()][e.getYCell()]) {
                    temp = bfsEscapeBomb(ix, iy);
                } else if (!ok[ix][iy]) {
                    temp = bfs(ix, iy, dichX, dichY);
                }
                if (temp != inf && temp < res) {
                    res = temp;
                    dir = d;
                }
            }
        }

        return new Pair(res, dir);
    }
}