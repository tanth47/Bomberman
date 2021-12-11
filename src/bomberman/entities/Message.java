package bomberman.entities;
/**
 * @author Taaan
 * @author Kyoraku
 */

import java.awt.*;

import bomberman.graphics.Screen;

/**
 * In ra các thông tin nhỏ như điểm khi ăn được quái.
 */
public class Message extends Entity {

    protected String text;
    protected int interval;
    protected Color color;
    protected int size;

    /**
     * Constructor.
     *
     * @param text     nội dung, ví dụ: +100đ
     * @param x        vị trí
     * @param y        vị trí
     * @param interval khoảng thời gian
     * @param color    màu
     * @param size     kích cỡ
     */
    public Message(String text, double x, double y, int interval, Color color, int size) {
        coordinateX = x;
        coordinateY = y;
        this.text = text;
        this.interval = interval * 60; //seconds
        this.color = color;
        this.size = size;
    }

    public int getInterval() {
        return interval;
    }

    public String getText() {
        return text;
    }

    public Color getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void update() {
    }

    @Override
    public void render(Screen screen) {
    }

    @Override
    public boolean collide(Entity e) {
        return true;
    }
}
