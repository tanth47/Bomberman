package  bomberman.graphics;
/**
 * Class SpriteSheet
 * @author Taaan
 */
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Tất cả sprite (hình ảnh game) được lưu trữ vào một ảnh duy nhất
 * Class này giúp lấy ra các sprite riêng từ 1 ảnh chung duy nhất đó
 */
public class SpriteSheet {

	private String path;
	public final int SIZEW;
	public final int SIZEH;
	public int[] pixels;
	
	public static SpriteSheet tiles = new SpriteSheet("/textures/classic.png", 256,256);
	
	
	public SpriteSheet(String path, int sizeW, int sizeH) {
		this.path = path;
		SIZEW = sizeW;
		SIZEH = sizeH;
		this.pixels = new int[SIZEW * SIZEW];
		load();
	}
	
	private void load() {
		try {
			URL a = SpriteSheet.class.getResource(this.path);
			BufferedImage image = ImageIO.read(a);
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, this.pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
