package bomberman.entities.bomb;
/**
 * @author Taaan
 */

import bomberman.entities.Entity;
import bomberman.entities.character.Character;
import bomberman.graphics.Screen;
import bomberman.graphics.Sprite;

public class FlameSegment extends Entity {

    protected boolean isLast;

    /**
     * @param direction huong cua ngon lua
     * @param isLast    check xem Segment nay co phai la segment cuoi hay ko
     */
    public FlameSegment(int x, int y, int direction, boolean isLast) {
        coordinateX = x;
        coordinateY = y;
        this.isLast = isLast;

        if (direction == 0) {
            if (!isLast) {
                sprite = Sprite.explosion_vertical2;
            } else {
                sprite = Sprite.explosion_vertical_top_last2;
            }
        }
        if (direction == 1) {
            if (!isLast) {
                sprite = Sprite.explosion_horizontal2;
            } else {
                sprite = Sprite.explosion_horizontal_right_last2;
            }
        }
        if (direction == 2) {
            if (!isLast) {
                sprite = Sprite.explosion_vertical2;
            } else {
                sprite = Sprite.explosion_vertical_down_last2;
            }
        }
        if (direction == 3) {
            if (!isLast) {
                sprite = Sprite.explosion_horizontal2;
            } else {
                sprite = Sprite.explosion_horizontal_left_last2;
            }
        }
    }


    @Override
    public void update() {
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Character) {
            ((Character) e).killed();
        }
        return true;
    }

    @Override
    public void render(Screen screen) {
        screen.renderEntity((int) coordinateX * 16, (int) coordinateY * 16, this);
    }
}