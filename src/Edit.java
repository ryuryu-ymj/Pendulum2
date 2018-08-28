import org.newdawn.slick.*;

/**
 * 編集画面の更新,描画を行うクラス.
 */
public class Edit extends GameState
{
    /**
     * フレームカウンタ
     */
    public static int counter;
    ObjectPoolEditVer objectPool;
    StageData stageData;
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
        objectPool.create(objectPool);
        stageData = new StageData();
        grid = new Grid(objectPool);
        mousePointer = new MousePointer(objectPool);
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
        objectPool.collisionDetection(gc);
        grid.update(gc, objectPool.camera.getX(), objectPool.camera.getY());
        mousePointer.setPointer(grid.getGridCenterAbX(gc.getInput().getMouseX()), grid.getGridCenterAbY(gc.getInput().getMouseY()));
        mousePointer.update(gc, objectPool.camera.getX(), objectPool.camera.getY());

        if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
        {
            if (mousePointer.isCanPutObject())
            {
                switch (mousePointer.type)
                {
                    case GROUND:
                        stageData.addGround((int) mousePointer.abX, (int) mousePointer.abY, mousePointer.ground.getType());
                        break;
                    case JOINT:
                        stageData.addJoint((int) mousePointer.abX, (int) mousePointer.abY, mousePointer.joint.getType(),
                                mousePointer.joint.getLockRadius());
                        break;
                    case BACK_OBJECT:
                        stageData.addBackObject((int) mousePointer.abX, (int) mousePointer.abY, mousePointer.backObject.getType()
                                , mousePointer.backObject.getLayer());
                        break;
                    case CHERRY:
                        stageData.addCherry((int) mousePointer.abX, (int) mousePointer.abY);
                        break;
                    case HEART:
                        stageData.addHeart((int) mousePointer.abX, (int) mousePointer.abY);
                        break;
                    case DELETE:
                        stageData.deleteObject((int) mousePointer.abX, (int) mousePointer.abY);
                        break;
                }
                objectPool.init();
            }

            mousePointer.canPutObject = true;
            if (objectPool.wire.jointLockedNum != -1)
            {
                mousePointer.canPutObject = false;
                int jointLockRadius = ((int) (objectPool.joints[objectPool.wire.jointLockedNum].getDiX() - gc.getInput().getMouseX()))
                        / Ground.WIDTH * Ground.WIDTH;
                Joint joint = objectPool.joints[objectPool.wire.jointLockedNum];
                joint.activate((int) joint.abX, (int) joint.abY, joint.getType(), jointLockRadius,
                        false, 0);
            }
        }

        if (gc.getInput().isKeyDown(Input.KEY_LCONTROL) || gc.getInput().isKeyDown(Input.KEY_RCONTROL))
        {
            if (gc.getInput().isKeyPressed(Input.KEY_S))
            {
                stageData.saveStageDate(stageNum);
                System.out.println("ステージデータをstage" + (stageNum + 1) + "を保存しました");
            }
            else if (gc.getInput().isKeyPressed(Input.KEY_DELETE))
            {
                stageData.deleteAllObject();
                objectPool.init();
            }
            for (int i = 0; i < StageData.STAGE_MAX; i++)
            {
                if (gc.getInput().isKeyPressed(2 + i) || gc.getInput().isKeyPressed(79 + i))
                {
                    stageNum = i;
                    stageData.loadStageDate(stageNum);
                    objectPool.init();
                }
            }
        }

        objectPool.moveGrounds(stageData.getGroundXs(), stageData.getGroundYs(),
                stageData.getGroundTypes(), stageData.getGroundPositions());
        objectPool.moveJoints(stageData.getJointXs(), stageData.getJointYs(), stageData.getJointTypes(),
                stageData.getJointLockRadiuses());
        objectPool.moveCherries(stageData.getCherryXs(), stageData.getCherryYs());
        objectPool.moveHearts(stageData.getHeartXs(), stageData.getHeartYs());
        objectPool.moveBackObjects(stageData.getBackObjectXs(), stageData.getBackObjectYs(), stageData.getBackObjectTypes()
                , stageData.getBackObjectLayers());
        objectPool.update(gc);
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
