import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * メインクラス.
 * ウィンドウの生成及びゲームシーンの管理を行う.
 */
public class Main extends BasicGame
{
    /** タイトル画面 */
    Title title;
    /** プレイ画面 */
    Play play;
    /** ゲームシーン */
    private State state;
    ImageManager im;

    public enum State
    {
        Title,
        Play
    }

    /**
     * コンストラクタ
     *
     * @param name ゲーム名
     * @throws SlickException
     */
    public Main(String name)
    {
        super(name);
    }

    /**
     * 初期化処理.
     */
    @Override
    public void init(GameContainer gc)
            throws SlickException
    {
        title = new Title();
        play = new Play();
        state = State.Play;
        play.init(gc);
        im = new ImageManager();
    }

    /**
     * ステップごとの更新.
     */
    @Override
    public void update(GameContainer gc, int delta)
            throws SlickException
    {
        switch (state)
        {
            case Title:
                title.update(gc, delta);
                if (title.nextState)
                {
                    play.init(gc);
                    state = State.Play;
                }
                break;
            case Play:
                play.update(gc, delta);
                if (play.nextState)
                {
                    title.init(gc);
                    state = State.Title;
                }
                break;
        }
    }

    /**
     * ステップごとの描画処理.
     */
    @Override
    public void render(GameContainer gc, Graphics g)
            throws SlickException
    {
        // 背景を描画
        g.setBackground(Color.white);

        switch (state)
        {
            case Title:
                title.render(gc, g, im);
                break;
            case Play:
                play.render(gc, g, im);
                break;
        }
    }

    /**
     * メインメソッド.
     * ウィンドウの生成を行う.
     */
    public static void main(String[] args)
    {
        Main main = new Main("The Past Myself");
        try
        {
            AppGameContainer agc = new AppGameContainer(main);
            agc.setDisplayMode(Play.DISPLAY_WIDTH, Play.DISPLAY_HEIGHT, false);
            agc.setTargetFrameRate(60);
            agc.setShowFPS(false);
            agc.setAlwaysRender(true);
            //agc.setIcon("res/img/bullet0.png");
            agc.start();
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
    }
}
