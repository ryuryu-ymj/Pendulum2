import org.newdawn.slick.*;

/**
 * プレイ画面の更新,描画を行うクラス.
 */
public class Play extends GameState
{
    /**
     * 画面の横幅
     */
    public static final int DISPLAY_WIDTH = 1400;
    /**
     * 画面の縦幅
     */
    public static final int DISPLAY_HEIGHT = 900;
    /**
     * フレームカウンタ
     */
    public static int counter;

    /**
     * プレイするステージの番号 0から
     */
    public int stageNum;

    private State state;

    private enum State
    {
        STAGETITLE, PLAY, GAMEOVER
    }

    ObjectPool objectPool;
    StageData stageData;
    PlayMessage playMessage;

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
    }

    /**
     * 初期化処理.
     */
    @Override
    public void init(GameContainer gc)
            throws SlickException
    {
        stageNum = 0;
        stageData.loadStageDate(stageNum);
        objectPool.init();
        state = State.STAGETITLE;
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
            case STAGETITLE:
                if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
                {
                    state = State.PLAY;
                }
                break;
            case PLAY:
                objectPool.moveGrounds(stageData.getGroundXs(), stageData.getGroundYs()
                        , stageData.getGroundTypes(), stageData.getGroundPositions());
                objectPool.moveJoints(stageData.getJointXs(), stageData.getJointYs(), stageData.getJointTypes());
                objectPool.moveCherries(stageData.getCherryXs(), stageData.getCherryYs());
                objectPool.moveHearts(stageData.getHeartXs(), stageData.getHeartYs());
                objectPool.moveBackObjects(stageData.getBackObjectXs(), stageData.getBackObjectYs(), stageData.getBackObjectTypes()
                        , stageData.getBackObjectLayers());
                objectPool.collisionDetection(gc);
                objectPool.update(gc);
                if (objectPool.isPlayerGoal())
                {
                    objectPool.init();
                    stageData.loadStageDate(++stageNum);
                }
                if (objectPool.isPlayerDead())
                {
                    init(gc);
                    state = State.GAMEOVER;
                }
                break;
            case GAMEOVER:
                if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
                {
                    state = State.STAGETITLE;
                }
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
        g.setBackground(new Color(189f / 256, 226f / 256, 14f / 256));

        switch (state)
        {
            case STAGETITLE:
                playMessage.renderStageNum(g, stageNum, counter);
                break;
            case PLAY:
                objectPool.render(g, im);
                break;
            case GAMEOVER:
                playMessage.renderGameOver(g, counter);
                break;
        }
    }
}