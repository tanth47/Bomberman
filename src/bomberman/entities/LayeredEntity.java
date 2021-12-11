package bomberman.entities;
/**
 * @author Taaan
 * @author Kyoraku
 */

import bomberman.graphics.Screen;
import bomberman.entities.cell.destroyable.DestroyableCell;

import java.util.LinkedList;

/**
 * Quản lý các Entity nằm cùng một vị trí
 */
public class LayeredEntity extends Entity {

    protected LinkedList<Entity> _entities = new LinkedList<>();

    public LayeredEntity(int x, int y, Entity... entities) {
        coordinateX = x;
        coordinateY = y;
        for (int i = 0; i < entities.length; i++) {
            _entities.add(entities[i]);
            if (i > 1) {
                if (entities[i] instanceof DestroyableCell)
                    ((DestroyableCell) entities[i]).addBelow(entities[i - 1].getSprite());
            }
        }
    }

    /**
     * update vị tri này.
     */
    @Override
    public void update() {
        clearRemoved();
        getTopEntity().update();
    }

    /**
     * Khi vẽ, ta chỉ vẽ entity ở trên cùng.
     */
    @Override
    public void render(Screen screen) {
        getTopEntity().render(screen);
    }

    /**
     * Lấy ra Entity nằm trên cùng.
     */
    public Entity getTopEntity() {
        return _entities.getLast();
    }

    /**
     * Nếu Entity trên cùng đã bị remove thì xóa nó đi.
     */
    private void clearRemoved() {
        Entity top = getTopEntity();
        if (top.isRemoved()) {
            _entities.removeLast();
        }
    }


    /**
     * Kiểm tra xem ô này có va chạm với các Entity khác.
     * @param e another Entity
     * @return false nếu va chạm, ngược lại return true
     */
    @Override
    public boolean collide(Entity e) {
        return getTopEntity().collide(e);
    }

}
