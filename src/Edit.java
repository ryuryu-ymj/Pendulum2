import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Edit extends GameState
{
    /**
     * フレームカウンタ
     */
    public static int counter;
    ObjectPoolEditVer objectPool;
    StageDate stageDate;
    Grid grid;

    /**
     * プレイするステージの番号 0から
     */
    private int stageNum;

    /**
     * コンストラクタ
     */
    Edit()
    {
        super();
        objectPool = new ObjectPoolEditVer();
        stageDate = new StageDate();
        grid = new Grid();
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
        objectPool.moveGrounds(stageDate.getGroundNum(), stageDate.getGroundXs(), stageDate.getGroundYs(), stageDate.getGroundTypes());
        objectPool.moveJoints(stageDate.getJointNum(), stageDate.getJointXs(), stageDate.getJointYs());
        objectPool.moveBackObjects(stageDate.getBackObjectNum(), stageDate.getBackObjectXs(), stageDate.getBackObjectYs()
                , stageDate.getBackObjectLayers(), stageDate.getBackObjectTypes());
        objectPool.update(gc);
        grid.update(gc, objectPool.camera.getX(), objectPool.camera.getY());
        counter++;
    }

    /**
     * ステップごとの描画処理.
     */
    public void render(GameContainer gc, Graphics g, ImageManager im)
            throws SlickException
    {
        objectPool.render(g, im);
        grid.render(g, im);
    }
}
