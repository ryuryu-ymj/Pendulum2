import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

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

    ObjectPool objectPool;
    StageData stageData;

    /**
     * プレイするステージの番号 0から
     */
    public int stageNum;

    /**
     * コンストラクタ
     */
    Play()
    {
        super();
        objectPool = new ObjectPool();
        objectPool.create(objectPool);
        stageData = new StageData();
    }

    /**
     * 初期化処理.
     */
    public void init(GameContainer gc)
            throws SlickException
    {
        stageNum = 0;
        stageData.loadStageDate(stageNum);
        objectPool.init();
    }

    /**
     * ステップごとの更新.
     */
    public void update(GameContainer gc, int delta)
            throws SlickException
    {
        objectPool.moveGrounds(stageData.getGroundXs(), stageData.getGroundYs(), stageData.getGroundTypes(), stageData.getGroundShapes(), stageData.getGroundIsCheckCollisions());
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
        }
        counter++;
    }

    /**
     * ステップごとの描画処理.
     */
    public void render(GameContainer gc, Graphics g, ImageManager im)
            throws SlickException
    {
        objectPool.render(g, im);
    }
}