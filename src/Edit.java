import org.newdawn.slick.*;

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
                case JOINT_NORMAL:
                    stageDate.addJoint((int)mousePointer.abX, (int)mousePointer.abY, Joint.Type.NORMAL);
                    break;
                case JOINT_GOAL:
                    stageDate.addJoint((int)mousePointer.abX, (int)mousePointer.abY, Joint.Type.GOAL);
                    break;
                case DELETE:
                    stageDate.deleteObject((int)mousePointer.abX, (int)mousePointer.abY);
                    objectPool.reload();
                    break;
            }
            objectPool.reload();
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
            for (int i = 0; i < StageDate.STAGE_MAX; i++)
            {
                if (gc.getInput().isKeyPressed(2 + i) || gc.getInput().isKeyPressed(79 + i))
                {
                    stageNum = i;
                    stageDate.loadStageDate(stageNum);
                    objectPool.reload();
                }
            }
        }

        objectPool.moveGrounds(stageDate.getGroundXs(), stageDate.getGroundYs(), stageDate.getGroundTypes(), stageDate.getGroundShapes());
        objectPool.moveJoints(stageDate.getJointXs(), stageDate.getJointYs(), stageDate.getJointTypes());
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
        g.setColor(Color.black);
        g.drawString("stage" + (stageNum + 1), 100, 100);
    }
}
