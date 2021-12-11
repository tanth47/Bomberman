package bomberman;
/**
 * Class Board
 *
 * @author Taaan
 * @author Kyoraku
 */

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import bomberman.entities.Entity;
import bomberman.entities.LayeredEntity;
import bomberman.entities.Message;
import bomberman.entities.bomb.Bomb;
import bomberman.entities.bomb.FlameSegment;
import bomberman.entities.character.Bomber;
import bomberman.entities.character.Character;
import bomberman.entities.cell.Wall;
import bomberman.entities.cell.destroyable.Brick;
import bomberman.graphics.IRender;
import bomberman.graphics.Screen;
import bomberman.input.Keyboard;
import bomberman.LoadMap.FileLevelLoader;
import bomberman.LoadMap.LevelLoader;

/**
 * Quản lý thao tác điều khiển, load level, render các màn hình của game
 */
public class Board implements IRender {

    public Entity[] entities;
    public List<Character> characters = new ArrayList<>();
    public static SoundPlayer levelUpSound = new SoundPlayer(new File("res/sound/levelUp.wav"));
    public static SoundPlayer backgroundSound = new SoundPlayer(new File("res/sound/bgmusic.wav"));
    public static SoundPlayer gameOverSound = new SoundPlayer(new File("res/sound/gameOver.wav"));
    public static SoundPlayer victorySound = new SoundPlayer(new File("res/sound/victory.wav"));
    public static SoundPlayer explosionSound = new SoundPlayer(new File("res/sound/explosion.wav"));
    public static SoundPlayer placeBombSound = new SoundPlayer(new File("res/sound/placeBomb.wav"));
    public static SoundPlayer bomberDieSound = new SoundPlayer(new File("res/sound/bomberDie.wav"));
    public static SoundPlayer getItemSound = new SoundPlayer(new File("res/sound/getItem.wav"));

    protected LevelLoader levelLoader;
    protected Game game;
    protected Keyboard input;
    protected Screen screen;
    protected List<Bomb> bombs = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();
    private int screenToShow = -1; //1:endgame, 2:changelevel, 3:paused, 4: startgame,

    private int time = Game.TIME;
    private int points = Game.POINTS;
    private int lives = Game.LIVES;
    private int plusPoint = 0;
    private int highScore = 0;

    private boolean victory = false;
    private double lastUpdatePaused = 0;

    /**
     * Constructor.
     *
     * @param game   game
     * @param input  input
     * @param screen screen
     */
    public Board(Game game, Keyboard input, Screen screen) {
        this.game = game;
        this.input = input;
        this.screen = screen;
        readHighScore();
        loadLevel(1);
    }

    /**
     * write high score to file.
     *
     * @throws IOException when error on open file
     */
    private void writeHighScore() throws IOException {
        FileWriter w = new FileWriter(new File("res/userData/highScore.txt"));
        w.write(String.valueOf(highScore));
        w.close();
    }

