import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
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
    MousePointer mousePointer;

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
        mousePointer = new MousePointer();
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
        if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
        {
            switch (mousePointer.type)
            {
                case GROUND:
                    stageDate.addGround((int)mousePointer.abX, (int)mousePointer.abY, Ground.Type.NORMAL);
                    break;
                case JOINT:
                    stageDate.addJoint((int)mousePointer.abX, (int)mousePointer.abY);
                    break;
                case DELETE:
                    stageDate.deleteObject((int)mousePointer.abX, (int)mousePointer.abY);
                    objectPool.reload();
                    break;
            }
        }

        if (gc.getInput().isKeyDown(Input.KEY_LCONTROL) || gc.getInput().isKeyDown(Input.KEY_RCONTROL))
        {
            if (gc.getInput().isKeyPressed(Input.KEY_S))
            {
                stageDate.saveStageDate(stageNum);
                System.out.println("ステージデータをstage" + (stageNum + 1) + "を保存しました");
            }
            else if (gc.getInput().isKeyPressed(Input.KEY_DELETE))
            {
                stageDate.deleteAllObject();
                objectPool.reload();
            }
        }
        else if (gc.getInput().isKeyPressed(Input.KEY_G))
        {
            mousePointer.type = MousePointer.Type.GROUND;
        }
        else if (gc.getInput().isKeyPressed(Input.KEY_J))
        {
            mousePointer.type = MousePointer.Type.JOINT;
        }
        else if (gc.getInput().isKeyPressed(Input.KEY_D))
        {
            mousePointer.type = MousePointer.Type.DELETE;
        }
        objectPool.moveGrounds(stageDate.getGroundXs(), stageDate.getGroundYs(), stageDate.getGroundTypes());
        objectPool.moveJoints(stageDate.getJointXs(), stageDate.getJointYs());
        objectPool.moveBackObjects(stageDate.getBackObjectNum(), stageDate.getBackObjectXs(), stageDate.getBackObjectYs()
                , stageDate.getBackObjectLayers(), stageDate.getBackObjectTypes());
        objectPool.update(gc);
        grid.update(gc, objectPool.camera.getX(), objectPool.camera.getY());
        mousePointer.setPointer(grid.getGridCenterAbX(gc.getInput().getMouseX()), grid.getGridCenterAbY(gc.getInput().getMouseY()));
        mousePointer.update(gc, objectPool.camera.getX(), objectPool.camera.getY());
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
        mousePointer.render(g, im);
    }
}
