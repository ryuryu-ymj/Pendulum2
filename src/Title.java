import org.newdawn.slick.*;

/**
 * タイトル画面の更新、描画を行うクラス
 * @author ryuryu
 *
 */
public class Title extends GameState
{

	@Override
	public void init(GameContainer gc) throws SlickException
	{
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException
	{
	}

	@Override
	public void render(GameContainer gc, Graphics g, ImageManager im) throws SlickException
	{
		// TODO 自動生成されたメソッド・スタブ
        g.setColor(Color.black);
        g.drawString("If play mode, press p. If edit mode, press e.", 100, 100);
	}
}
