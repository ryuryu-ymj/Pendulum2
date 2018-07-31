import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * ゲームシーンの抽象クラス.
 *
 * @author negset
 */
public abstract class GameState
{
	/**
	 * 初期化を行う.
	 */
	public abstract void init(GameContainer gc)
			throws SlickException;

	/**
	 * 動作を規定する.
	 */
	public abstract void update(GameContainer gc, int delta)
			throws SlickException;

	/**
	 * 描画処理を行う.
	 */
	public abstract void render(GameContainer gc, Graphics g, ImageManager im)
			throws SlickException;
}
