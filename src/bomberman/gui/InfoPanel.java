package  bomberman.gui;
/**
 * Class Infor Panel
 * @author Taaan
 */
import  bomberman.Game;
import javax.swing.*;
import java.awt.*;

/**
 * Swing Panel hiển thị thông tin thời gian, điểm mà người chơi đạt được
 */
public class InfoPanel extends JPanel {
	
	private JLabel timeLabel;
	private JLabel pointsLabel;
	private JLabel livesLabel;
	private JLabel HighScoreLabel;

	/**
	 * Contrutor.
	 * @param game Game
	 */
	public InfoPanel(Game game) {
		setLayout(new GridLayout());
		
		timeLabel = new JLabel("Time: " + game.getBoard().getTime());
		timeLabel.setForeground(Color.WHITE);
		timeLabel.setHorizontalAlignment(JLabel.CENTER);
		
		pointsLabel = new JLabel("Points: " + game.getBoard().getPoints());
		pointsLabel.setForeground(Color.YELLOW);
		pointsLabel.setHorizontalAlignment(JLabel.CENTER);
		
		livesLabel = new JLabel("Lives: " + game.getBoard().getLives());
		livesLabel.setForeground(Color.RED);
		livesLabel.setHorizontalAlignment(JLabel.CENTER);
		
		HighScoreLabel = new JLabel("High Score: " + game.getBoard().getHighScore());
		HighScoreLabel.setForeground(Color.ORANGE);
		HighScoreLabel.setHorizontalAlignment(JLabel.CENTER);
		
		add(timeLabel);
		add(pointsLabel);
		add(livesLabel);
		add(HighScoreLabel);
		
		setBackground(Color.black);
		setPreferredSize(new Dimension(0, 40));
	}

	/**
	 * set time.
	 * @param t time
	 */
	public void setTime(int t) {
		timeLabel.setText("Time: " + t);
	}

	/**
	 * set Lives
	 * @param lives
	 */
	public void setLives(int lives) {
		livesLabel.setText("Lives: " + lives);
		
	}

	/**
	 * set Points.
	 * @param p points
	 */
	public void setPoints(int p) {
		pointsLabel.setText("Score: " + p);
	}

	/**
	 * set high score.
	 * @param highScore
	 */
	public void setHighScore(int highScore) {
		HighScoreLabel.setText("High Score: " + highScore);
	}
	
}
