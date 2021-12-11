package  bomberman.gui;
/**
 * Class GamePanel
 * @author Taaan
 */
import  bomberman.Game;
import javax.swing.*;
import java.awt.*;

/**
 * Swing Panel chứa cảnh game
 */
public class GamePanel extends JPanel {

	private Game game;

	/**
	 * Constructor.
	 * @param frame frame
	 */
	public GamePanel(Frame frame) {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE));

		game = new Game(frame);

		add(game);

		game.setVisible(true);

		setVisible(true);
		setFocusable(true);
		
	}

	/**
	 * return game.
	 * @return game
	 */
	public Game getGame() {
		return game;
	}
	
}
