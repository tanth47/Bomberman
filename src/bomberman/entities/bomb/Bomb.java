package bomberman.entities.bomb;
/**
 * Class Bomb
 *
 * @author Taaan
 */

import bomberman.Board;
import bomberman.Game;
import bomberman.entities.AnimatedEntity;
import bomberman.entities.Entity;
import bomberman.entities.character.Bomber;
import bomberman.entities.character.Character;
import bomberman.graphics.Screen;
import bomberman.graphics.Sprite;
import bomberman.LoadMap.Coordinates;

public class Bomb extends AnimatedEntity {

    /**
     * board: map game
     * timeRemainToExplode
     */
    protected double timeRemainToExplode = 100; //approximate to 2 seconds
    protected boolean exploded = false;
    protected boolean canBePassed = true;
    public int timeExploding = 20;
    protected Board board;
    public Flame[] flames;

    public Bomb(int x, int y, Board board) {
        coordinateX = x;
        coordinateY = y;
        this.board = board;
        sprite = Sprite.bomb;

    }

    /**
     * update trang thai bomb
     */
    @Override
    public void update() {
        if (timeRemainToExplode > 0)
            timeRemainToExplode--;
        else {
            if (!exploded)
                explode();
            else
                updateFlames();

            if (timeExploding > 0)
                timeExploding--;
            else
                remove();
        }
        animate();
    }

    /**
     * Render Bomb to Screne.
     *
     * @param screen screen
     */
    @Override
    public void render(Screen screen) {
        if (exploded) {
            sprite = Sprite.bomb_exploded2;
            renderFlames(screen);
        } else
            sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 30);

        int xt = (int) (coordinateX * 16);
        int yt = (int) (coordinateY * 16);

        screen.renderEntity(xt, yt, this);
    }

    /**
     * Render Flames to Screne.
     *
     * @param screen
     */
    public void renderFlames(Screen screen) {
        for (int i = 0; i < flames.length; i++) {
            flames[i].render(screen);
        }
    }

    /**
     * Update flame
     */
    public void updateFlames() {
        for (int i = 0; i < flames.length; i++) {
            flames[i].update();
        }
    }

    /**
     * Solve Bomb explode.
     */
    protected void explode() {
        exploded = true;
        canBePassed = true;
        Character a = board.getCharacterAt(coordinateX, coordinateY);
        if (a != null) a.killed();
        flames = new Flame[4];
        for (int i = 0; i < flames.length; i++) {
            flames[i] = new Flame((int) coordinateX, (int) coordinateY, i, Game.getBombRadius(), board);
        }
        Board.explosionSound.play();
    }

    public FlameSegment flameAt(int x, int y) {
        if (!exploded) return null;

        for (int i = 0; i < flames.length; i++) {
            if (flames[i] == null) return null;
            FlameSegment e = flames[i].flameSegmentAt(x, y);
            if (e != null) return e;
        }

        return null;
    }

    /**
     * Xu ly va cham
     *
     * @param e entity
     * @return boolean
     */
    @Override
    public boolean collide(Entity e) {
        // xử lý khi Bomber đi ra sau khi vừa đặt bom (canBePassed)
        // xử lý va chạm với Flame của Bomb khác
        if (e instanceof Bomber) {
            double diffX = e.getX() - Coordinates.cellToPixel(getX());
            double diffY = e.getY() - Coordinates.cellToPixel(getY());

            if (!(diffX >= -10 && diffX < 16 && diffY >= 1 && diffY <= 28)) { // differences to see if the player has moved out of the bomb, tested values
                canBePassed = false;
            }

            return canBePassed;
        }

        if (e instanceof FlameSegment || e instanceof Flame) {
            timeRemainToExplode = 0;
            return true;
        }

        return false;
    }
}
