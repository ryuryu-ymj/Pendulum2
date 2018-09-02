import org.newdawn.slick.*;

/**
 * プレイ画面の更新,描画を行うクラス.
 */
public class Play extends GameState
{
    /**
     * 画面の横幅
     */
    public static final int DISPLAY_WIDTH = 1200;
    /**
     * 画面の縦幅
     */
    public static final int DISPLAY_HEIGHT = 900;
    /**
     * フレームカウンタ
     */
    public static int counter;
    private Music bgm;

    /**
     * プレイするステージの番号 0から
     */
    private int stageNum;

    public int getStageNum()
    {
        return stageNum;
    }

    private State state;

    private enum State
    {
        STAGE_TITLE, PLAY, GAMEOVER, KEEP
    }

    ObjectPool objectPool;
    private StageData stageData;
    private PlayMessage playMessage;

    /**
     * コンストラクタ
     */
    Play()
    {
        super();
        objectPool = new ObjectPool();
        objectPool.create(objectPool);
        stageData = new StageData();
        playMessage = new PlayMessage();
        try
        {
            bgm = new Music("res/sound/bgm.ogg");
            bgm.setVolume(0.5f);
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 初期化処理.
     */
    public void init(GameContainer gc)
    {
        initStage(0);
    }

    public void initStage(int stageNum)
    {
        if (stageData.loadStageDate(stageNum))
        {
            this.stageNum = stageNum;
        }
        objectPool.init();
        state = State.STAGE_TITLE;
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
            case STAGE_TITLE:
                if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON))
                {
                    state = State.PLAY;
                    bgm.loop();
                }
                break;
            case PLAY:
                objectPool.moveGrounds(stageData.getGroundXs(), stageData.getGroundYs()
                        , stageData.getGroundTypes(), stageData.getGroundPositions());
                objectPool.moveJoints(stageData.getJointXs(), stageData.getJointYs(), stageData.getJointTypes(),
                        stageData.getJointLockRadiuses());
                objectPool.moveCherries(stageData.getCherryXs(), stageData.getCherryYs());
                objectPool.moveHearts(stageData.getHeartXs(), stageData.getHeartYs());
                objectPool.moveBackObjects(stageData.getBackObjectXs(), stageData.getBackObjectYs(), stageData.getBackObjectTypes()
                        , stageData.getBackObjectLayers());
                objectPool.collisionDetection(gc);
                objectPool.update(gc);
                if (objectPool.isPlayerGoal() || objectPool.isPlayerDead() || objectPool.isPlayerGameOver())
                {
                    state = State.KEEP;
                    counter = 0;
                    bgm.pause();
                }
                break;
            case KEEP:
                if (objectPool.isPlayerGameOver())
                {
                    if (counter > 30)
                    {
                        init(gc);
                        state = State.GAMEOVER;
                    }
                }
                else if (objectPool.isPlayerDead())
                {
                    if (counter > 30)
                    {
                        objectPool.initStage();
                        state = State.STAGE_TITLE;
                    }
                }
                else if (objectPool.isPlayerGoal())
                {
                    if (counter > 90)
                    {
                        initStage(stageNum + 1);
                    }
                }
                break;
            case GAMEOVER:
                if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON))
                {
                    state = State.STAGE_TITLE;
                }
                break;
        }

        counter++;
    }

    /**
     * ステップごとの描画処理.
     */
    @Override
    public void render(GameContainer gc, Graphics g, ImageManager im)
            throws SlickException
    {
        switch (state)
        {
            case STAGE_TITLE:
                g.setBackground(new Color(189f / 256, 226f / 256, 14f / 256));
                playMessage.renderStageNum(g, stageNum, counter);
                objectPool.score.render(g, im);
                break;
            case PLAY:
            case KEEP:
                g.setBackground(new Color(189f / 256, 226f / 256, 14f / 256));
                objectPool.render(g, im);
                break;
            case GAMEOVER:
                g.setBackground(new Color(189f / 256, 226f / 256, 14f / 256));
                playMessage.renderGameOver(g, counter);
                break;
        }
    }

    public void finish()
    {
        bgm.pause();
    }
}