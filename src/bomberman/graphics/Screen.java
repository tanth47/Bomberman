package  bomberman.graphics;
/**
 * Class Screen
 * @author Taaan
 */
import  bomberman.Board;
import  bomberman.Game;
import  bomberman.entities.Entity;
import  bomberman.entities.character.Bomber;

import javax.swing.plaf.IconUIResource;
import java.awt.*;

/**
 * Xử lý render cho tất cả Entity và một số màn hình phụ ra Game Panel
 */
public class Screen {
	protected int width, height;
	public int[] pixels;
	private int transparentColor = 0xffff00ff;
	
	public static int xOffset = 0, yOffset = 0;

	/**
	 * Constructor.
	 * @param width width
	 * @param height height
	 */
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		
	}

	/**
	 * clear Screen.
	 */
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	/**
	 * render Entity.
	 * @param xp xp
	 * @param yp yp
	 * @param entity entity
	 */
	public void renderEntity(int xp, int yp, Entity entity) {
		xp -= xOffset;
		yp -= yOffset;
		if(entity.getSprite() == null) {
			return;
		}
		for (int y = 0; y < entity.getSprite().getSize(); y++) {
			int ya = y + yp; //add offset
			for (int x = 0; x < entity.getSprite().getSize(); x++) {
				int xa = x + xp; //add offset
				if(xa < -entity.getSprite().getSize() || xa >= width || ya < 0 || ya >= height) break;
				if(xa < 0) xa = 0; //start at 0 from left
				int color = entity.getSprite().getPixel(x + y * entity.getSprite().getSize());
				if(color != transparentColor) pixels[xa + ya * width] = color;
			}
		}
	}

	/**
	 * render Entity With Sprite Below.
	 * @param xp tọa độ x
	 * @param yp tọa độ y
	 * @param entity Entity
	 * @param below Entity below
	 */
	public void renderEntityWithSpriteBelow(int xp, int yp, Entity entity, Sprite below) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < entity.getSprite().getSize(); y++) {
			int ya = y + yp;
			for (int x = 0; x < entity.getSprite().getSize(); x++) {
				int xa = x + xp;
				if(xa < -entity.getSprite().getSize() || xa >= width || ya < 0 || ya >= height) break; //fix black margins
				if(xa < 0) xa = 0;
				int color = entity.getSprite().getPixel(x + y * entity.getSprite().getSize());
				if(color != transparentColor)
					pixels[xa + ya * width] = color;
				else
					pixels[xa + ya * width] = below.getPixel(x + y * below.getSize());
			}
		}
	}

	/**
	 * set Left Upper.
	 * @param xO Left
	 * @param yO Upper
	 */
	public static void setOffset(int xO, int yO) {
		xOffset = xO;
		yOffset = yO;
	}

	/**
	 * calculate X Offset.
	 * @param board board
	 * @param bomber bomber
	 * @return X offset
	 */
	public static int calculateXOffset(Board board, Bomber bomber) {
		if(bomber == null) return 0;
		int temp = xOffset;
		
		double BomberX = bomber.getX() / 16;
		double complement = 0.5;
		int firstBreakpoint = board.getWidth() / 4;
		int lastBreakpoint = board.getWidth() - firstBreakpoint;
		
		if( BomberX > firstBreakpoint + complement && BomberX < lastBreakpoint - complement) {
			temp = (int)bomber.getX()  - (Game.WIDTH / 2);
		}
		
		return temp;
	}

	/**
	 * Draw Screen Game over.
	 * @param g graphic g
	 * @param points Points to show
	 */
	public void drawGameOver(Graphics g, int points) {
		g.setColor(Color.black);
		g.fillRect(0, 0, getRealWidth(), getRealHeight());
		
		Font font = new Font("Arial", Font.PLAIN, 20 * Game.SCALE);
		g.setFont(font);
		g.setColor(Color.white);
		drawCenteredString("GAME OVER :((", getRealWidth(), getRealHeight() ,-(Game.CELLS_SIZE*2), g);
		font = new Font("Arial", Font.PLAIN, 10 * Game.SCALE);
		g.setFont(font);
		g.setColor(Color.yellow);
		drawCenteredString("POINTS: " + points, getRealWidth(), getRealHeight() + (Game.CELLS_SIZE * 2) * Game.SCALE,0, g);
		
		font = new Font("Arial", Font.PLAIN, 5 * Game.SCALE);
		g.setFont(font);
		g.setColor(Color.white);
		drawCenteredString("[Press space to replay]", getRealWidth(), getRealHeight() + (Game.CELLS_SIZE * 4) * Game.SCALE,0, g);
		
	}

	/**
	 * Draw Victory Screen.
	 * @param g Graphic g
	 * @param points points to show
	 */
	public void drawVictory(Graphics g, int points) {
		g.setColor(Color.black);
		g.fillRect(0, 0, getRealWidth(), getRealHeight());
		
		Font font = new Font("Arial", Font.PLAIN, 20 * Game.SCALE);
		g.setFont(font);
		g.setColor(Color.white);
		drawCenteredString("VICTORY !!!", getRealWidth(), getRealHeight() ,-(Game.CELLS_SIZE*2), g);
		font = new Font("Arial", Font.PLAIN, 10 * Game.SCALE);
		g.setFont(font);
		g.setColor(Color.yellow);
		drawCenteredString("POINTS: " + points, getRealWidth(), getRealHeight() + (Game.CELLS_SIZE * 2) * Game.SCALE,0, g);
		
		font = new Font("Arial", Font.PLAIN, 5 * Game.SCALE);
		g.setFont(font);
		g.setColor(Color.white);
		drawCenteredString("[Press space to replay]", getRealWidth(), getRealHeight() + (Game.CELLS_SIZE * 4) * Game.SCALE,0, g);
		
	}

	/**
	 * Draw Change Level Screen.
	 * @param g Graphic g
	 * @param level level
	 */
	public void drawChangeLevel(Graphics g, int level) {
		g.setColor(Color.black);
		g.fillRect(0, 0, getRealWidth(), getRealHeight());
		
		Font font = new Font("Arial", Font.PLAIN, 20 * Game.SCALE);
		g.setFont(font);
		g.setColor(Color.white);
		drawCenteredString("LEVEL " + level, getRealWidth(), getRealHeight(),0, g);
		
	}

	/**
	 * Draw Game Paused Screen.
	 * @param g Graphic
	 */
	public void drawPaused(Graphics g) {
		Font font = new Font("Arial", Font.PLAIN, 20 * Game.SCALE);
		g.setFont(font);
		g.setColor(Color.white);
		drawCenteredString("PAUSED", getRealWidth(), getRealHeight(),0, g);
		
	}

	/**
	 * Draw CenterString.
	 * @param s text
	 * @param w width
	 * @param h height
	 * @param padding padding
	 * @param g graphic
	 */
	public void drawCenteredString(String s, int w, int h, int padding, Graphics g) {
	    FontMetrics fm = g.getFontMetrics();
	    int x = (w - fm.stringWidth(s)) / 2;
	    int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2)+padding;
	    g.drawString(s, x, y);
	 }

	/**
	 * return width.
	 * @return width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * return height.
	 * @return height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * return real width.
	 * @return real width
	 */
	public int getRealWidth() {
		return width * Game.SCALE;
	}

	/**
	 * return real height.
	 * @return real height
	 */
	public int getRealHeight() {
		return height * Game.SCALE;
	}
}
