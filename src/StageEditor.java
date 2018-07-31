import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class StageEditor extends GameState
{
    /**
     * フレームカウンタ
     */
    public static int counter;

    ObjectPool objectPool;
    StageDate stageDate;

    /**
     * プレイするステージの番号 0から
     */
    private int stageNum;

    /**
     * コンストラクタ
     */
    StageEditor()
    {
        super();
        objectPool = new ObjectPool();
        stageDate = new StageDate();
    }

    /**
     * 初期化処理.
     */
    @Override
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
    @Override
    public void update(GameContainer gc, int delta)
            throws SlickException
    {
        objectPool.moveGrounds(stageDate.getGroundNum(), stageDate.getGroundXs(), stageDate.getGroundYs(), stageDate.getGroundTypes());
        objectPool.moveJoints(stageDate.getJointNum(), stageDate.getJointXs(), stageDate.getJointYs());
        objectPool.moveBackObjects(stageDate.getBackObjectNum(), stageDate.getBackObjectXs(), stageDate.getBackObjectYs()
                , stageDate.getBackObjectLayers(), stageDate.getBackObjectTypes());
        objectPool.collisionDetection(gc);
        objectPool.update(gc);
        counter++;
    }

    /**
     * ステップごとの描画処理.
     */
    @Override
    public void render(GameContainer gc, Graphics g, ImageManager im)
            throws SlickException
    {
        objectPool.render(g, im);
    }
}
