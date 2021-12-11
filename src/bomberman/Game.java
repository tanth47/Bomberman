/**
 * Class Game
 *
 * @author Kyoraku
 * @author Taaaaaaaan
 */

package bomberman;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import bomberman.graphics.Screen;
import bomberman.gui.Frame;
import bomberman.input.Keyboard;

/**
 * Tạo vòng lặp cho game, lưu trữ một vài tham số cấu hình toàn cục,
 * Gọi phương thức render(), update() cho tất cả các entity
 */
public class Game extends Canvas {
	public static final int CELLS_SIZE = 16;
	public static final int WIDTH = CELLS_SIZE * (31 / 2);
	public static final int HEIGHT = 13 * CELLS_SIZE;
	public static int SCALE = 3;
	public static final String TITLE = "BombermanGame";
	public static final int BOMBRATE = 1;
	public static final int BOMBRADIUS = 1;
	public static final double BOMBERSPEED = 1.0;
	public static final int TIME = 200;
	public static final int POINTS = 0;
	public static final int LIVES = 3;
	protected static int SCREENDELAY = 3;
	protected static int bombRate = BOMBRATE;
	protected static int bombRadius = BOMBRADIUS;
	protected static double bomberSpeed = BOMBERSPEED;
	protected static int plusbombRate = 0;
	protected static int plusbombRadius = 0;
	protected static double plusbomberSpeed = 0;
	protected int screenDelay = SCREENDELAY;
	private Keyboard input;
	private boolean running = false;
	private boolean paused = true;
	private Board board;
	private Screen screen;
	private Frame frame;
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	/**
	 * constructor.
	 * @param frame frame
	 */
	public Game(Frame frame) {
		this.frame = frame;
		this.frame.setTitle(TITLE);
		screen = new Screen(WIDTH, HEIGHT);
		input = new Keyboard();
		board = new Board(this, input, screen);
		addKeyListener(input);
	}

	/**
	 * function render game.
	 */
	private void renderGame() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.clear();
		board.render(screen);
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		Graphics g = bs.getDrawGraphics();

		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		board.renderMessages(g);
		g.dispose();
		bs.show();
	}

	/**
	 * function render screen.
	 */
	private void renderScreen() {
		BufferStrategy bs = getBufferStrategy();

		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.clear();
		Graphics g = bs.getDrawGraphics();

		board.drawScreen(g);
		g.dispose();
		bs.show();
	}

	/**
	 * update from input and update board.
	 */
	private void update() {
		input.update();
		board.update();
	}

	/**
	 * main function to start game.
	 */
	public void start() {
		running = true;

		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0; // nanos, 60 khung hinh moi giay
		double delta = 0;
		int frames = 0;
		int updates = 0;

		requestFocus();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				++updates;
				--delta;
			}
			if (paused) {
				if (screenDelay <= 0) {
					board.setShow(-1);
					paused = false;
					board.backgroundSound.play();
				}
				renderScreen();
			} else {
				renderGame();
			}
			++frames;
			if (System.currentTimeMillis() - timer > 1000) {
				frame.setTime(board.subtractTime());
				frame.setPoints(board.getPoints());
				frame.setLives(board.getLives());
				frame.setHighScore(board.getHighScore());
				timer += 1000;
				frame.setTitle(TITLE + " | " + updates + " rate, " + frames + " fps");
				updates = 0;
				frames = 0;
				if (board.getShow() == 2) {
					--screenDelay;
				}
			}
		}
	}

	/**
	 * function to get bomber speed.
	 * @return double speed
	 */
	public static double getBomberSpeed() {
		return bomberSpeed;
	}

	/**
	 * function to get bomb rate (so luong bom dat duoc trong 1 luc).
	 * @return int bomb rate
	 */
	public static int getBombRate() {
		return bombRate;
	}

	/**
	 * function to get radius of bomb flame.
	 * @return int radius
	 */
	public static int getBombRadius() {
		return bombRadius;
	}

	/**
	 * function to up speed of bomber.
	 * @param i double speed
	 */
	public static void addBomberSpeed(double i) {
		plusbomberSpeed += i;
		bomberSpeed += i;
	}

	/**
	 * function to increase radius of bomb.
	 * @param i int i
	 */
	public static void addBombRadius(int i) {
		plusbombRadius += i;
		bombRadius += i;

	}

	/**
	 * function to increase number of bombs in time.
	 * @param i int number
	 */
	public static void addBombRate(int i) {
		plusbombRate += i;
		bombRate += i;
	}

	/**
	 * function to reset all after restart level.
	 */
	public static void levelresetItem() {
		bombRate -= plusbombRate;
		bomberSpeed -= plusbomberSpeed;
		bombRadius -= plusbombRadius;

		plusbombRate = 0;
		plusbomberSpeed = 0;
		plusbombRadius = 0;
	}

	/**
	 * function to reset all after new game or next level.
	 */
	public static void levelnextItem() {
		plusbombRate = 0;
		plusbomberSpeed = 0;
		plusbombRadius = 0;
	}

	/**
	 * function to reset screen delay.
	 */
	public void resetScreenDelay() {
		screenDelay = SCREENDELAY;
	}

	/**
	 * getter board.
	 * @return board
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * function to check if game is paused.
	 * @return boolean true or false
	 */
	public boolean isPaused() {
		return paused;
	}

	/**
	 * function to pause game.
	 */
	public void pause() {
		paused = true;
	}

	/**
	 * function to run game.
	 */
	public void run() {
		running = true;
		paused = false;
	}
}