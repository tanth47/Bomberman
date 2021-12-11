/**
 * @author Kyoraku
 */

package bomberman.entities.character.enemy;

import bomberman.Board;
import bomberman.Game;
import bomberman.entities.Entity;
import bomberman.entities.Message;
import bomberman.entities.bomb.Flame;
import bomberman.entities.character.Bomber;
import bomberman.entities.character.Character;
import bomberman.entities.character.enemy.ai.AI;
import bomberman.graphics.Screen;
import bomberman.graphics.Sprite;
import bomberman.LoadMap.Coordinates;
import bomberman.entities.bomb.FlameSegment;
import bomberman.SoundPlayer;

import java.awt.*;
import java.io.File;

public abstract class Enemy extends Character {
    protected int points;
    protected double speed;
    protected AI ai;
    protected final double MAX_STEPS;
    protected final double rest;
    protected double steps;
    protected int finalAnimation = 30;
    protected Sprite deadSprite;
    public static SoundPlayer enemyDieSound = new SoundPlayer(new File("res/sound/enemyDie.wav"));

    /**
     * constructor.
     * @param x int x
     * @param y int y
     * @param board board
     * @param dead sprite
     * @param speed double speed
     * @param points int points
     */
    public Enemy(int x, int y, Board board, Sprite dead, double speed, int points) {
        super(x, y, board);
        this.points = points;
        this.speed = speed;
        MAX_STEPS = Game.CELLS_SIZE / speed;
        rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
        steps = 0;
        timeAfter = 20;
        deadSprite = dead;
    }

    /**
     * override function to upd.
     */
    @Override
    public void update() {
        animate();
        if (!alive) {
            afterKilled();

            return;
        }
        calculateMove();
    }

    /**
     * override function to render.
     * @param screen
     */
    @Override
    public void render(Screen screen) {
        if (alive) {
            chooseSprite();
        } else {
            if (timeAfter > 0) {
                sprite = deadSprite;
                animate = 0;
            } else {
                sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animate, 60);
            }
        }
        screen.renderEntity((int) coordinateX, (int) coordinateY - sprite.SIZE, this);
    }

    /**
     * override function to calculate direction moving.
     */
    @Override
    public void calculateMove() {
        // TODO: Tính toán hướng đi và di chuyển Enemy theo ai và cập nhật giá trị cho direction
        // TODO: sử dụng canMove() để kiểm tra xem có thể di chuyển tới điểm đã tính toán hay không
        // TODO: sử dụng move() để di chuyển
        // TODO: nhớ cập nhật lại giá trị cờ moving khi thay đổi trạng thái di chuyển
        int x1 = 0;
        int y1 = 0;

        if (steps <= 0) {
            direction = ai.calculateDirection();
            steps = MAX_STEPS;
        }

        if (direction == 0) {
            --y1;
        }
        if (direction == 1) {
            ++x1;
        }
        if (direction == 2) {
            ++y1;
        }
        if (direction == 3) {
            --x1;
        }
        if (canMove(x1, y1)) {
            steps += -rest - 1;
            move(speed * x1, speed * y1);
            moving = true;
        } else {
            moving = false;
            steps = 0;
        }
    }

    /**
     * override function to move.
     * @param xa double xa
     * @param ya double ya
     */
    @Override
    public void move(double xa, double ya) {
        if (!alive) {
            return;
        }
        coordinateY += ya;
        coordinateX += xa;
    }

    /**
     * override function to check if character can move to position x,y.
     * @param x double x
     * @param y double y
     * @return boolean true or false
     */
    @Override
    public boolean canMove(double x, double y) {
        // TODO: kiểm tra có đối tượng tại vị trí chuẩn bị di chuyển đến và có thể di chuyển tới đó hay không
        //	double xr = coordinateX, yr = coordinateY - 16;
        int x1 = (int) x;
        int y1 = (int) y;
        if (direction == 0) {
            y1 += Coordinates.pixelToCell(-17 + sprite.getSize() + coordinateY);
            x1 += Coordinates.pixelToCell(sprite.getSize() / 2 + coordinateX);
        } else if (direction == 1) {
            y1 += Coordinates.pixelToCell(-16 + sprite.getSize() / 2 + coordinateY);
            x1 += Coordinates.pixelToCell(1 + coordinateX);
        } else if (direction == 2) {
            y1 += Coordinates.pixelToCell(-15 + coordinateY);
            x1 += Coordinates.pixelToCell(sprite.getSize() / 2 + coordinateX);
        } else if (direction == 3) {
            y1 += Coordinates.pixelToCell(-16 + sprite.getSize() / 2 + coordinateY);
            x1 += Coordinates.pixelToCell(-1 + sprite.getSize() + coordinateX);
        } else {
            x1 += Coordinates.pixelToCell(coordinateX) + (int) x;
            y1 += Coordinates.pixelToCell(-16 + coordinateY) + (int) y;
        }
        Entity a = board.getEntity(x1, y1, this);

        return a.collide(this);
    }

    /**
     * kiem tra va cham.
     * @param e entity
     * @return boolean true or false
     */
    @Override
    public boolean collide(Entity e) {
        // TODO: xử lý va chạm với Flame
        if (e instanceof Flame || e instanceof FlameSegment) {
            killed();

            return false;
        }
        // TODO: xử lý va chạm với Bomber
        if (e instanceof Bomber) {
            if (!collisionWith((Character) e)) {
                return true;
            }
            ((Bomber) e).killed();

            return false;
        }
        return true;
    }

    /**
     * override function to killed.
     */
    @Override
    public void killed() {
        if (!alive) {
            return;
        }
        alive = false;
        board.addPoints(points);
        Message msg = new Message("+" + points, getXMessage(), getYMessage(), 2, Color.white, 14);

        board.addMessage(msg);
        enemyDieSound.play();
    }

    /**
     * override function after killed.
     */
    @Override
    protected void afterKilled() {
        if (timeAfter > 0) {
            --timeAfter;
        } else {
            if (finalAnimation > 0) {
                --finalAnimation;
            } else {
                remove();
            }
        }
    }

    protected abstract void chooseSprite();
}