    /**
     * read high score from file.
     */
    private void readHighScore() {
        Scanner sc = null;
        try {
            sc = new Scanner(new File("res/userData/highScore.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        highScore = sc.nextInt();
        sc.close();
    }

    /**
     * Cập nhật trạng thái game, được chạy liên tục.
     */
    @Override
    public void update() {
        //Khi thua, press space de choi game mới
        if (screenToShow == 1 && input.space) {
            newGame();
        }
        //press esc để pause, nếu bấm 2 lần esc liên tục thì ko pause.
        if (input.esc && System.currentTimeMillis() - lastUpdatePaused > 500) {
            if (game.isPaused()) gameResume();
            else gamePause();
            lastUpdatePaused = System.currentTimeMillis();
        }
        if (game.isPaused()) return;
        updateEntities();
        updateCharacters();
        updateBombs();
        updateMessages();
        detectEndGame();
        characters.removeIf(a -> a.isRemoved());
    }

    /**
     * Render game to Screne, chỉ vẽ 1 phần của map.
     *
     * @param screen
     */
    @Override
    public void render(Screen screen) {
        if (game.isPaused()) return;

        int xLeft = Screen.xOffset >> 4;
        int yUp = Screen.yOffset >> 4;
        int xRight = (Screen.xOffset + screen.getWidth() + Game.CELLS_SIZE) / Game.CELLS_SIZE;
        int yDown = (Screen.yOffset + screen.getHeight()) / Game.CELLS_SIZE;
        for (int y = yUp; y < yDown; y++) {
            for (int x = xLeft; x < xRight; x++) {
                entities[x + y * levelLoader.getWidth()].render(screen);
            }
        }
        renderBombs(screen);
        renderCharacter(screen);
    }

    /**
     * Game Pause.
     */
    public void gamePause() {
        game.resetScreenDelay();
        backgroundSound.stop();
        if (screenToShow <= 0)
            screenToShow = 3;
        game.pause();
    }

    /**
     * Game Resume.
     */
    public void gameResume() {
        game.resetScreenDelay();
        backgroundSound.play();
        screenToShow = -1;
        game.run();
    }

    /**
     * Load level.
     *
     * @param level level
     */
    public void loadLevel(int level) {
        time = Game.TIME;
        screenToShow = 2;
        game.resetScreenDelay();
        game.pause();
        characters.clear();
        bombs.clear();
        messages.clear();
        try {
            writeHighScore();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (level > 5) {
            victory = true;
            endGame();
            return;
        }
        levelLoader = new FileLevelLoader(this, level);
        entities = new Entity[levelLoader.getHeight() * levelLoader.getWidth()];
        levelLoader.createEntities();
        backgroundSound.stop();
        gameOverSound.stop();
        victorySound.stop();
        levelUpSound.play();
    }

    /**
     * next level.
     */
    public void nextLevel() {
        Game.addBombRate(bombs.size());
        bombs.clear();
        addPoints(time);
        plusPoint = 0;
        Game.levelnextItem();
        loadLevel(levelLoader.getLevel() + 1);
    }

    /**
     * restart level. Nếu hết 3 mạng thì end game.
     */
    public void restartLevel() {
        lives--;
        if (lives == 0) endGame();
        else {
            this.points -= this.plusPoint;
            this.plusPoint = 0;
            Game.levelresetItem();
            loadLevel(levelLoader.getLevel());
        }
    }

    /**
     * kiem tra xem het thoi gian hay chua. Nếu hết thời gian thì restart level.
     */
    protected void detectEndGame() {
        if (time < 0) {
            restartLevel();
        }
    }

    /**
     * Tạo game mới.
     */
    public void newGame() {
        lives = Game.LIVES;
        victory = false;
        points = Game.POINTS;
        Game.bombRadius = Game.BOMBRADIUS;
        Game.bombRate = Game.BOMBRATE;
        Game.bomberSpeed = Game.BOMBERSPEED;
        plusPoint = 0;
        Game.levelnextItem();
        loadLevel(1);
    }

    /**
     * end game.
     */
    public void endGame() {
        screenToShow = 1;
        game.resetScreenDelay();
        game.pause();
        backgroundSound.stop();
        if (victory)
            victorySound.play();
        else
            gameOverSound.play();
    }

    /**
     * kiem tra xem sl enemy = 0 ?
     *
     * @return false nếu còn
     */
    public boolean detectNoEnemies() {
        for (Character c : characters) {
            if (c instanceof Bomber == false)
                return false;
        }
        return true;
    }

    /**
     * Vẽ các màn hình phụ cho game.
     *
     * @param g Graphic to show
     */
    public void drawScreen(Graphics g) {
        switch (screenToShow) {
            case 1:
                if (victory)
                    screen.drawVictory(g, points);
                else
                    screen.drawGameOver(g, points);
                break;
            case 2:
                screen.drawChangeLevel(g, levelLoader.getLevel());
                break;
            case 3:
                screen.drawPaused(g);
                break;
        }
    }

    /**
     * Trả về thực thể ở ô vị trí x,y.
     *
     * @param x coordinate X
     * @param y coordinate Y
     * @param m Excluding m, ví dụ với LayerEntity(Brick, Item), sẽ có nhiều hơn 1 entity ở 1 vị trí.
     * @return Entity result
     */
    public Entity getEntity(double x, double y, Character m) {

        Entity res = null;

        res = getFlameSegmentAt((int) x, (int) y);
        if (res != null) return res;

        res = getBombAt(x, y);
        if (res != null) return res;

        res = getCharacterAtExcluding((int) x, (int) y, m);
        if (res != null) return res;

        res = getEntityAt((int) x, (int) y);

        return res;
    }

    /**
     * kiem tra xem vi tri co phai wall/brick.
     *
     * @param x toa do
     * @param y toa do
     * @return true/false
     */
    public boolean isWallBrick(int x, int y) {
        Entity e = entities[x + y * levelLoader.getWidth()];
        if (e instanceof LayeredEntity) {
            return ((LayeredEntity) e).getTopEntity() instanceof Brick;
        }
        return (e instanceof Brick || e instanceof Wall);

    }

    /**
     * Trả về Character ở vị trí x,y.
     *
     * @param x tọa độ x
     * @param y tọa độ y
     * @return Character res.
     */
    public Character getCharacterAt(double x, double y) {
        for (Character cur : characters) {
            if (cur.getXCell() == x && cur.getYCell() == y)
                return cur;
        }
        return null;
    }

    /**
     * Trả về bombs.
     *
     * @return bombs
     */
    public List<Bomb> getBombs() {
        return bombs;
    }

    /**
     * Lấy ra Bomb ở vị trí x,y.
     *
     * @param x tọa độ x
     * @param y tọa độ y
     * @return Bomb at (x,y) or null
     */
    public Bomb getBombAt(double x, double y) {
        for (Bomb b : bombs) {
            if (b.getX() == (int) x && b.getY() == (int) y)
                return b;
        }
        return null;
    }

    /**
     * Trả về đối tượng Bomber.
     *
     * @return Bomber
     */
    public Bomber getBomber() {
        for (Character cur : characters) {
            if (cur instanceof Bomber)
                return (Bomber) cur;
        }

        return null;
    }

    /**
     * Trả về thực thể ở ô vị trí x,y và khác Character a.
     *
     * @param x coordinate X
     * @param y coordinate Y
     * @param a Excluding a, ví dụ với LayerEntity(Brick, Item), sẽ có nhiều hơn 1 entity ở 1 vị trí.
     * @return Entity result
     */
    public Character getCharacterAtExcluding(int x, int y, Character a) {
        for (Character character : characters) {
            if (character == a) {
                continue;
            }
            if (character.getXCell() == x && character.getYCell() == y) {
                return character;
            }
        }
        return null;
    }

    /**
     * Trả về đối tượng Flame Segment ở vị trí (x,y).
     *
     * @param x tọa độ x
     * @param y tọa độ y
     * @return FlameSegment res
     */
    public FlameSegment getFlameSegmentAt(int x, int y) {
        for (Bomb b : bombs) {
            FlameSegment flameSegment = b.flameAt(x, y);
            if (flameSegment != null) {
                return flameSegment;
            }
        }
        return null;
    }

    /**
     * Trả về thực thể ở vị trí x,y.
     *
     * @param x tọa độ x
     * @param y tọa độ y
     * @return Entity result
     */
    public Entity getEntityAt(int x, int y) {
        return entities[x + y * levelLoader.getWidth()];
    }

    /**
     * Thêm mới 1 entity vào bảng.
     *
     * @param pos position
     * @param e   Entity
     */
    public void addEntity(int pos, Entity e) {
        entities[pos] = e;
    }

    /**
     * Thêm mới 1 Character vào board.
     *
     * @param e Character
     */
    public void addCharacter(Character e) {
        characters.add(e);
    }

    /**
     * Add a new Bomb to board.
     *
     * @param e Bomb
     */
    public void addBomb(Bomb e) {
        bombs.add(e);
    }

    /**
     * Add a message to board.
     *
     * @param e Message
     */
    public void addMessage(Message e) {
        messages.add(e);
    }

    /**
     * Render all Character to Screen.
     *
     * @param screen Screen
     */
    protected void renderCharacter(Screen screen) {
        for (Character character : characters) {
            character.render(screen);
        }
    }

    /**
     * render all Bombs to Screen.
     *
     * @param screen Screne
     */
    protected void renderBombs(Screen screen) {
        for (Bomb b : bombs) {
            b.render(screen);
        }
    }

    /**
     * Render all Message to Graphic.
     *
     * @param g Graphic
     */
    public void renderMessages(Graphics g) {
        for (Message m : messages) {
            g.setFont(new Font("Arial", Font.PLAIN, m.getSize()));
            g.setColor(m.getColor());
            g.drawString(m.getText(), (int) m.getX() - Screen.xOffset * Game.SCALE, (int) m.getY());
        }
    }

    /**
     * update Entities.
     */
    protected void updateEntities() {
        if (game.isPaused()) return;
        for (Entity entity : entities) {
            entity.update();
        }
    }

    /**
     * Update Characters.
     */
    protected void updateCharacters() {
        if (game.isPaused()) return;
        Character bomber = null;
        for (Character character : characters) {
            if (game.isPaused()) {
                break;
            }
            if ((character instanceof Bomber)) bomber = character;
        }
        if (bomber != null) bomber.update();
        for (Character character : characters) {
            if (game.isPaused()) {
                break;
            }
            if (!(character instanceof Bomber)) character.update();
        }
    }

    /**
     * Update Bombs.
     */
    protected void updateBombs() {
        if (game.isPaused()) return;
        for (Bomb b : bombs) {
            b.update();
        }
    }

    /**
     * update Message.
     */
    protected void updateMessages() {
        if (game.isPaused()) return;
        Message m;
        int left;
        for (int i = 0; i < messages.size(); i++) {
            m = messages.get(i);
            left = m.getInterval();

            if (left > 0) {
                m.setInterval(--left);
            } else {
                messages.remove(i);
            }
        }
    }

    /**
     * subtract Time.
     *
     * @return time remain
     */
    public int subtractTime() {
        if (game.isPaused())
            return this.time;
        else
            return this.time--;
    }

    /**
     * return input.
     *
     * @return input
     */
    public Keyboard getInput() {
        return input;
    }

    /**
     * return level.
     *
     * @return level
     */
    public LevelLoader getLevel() {
        return levelLoader;
    }

    /**
     * return game.
     *
     * @return game
     */
    public Game getGame() {
        return game;
    }

    /**
     * return screen to show.
     *
     * @return screen to show
     */
    public int getShow() {
        return screenToShow;
    }

    /**
     * set screen to show.
     *
     * @param i screen i
     */
    public void setShow(int i) {
        screenToShow = i;
    }

    /**
     * return time.
     *
     * @return time
     */
    public int getTime() {
        return time;
    }

    /**
     * return points.
     *
     * @return point
     */
    public int getPoints() {
        return points;
    }

    /**
     * get Lives.
     *
     * @return lives
     */
    public int getLives() {
        return lives;
    }

    /**
     * return high score.
     *
     * @return high score
     */
    public int getHighScore() {
        return highScore;
    }

    /**
     * add points to player's points.
     *
     * @param points points
     */
    public void addPoints(int points) {
        this.plusPoint += points;
        this.points += points;
        if (highScore < points) {
            highScore = points;
        }

    }

    /**
     * return width.
     *
     * @return width
     */
    public int getWidth() {
        return levelLoader.getWidth();
    }

    /**
     * return height.
     *
     * @return height
     */
    public int getHeight() {
        return levelLoader.getHeight();
    }

}
