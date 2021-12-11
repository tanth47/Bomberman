package bomberman.LoadMap;
/**
 * Class FileLevelLoader
 *
 * @author Taaan
 */

import bomberman.Board;
import bomberman.Game;
import bomberman.entities.LayeredEntity;
import bomberman.entities.character.Bomber;
import bomberman.entities.character.enemy.*;
import bomberman.entities.cell.Grass;
import bomberman.entities.cell.Portal;
import bomberman.entities.cell.Wall;
import bomberman.entities.cell.destroyable.Brick;
import bomberman.entities.cell.item.BombItem;
import bomberman.entities.cell.item.FlameItem;
import bomberman.entities.cell.item.SpeedItem;
import bomberman.graphics.Screen;
import bomberman.graphics.Sprite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileLevelLoader extends LevelLoader {

    /**
     * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá trị kí tự đọc được
     * từ ma trận bản đồ trong tệp cấu hình
     */
    private static String[] map;

    public FileLevelLoader(Board board, int level) {
        super(board, level);
    }

    @Override
    public void loadLevel(int level) {
        //  đọc dữ liệu từ tệp cấu hình /levels/Level{level}.txt
        //  cập nhật các giá trị đọc được vào width, height, level, map
        try {
            Scanner sc = new Scanner(new File("res/levels/Level" + level + ".txt"));
            this.level = sc.nextInt();
            height = sc.nextInt();
            width = sc.nextInt();
            map = new String[height];
            map[0] = sc.nextLine();
            for (int i = 0; i < height; i++) {
                map[i] = sc.nextLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void createEntities() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                AddEntity(map[y].charAt(x), x, y);
            }
        }

    }

    /**
     * Them entity
     * @param ch ki hieu
     * @param x toa do
     * @param y toa do
     */
    public void AddEntity(char ch, int x, int y) {
        // tạo các Entity của màn chơi
        //  sau khi tạo xong, gọi board.addEntity() để thêm Entity vào game
        int pos = x + y * width;
        switch (ch) {
            case '#':
                board.addEntity(pos, new Wall(x, y, Sprite.wall));
                break;
            case ' ':
                board.addEntity(pos, new Grass(x, y, Sprite.grass));
                break;
            case 'p':
                board.addCharacter(new Bomber(Coordinates.cellToPixel(x), Coordinates.cellToPixel(y) + Game.CELLS_SIZE, board));
                Screen.setOffset(0, 0);
                board.addEntity(pos, new Grass(x, y, Sprite.grass));
                break;
            case '*':
                board.addEntity(pos, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new Brick(x, y, Sprite.brick)));
                break;
            case 'b':
                board.addEntity(pos, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new BombItem(x, y, Sprite.powerup_bombs), new Brick(x, y, Sprite.brick)));
                break;
            case 'f':
                board.addEntity(pos, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new FlameItem(x, y, Sprite.powerup_flames), new Brick(x, y, Sprite.brick)));
                break;
            case 's':
                board.addEntity(pos, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new SpeedItem(x, y, Sprite.powerup_speed), new Brick(x, y, Sprite.brick)));
                break;
            case 'x':
                board.addEntity(pos, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new Portal(x, y, Sprite.portal, board), new Brick(x, y, Sprite.brick)));
                break;
            case '1':
                board.addCharacter(new Balloon(Coordinates.cellToPixel(x), Coordinates.cellToPixel(y) + Game.CELLS_SIZE, board));
                board.addEntity(pos, new Grass(x, y, Sprite.grass));
                break;
            case '2':
                board.addCharacter(new Oneal(Coordinates.cellToPixel(x), Coordinates.cellToPixel(y) + Game.CELLS_SIZE, board));
                board.addEntity(pos, new Grass(x, y, Sprite.grass));
                break;
            case '3':
                board.addCharacter(new Doll(Coordinates.cellToPixel(x), Coordinates.cellToPixel(y) + Game.CELLS_SIZE, board));
                board.addEntity(pos, new Grass(x, y, Sprite.grass));
                break;
            case '4':
                board.addCharacter(new Minvo(Coordinates.cellToPixel(x), Coordinates.cellToPixel(y) + Game.CELLS_SIZE, board));
                board.addEntity(pos, new Grass(x, y, Sprite.grass));
                break;
            case '5':
                board.addCharacter(new Kondoria(Coordinates.cellToPixel(x), Coordinates.cellToPixel(y) + Game.CELLS_SIZE, board));
                board.addEntity(pos, new Grass(x, y, Sprite.grass));
                break;
            case '6':
                board.addCharacter(new Ovapes(Coordinates.cellToPixel(x), Coordinates.cellToPixel(y) + Game.CELLS_SIZE, board));
                board.addEntity(pos, new Grass(x, y, Sprite.grass));
                break;
            default:
                board.addEntity(pos, new Grass(x, y, Sprite.grass));
                break;

        }

    }
}

