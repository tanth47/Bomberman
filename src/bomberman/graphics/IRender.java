package  bomberman.graphics;
/**
 * @author Taaan
 */
public interface IRender {

	/**
	 * update Screens.
	 */
	void update();

	/**
	 * render Screen.
	 * @param screen screen
	 */
	void render(Screen screen);
}
