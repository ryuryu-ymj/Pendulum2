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
                case GROUND_NORMAL:
                    stageDate.addGround((int)mousePointer.abX, (int)mousePointer.abY, Ground.Type.NORMAL);
                    break;
                case GROUND_INVISIBLE:
                    stageDate.addGround((int)mousePointer.abX, (int)mousePointer.abY, Ground.Type.INVISIBLE);
                    break;
                case JOINT_NORMAL:
                    stageDate.addJoint((int)mousePointer.abX, (int)mousePointer.abY, Joint.Type.NORMAL);
                    break;
                case JOINT_GOAL:
                    stageDate.addJoint((int)mousePointer.abX, (int)mousePointer.abY, Joint.Type.GOAL);
                    break;
                case JOINT_BEE:
                    stageDate.addJoint((int)mousePointer.abX, (int)mousePointer.abY, Joint.Type.BEE);
                    break;
                case GLASS1:
                    stageDate.addBackObject((int)mousePointer.abX, (int)mousePointer.abY, BackObject.Type.GLASS1, BackObject.Layer.LAYER0);
                    break;
                case GLASS2:
                    stageDate.addBackObject((int)mousePointer.abX, (int)mousePointer.abY, BackObject.Type.GLASS2, BackObject.Layer.LAYER0);
                    break;
                case GLASS3:
                    stageDate.addBackObject((int)mousePointer.abX, (int)mousePointer.abY, BackObject.Type.GLASS3, BackObject.Layer.LAYER0);
                    break;
                case GLASS4:
                    stageDate.addBackObject((int)mousePointer.abX, (int)mousePointer.abY, BackObject.Type.GLASS4, BackObject.Layer.LAYER0);
                    break;
                case CHERRY:
                    stageDate.addCherry((int)mousePointer.abX, (int)mousePointer.abY);
                    break;
                case HEART:
                    stageDate.addHeart((int)mousePointer.abX, (int)mousePointer.abY);
                    break;
                case DELETE:
                    stageDate.deleteObject((int)mousePointer.abX, (int)mousePointer.abY);
                    break;
            }
            objectPool.init();
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
                objectPool.init();
            }
            for (int i = 0; i < StageDate.STAGE_MAX; i++)
            {
                if (gc.getInput().isKeyPressed(2 + i) || gc.getInput().isKeyPressed(79 + i))
                {
                    stageNum = i;
                    stageDate.loadStageDate(stageNum);
                    objectPool.init();
                }
            }
        }

        objectPool.moveGrounds(stageDate.getGroundXs(), stageDate.getGroundYs(), stageDate.getGroundTypes(), stageDate.getGroundShapes(), stageDate.getGroundIsCheckCollisions());
        objectPool.moveJoints(stageDate.getJointXs(), stageDate.getJointYs(), stageDate.getJointTypes());
        objectPool.moveCherries(stageDate.getCherryXs(), stageDate.getCherryYs());
        objectPool.moveHearts(stageDate.getHeartXs(), stageDate.getHeartYs());
        objectPool.moveBackObjects(stageDate.getBackObjectXs(), stageDate.getBackObjectYs(), stageDate.getBackObjectTypes()
                , stageDate.getBackObjectLayers());
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
        grid.render(g, im);
        objectPool.render(g, im);
        mousePointer.render(g, im);
        g.setColor(Color.black);
        g.drawString("stage" + (stageNum + 1), 100, 100);
    }
}
