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
    StageDate stageDate;

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
        stageDate = new StageDate();
    }

    /**
     * 初期化処理.
     */
    public void init(GameContainer gc)
            throws SlickException
    {
        stageNum = 0;
        stageDate.loadStageDate(stageNum);
        objectPool.init();
    }

    /**
     * ステップごとの更新.
     */
    public void update(GameContainer gc, int delta)
            throws SlickException
    {
        objectPool.moveGrounds(stageDate.getGroundXs(), stageDate.getGroundYs(), stageDate.getGroundTypes(), stageDate.getGroundShapes(), stageDate.getGroundIsCheckCollisions());
        objectPool.moveJoints(stageDate.getJointXs(), stageDate.getJointYs(), stageDate.getJointTypes());
        objectPool.moveCherries(stageDate.getCherryXs(), stageDate.getCherryYs());
        objectPool.moveBackObjects(stageDate.getBackObjectXs(), stageDate.getBackObjectYs(), stageDate.getBackObjectTypes()
                , stageDate.getBackObjectLayers());
        objectPool.collisionDetection(gc);
        objectPool.update(gc);
        if (objectPool.isPlayerGoal())
        {
            objectPool.init();
            stageDate.loadStageDate(++stageNum);
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