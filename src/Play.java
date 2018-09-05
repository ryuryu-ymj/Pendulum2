import org.newdawn.slick.*;

/**
 * プレイ画面の更新,描画を行うクラス.
 *
 * @author ryuryu
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
    private ImageButton homeButton;
    private ImageButton editButton;

    /**
     * プレイするステージの番号 0から
     */
    private int stageNum;
    private boolean isGoToTitle;
    private boolean isGoToEdit;

    private State state;

    private enum State
    {
        STAGE_TITLE, PLAY, GAME_OVER, KEEP, GAME_CLEAR
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
        homeButton = new ImageButton(30, 30, 60, 60);
        editButton = new ImageButton(90, 30, 60, 60);
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
        objectPool.init();
    }

    public boolean initStage(int stageNum)
    {
        if (stageData.loadStageDate(stageNum))
        {
            this.stageNum = stageNum;
            objectPool.initStage();
            state = State.STAGE_TITLE;
            isGoToTitle = false;
            isGoToEdit = false;
            editButton.active = (StageData.fileFolder != StageData.FileFolder.OFFICIAL);
            return true;
        }
        return false;
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
                if (Mouse.isJustClicked())
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
                        state = State.GAME_OVER;
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
                        if (!initStage(stageNum + 1))
                        {
                            state = State.GAME_CLEAR;
                        }
                    }
                }
                break;
            case GAME_OVER:
                if (Mouse.isJustClicked())
                {
                    state = State.STAGE_TITLE;
                }
                break;
            case GAME_CLEAR:
                if (Mouse.isJustClicked())
                {
                    isGoToTitle = true;
                }
        }
        homeButton.checkPressed(gc);
        editButton.checkPressed(gc);
        if (homeButton.isPressed())
        {
            isGoToTitle = true;
        }
        else if (editButton.isPressed())
        {
            isGoToEdit = true;
        }

        counter++;
    }

    /**
     * ステップごとの描画処理.
     */
    @Override
    public void render(GameContainer gc, Graphics g, ImageManager im, FontManager fm)
            throws SlickException
    {
        g.setBackground(new Color(189f / 256, 226f / 256, 14f / 256));
        switch (state)
        {
            case STAGE_TITLE:
                playMessage.renderStageNum(g, stageNum, counter, fm);
                objectPool.score.render(g, im);
                break;
            case PLAY:
            case KEEP:
                objectPool.render(g, im);
                break;
            case GAME_OVER:
                playMessage.renderGameOver(g, counter, fm);
                break;
            case GAME_CLEAR:
                playMessage.renderGameClear(g, counter, fm);
                break;
        }
        homeButton.render(im.getHomeIcon());
        editButton.render(im.getEditIcon());
    }

    public void finish()
    {
        bgm.pause();
    }

    public int getStageNum()
    {
        return stageNum;
    }

    public boolean isGoToTitle()
    {
        return isGoToTitle;
    }

    public boolean isGoToEdit()
    {
        return isGoToEdit;
    }
}