package  bomberman.entities;
/**
 * @author Taaan
 * @author Kyoraku
 */
/**
 * Các Entity có hiệu ứng hoạt hình
 */
public abstract class AnimatedEntity extends Entity {

    /**
     * _animate để tính ra xem Entity sẽ dùng Sprite nào để render
     * MAX_ANIMATE tránh tràn số cho _animate.
     */
    protected int animate = 0;
    protected final int MAX_ANIMATE = 60;

    protected void animate() {
        animate++;
        animate %= MAX_ANIMATE;
    }

}
