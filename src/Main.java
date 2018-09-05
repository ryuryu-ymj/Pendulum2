import org.lwjgl.input.Cursor;
import org.newdawn.slick.*;

/**
 * メインクラス.
 * ウィンドウの生成及びゲームシーンの管理を行う.
 */
public class Main extends BasicGame
{
    /**
     * タイトル画面
     */
    Title title;
    /**
     * プレイ画面
     */
    Play play;
    Edit edit;
    Credit credit;
    /**
     * ゲームシーン
     */
    private State state;
    ImageManager im;
    FontManager fm;

    /**
     * マウス入力用
     */
    private Mouse mouse;

    public enum State
    {
        TITLE, PLAY, EDIT, CREDIT
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
        edit = new Edit();
        credit = new Credit();
        state = State.TITLE;
        title.init(gc);
        im = new ImageManager();
        fm = new FontManager();

        mouse = new Mouse();

        gc.setMouseCursor(new Image("res/img/clear_img.png"), 0, 0);
    }

    /**
     * ステップごとの更新.
     */
    @Override
    public void update(GameContainer gc, int delta)
            throws SlickException
    {
        mouse.update(gc);

        switch (state)
        {
            case TITLE:
                title.update(gc, delta);
                if (title.isGoToPlay())
                {
                    StageData.fileFolder = StageData.FileFolder.OFFICIAL;
                    play.init(gc);
                    state = State.PLAY;
                }
                else if (title.isGoToEdit())
                {
                    edit.init(gc);
                    state = State.EDIT;
                }
                else if (title.isGoToCredit())
                {
                    credit.init(gc);
                    state = State.CREDIT;
                }
                break;
            case PLAY:
                play.update(gc, delta);
                if (play.isGoToTitle())
                {
                    play.finish();
                    title.init(gc);
                    state = State.TITLE;
                }
                if (play.isGoToEdit())
                {
                    play.finish();
                    edit.initStage(gc, play.getStageNum(), (int) play.objectPool.camera.getX(), (int) play.objectPool.camera.getY());
                    state = State.EDIT;
                }
                break;
            case EDIT:
                edit.update(gc, delta);
                if (edit.isGoToPlay())
                {
                    StageData.fileFolder = StageData.FileFolder.SELF_MADE;
                    play.initStage(edit.getStageNum());
                    play.objectPool.score.initScore();
                    state = State.PLAY;
                }
                else if (edit.isGoToTitle())
                {
                    title.init(gc);
                    state = State.TITLE;
                }
                break;
            case CREDIT:
                credit.update(gc, delta);
                if (Mouse.isJustClicked())
                {
                    title.init(gc);
                    state = State.TITLE;
                }
                break;
        }

        if (gc.getInput().isKeyDown(Input.KEY_LCONTROL) || gc.getInput().isKeyDown(Input.KEY_RCONTROL))
        {
            if (gc.getInput().isKeyPressed(Input.KEY_P))
            {
                if (state == State.EDIT)
                {
                    StageData.fileFolder = StageData.FileFolder.SELF_MADE;
                    play.initStage(edit.getStageNum());
                    play.objectPool.score.initScore();
                    state = State.PLAY;
                }
            }
            else if (gc.getInput().isKeyPressed(Input.KEY_E))
            {
                if (state == State.PLAY)
                {
                    play.finish();
                    edit.initStage(gc, play.getStageNum(), (int) play.objectPool.camera.getX(), (int) play.objectPool.camera.getY());
                    state = State.EDIT;
                }
            }
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
            case TITLE:
                title.render(gc, g, im, fm);
                break;
            case PLAY:
                play.render(gc, g, im, fm);
                break;
            case EDIT:
                edit.render(gc, g, im, fm);
                break;
            case CREDIT:
                credit.render(gc, g, im, fm);
                break;
        }

        im.drawCursor(gc.getInput().getMouseX() + 5, gc.getInput().getMouseY() + 13);
    }

    /**
     * メインメソッド.
     * ウィンドウの生成を行う.
     */
    public static void main(String[] args)
    {
        Main main = new Main("Pendulum2");
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
