/**
 * @author Kyoraku
 */

package bomberman.entities.character;

import bomberman.Board;
import bomberman.Game;
import bomberman.entities.Entity;
import bomberman.entities.bomb.Bomb;
import bomberman.graphics.Screen;
import bomberman.graphics.Sprite;
import bomberman.input.Keyboard;
import bomberman.LoadMap.Coordinates;
import bomberman.entities.bomb.Flame;
import bomberman.entities.bomb.FlameSegment;
import bomberman.entities.character.enemy.Enemy;

import java.util.Iterator;
import java.util.List;

public class Bomber extends Character {
    private List<Bomb> bombs;
    protected Keyboard input;

    /**
     * nếu giá trị này < 0 thì cho phép đặt đối tượng Bomb tiếp theo,
     * cứ mỗi lần đặt 1 Bomb mới, giá trị này sẽ được reset về 0 và giảm dần trong mỗi lần update().
     */
    protected int timeBetweenPutBombs = 0;

    /**
     * constructor.
     *
     * @param x     int x
     * @param y     int y
     * @param board board
     */
    public Bomber(int x, int y, Board board) {
        super(x, y, board);
        bombs = board.getBombs();
        input = board.getInput();
        sprite = Sprite.player_right;
    }

    /**
     * override function to update game.
     */
    @Override
    public void update() {
        clearBombs();
        if (!alive) {
            afterKilled();

            return;
        }
        if (timeBetweenPutBombs < -3000) {
            timeBetweenPutBombs = 0;
        } else {
            --timeBetweenPutBombs;
        }
        animate();
        calculateMove();
        detectPlaceBomb();
    }

    /**
     * override function to render screen.
     *
     * @param screen
     */
    @Override
    public void render(Screen screen) {
        calculateXOffset();
        if (alive) {
            chooseSprite();
        } else {
            sprite = Sprite.player_dead1;
        }
        screen.renderEntity((int) coordinateX, (int) coordinateY - sprite.SIZE, this);
    }

    /**
     * function to chia man hinh.
     */
    public void calculateXOffset() {
        int xScroll = Screen.calculateXOffset(board, this);

        Screen.setOffset(xScroll, 0);
    }

    /**
     * Kiểm tra xem có đặt được bom hay không? nếu có thì đặt bom tại vị trí hiện tại của Bomber.
     */
    private void detectPlaceBomb() {
        if (input.space && timeBetweenPutBombs < 0 && Game.getBombRate() > 0) {
            int x1 = Coordinates.pixelToCell(sprite.getSize() / 2 + coordinateX);
            int y1 = Coordinates.pixelToCell(coordinateY - sprite.getSize() / 1.5);

            placeBomb(x1, y1);
            Game.addBombRate(-1);
            timeBetweenPutBombs = 30;
            Board.placeBombSound.play();
        }
    }

    /**
     * dat bom vao vi tri x, y.
     *
     * @param x int x
     * @param y int y
     */
    protected void placeBomb(int x, int y) {
        // TODO: thực hiện tạo đối tượng bom, đặt vào vị trí (x, y)
        Bomb b = new Bomb(x, y, board);

        board.addBomb(b);
    }

    /**
     * function to clear bombs.
     */
    private void clearBombs() {
        Iterator<Bomb> bs = bombs.iterator();

        Bomb b;
        while (bs.hasNext()) {
            b = bs.next();
            if (b.isRemoved()) {
                bs.remove();
                Game.addBombRate(1);
            }
        }
    }

    /**
     * function to kill character.
     */
    @Override
    public void killed() {
        if (!alive) {
            return;
        }
        alive = false;
        Board.bomberDieSound.play();
    }

    /**
     * function update after killing.
     */
    @Override
    protected void afterKilled() {
        if (timeAfter > 0) {
            --timeAfter;
        } else {
            board.restartLevel();
        }
    }

    /**
     * tinh toan huong di.
     */
    @Override
    protected void calculateMove() {
        // TODO: xử lý nhận tín hiệu điều khiển hướng đi từ input và gọi move() để thực hiện di chuyển
        int x1 = 0, y1 = 0;

        if (input.left) {
            --x1;
        }
        if (input.right) {
            ++x1;
        }
        if (input.down) {
            ++y1;
        }
        if (input.up) {
            --y1;
        }
        // TODO: nhớ cập nhật lại giá trị cờ moving khi thay đổi trạng thái di chuyển
        if (x1 != 0 || y1 != 0) {
            move(x1 * Game.getBomberSpeed(), y1 * Game.getBomberSpeed());
            moving = true;
        } else {
            moving = false;
        }
    }

    /**
     * kiem tra xem co the di chuyen den vi tri x,y hay khong.
     *
     * @param x double x
     * @param y double y
     * @return boolean true or false
     */
    @Override
    public boolean canMove(double x, double y) {
        // TODO: kiểm tra có đối tượng tại vị trí chuẩn bị di chuyển đến và có thể di chuyển tới đó hay không
        for (int c = 0; c < 4; ++c) {
            // phát hiện va chạm của người chơi
            double x1 = ((coordinateX + x) + c % 2 * 11) / Game.CELLS_SIZE; // chia cho kích thước ô để chuyển đến tọa độ ô
            double y1 = ((coordinateY + y) + c / 2 * 12 - 13) / Game.CELLS_SIZE; // tính toán tốt nhất dựa vào thực tiễn test thử
            Entity a = board.getEntity(x1, y1, this);

            if (a != null) {
                if (!a.collide(this)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * function to move to x, y position.
     *
     * @param xa double xa
     * @param ya double ya
     */
    @Override
    public void move(double xa, double ya) {
        // TODO: sử dụng canMove() để kiểm tra xem có thể di chuyển tới điểm đã tính toán hay không và thực hiện thay đổi tọa độ coordinateX, coordinateY
        // TODO: nhớ cập nhật giá trị direction sau khi di chuyển
        if (xa < 0) {
            direction = 3;
        }
        if (xa > 0) {
            direction = 1;
        }
        if (ya < 0) {
            direction = 0;
        }
        if (ya > 0) {
            direction = 2;
        }
        // tách các bước di chuyển để người chơi có thể bị trượt khi va chạm
        if (canMove(0, ya)) {
            coordinateY += ya;
        }
        if (canMove(xa, 0)) {
            coordinateX += xa;
        }
    }

    /**
     * xu ly va cham.
     *
     * @param e Entity
     * @return true or false
     */
    @Override
    public boolean collide(Entity e) {
        // xử lý va chạm với Flame
        if (e instanceof FlameSegment || e instanceof Flame) {
            killed();

            return false;
        }
        // xử lý va chạm với Enemy
        if (e instanceof Enemy) {
            if (collisionWith((Character) e)) {
                killed();
            }
            return true;
        }
        return true;
    }

    /**
     * chon khung hinh phu hop cho hoat anh.
     */
    private void chooseSprite() {
        switch (direction) {
            case 0:
                sprite = Sprite.player_up;
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, animate, 20);
                }
                break;
            case 1:
                sprite = Sprite.player_right;
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animate, 20);
                }
                break;
            case 2:
                sprite = Sprite.player_down;
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, animate, 20);
                }
                break;
            case 3:
                sprite = Sprite.player_left;
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, animate, 20);
                }
                break;
            default:
                sprite = Sprite.player_right;
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animate, 20);
                }
                break;
        }
    }
}