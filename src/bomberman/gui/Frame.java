package bomberman.gui;
/**
 * Class Frame
 *
 * @author Taaan
 */

import bomberman.Game;

import javax.swing.*;
import java.awt.*;

/**
 * Swing Frame chứa toàn bộ các component
 */
public class Frame extends JFrame {

    private JPanel containerpane; // panel chứa toàn cảnh
    public GamePanel gamepane;    // panel chứa phần game
    private InfoPanel infopanel;  // panel chứa các thông tin như highscore, time, lives, ...

    private Game game;

    /**
     * Constructor.
     */
    public Frame() {
        containerpane = new JPanel(new BorderLayout());
        gamepane = new GamePanel(this);
        infopanel = new InfoPanel(gamepane.getGame());
        containerpane.add(infopanel, BorderLayout.PAGE_START);
        containerpane.add(gamepane, BorderLayout.PAGE_END);

        game = gamepane.getGame();

        add(containerpane);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        game.start();
    }

    /**
     * set time.
     * @param time time
     */
    public void setTime(int time) {
        infopanel.setTime(time);
    }

    /**
     * set Points.
     * @param points Points
     */
    public void setPoints(int points) {
        infopanel.setPoints(points);
    }

    /**
     * set Lives.
     * @param lives lives
     */
    public void setLives(int lives) {
        infopanel.setLives(lives);
    }

    /**
     * set high score.
     * @param highScore high score
     */
    public void setHighScore(int highScore) {
        infopanel.setHighScore(highScore);
    }

}
