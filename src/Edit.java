import org.newdawn.slick.*;

/**
 * 編集画面の更新,描画を行うクラス.
 *
 * @author ryuryu
 */
public class Edit extends GameState
{
    private ObjectPoolEditVer objectPool;
    private StageData stageData;
    private Grid grid;
    private MousePointer mousePointer;
    private ToolBar toolBar;

    /**
     * プレイするステージの番号 0から
     */
    private int stageNum;
    private boolean isGoToPlay;
    /**
     * フレームカウンタ
     */
    public static int counter;

    public int getStageNum()
    {
        return stageNum;
    }

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
        toolBar = new ToolBar();
    }

    /**
     * 初期化処理.
     */
    public void init(GameContainer gc)
            throws SlickException
    {
        initStage(gc, 0, 0, 0);
    }

    public void initStage(GameContainer gc, int stageNum, int cameraX, int cameraY)
    {
        if (stageData.loadStageDate(stageNum))
        {
            this.stageNum = stageNum;
        }
        objectPool.init();
        objectPool.camera.init(cameraX, cameraY);
        isGoToPlay = false;
        toolBar.init();
        mousePointer.init();
        grid.update(gc, cameraX, cameraY);
    }

    /**
     * ステップごとの更新.
     */
    public void update(GameContainer gc, int delta)
            throws SlickException
    {
        if (gc.getInput().getMouseY() < 80)
        {
            toolBar.update(gc, mousePointer);

            if (toolBar.isStageDataSave())
            {
                stageData.saveStageDate(stageNum);
                System.out.println("ステージデータをstage" + (stageNum + 1) + "を保存しました");
                isGoToPlay = true;
            }

            if (toolBar.isAddStageNum())
            {
                stageNum++;
                if (!stageData.loadStageDate(stageNum))
                {
                    stageData.createNewStage(stageNum);
                    objectPool.camera.init(0, 0);
                }
                objectPool.init();
            }
            else if (toolBar.isSubStageNum())
            {
                initStage(gc, stageNum - 1, 0, 0);
                objectPool.init();
            }
        }
        else
        {
            objectPool.collisionDetection(gc);
            mousePointer.setPointer(grid.getGridCenterAbX(gc.getInput().getMouseX()), grid.getGridCenterAbY(gc.getInput().getMouseY()));
            mousePointer.update(gc, objectPool.camera.getX(), objectPool.camera.getY());

            if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
            {
                if (mousePointer.canPutObject)
                {
                    addStageData();
                    switch (mousePointer.getType())
                    {
                        case DELETE:
                            stageData.deleteObject((int) mousePointer.abX, (int) mousePointer.abY, objectPool);
                            break;
                        case JOINT_LOCK_RADIUS:
                            if (objectPool.wire.jointLockedNum != -1)
                            {
                                int jointLockRadius = Math.abs((int) (objectPool.joints[objectPool.wire.jointLockedNum].getDiX() - gc.getInput().getMouseX()))
                                        / Ground.WIDTH * Ground.WIDTH;
                                Joint joint = objectPool.joints[objectPool.wire.jointLockedNum];
                                stageData.resetJointRadius((int) joint.abX, (int) joint.abY, jointLockRadius);
                            }
                            break;
                        case OPERATE:
                            mousePointer.setType(stageData.deleteObject((int) mousePointer.abX, (int) mousePointer.abY, objectPool));
                            mousePointer.isDragging = true;
                            break;
                    }
                    objectPool.init();
                }
            }
            else
            {
                if (mousePointer.isDragging)
                {
                    mousePointer.isDragging = false;
                    addStageData();
                    mousePointer.setType(MousePointer.Type.OPERATE);
                    objectPool.init();
                }
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
                if (gc.getInput().isKeyPressed(2 + i))
                {
                    stageNum = i;
                    if (!stageData.loadStageDate(stageNum))
                    {
                        stageData.createNewStage(stageNum);
                    }
                    objectPool.init();
                }
            }
        }

        grid.update(gc, objectPool.camera.getX(), objectPool.camera.getY());
        objectPool.moveGrounds(stageData.getGroundXs(), stageData.getGroundYs(),
                stageData.getGroundTypes(), stageData.getGroundPositions());
        objectPool.moveJoints(stageData.getJointXs(), stageData.getJointYs(), stageData.getJointTypes(),
                stageData.getJointLockRadiuses());
        objectPool.moveCherries(stageData.getCherryXs(), stageData.getCherryYs());
        objectPool.moveHearts(stageData.getHeartXs(), stageData.getHeartYs());
        objectPool.moveBackObjects(stageData.getBackObjectXs(), stageData.getBackObjectYs(), stageData.getBackObjectTypes()
                , stageData.getBackObjectLayers());
        objectPool.update(gc);

        for (Joint joint : objectPool.joints)
        {
            System.out.print("(" + joint.active + " " + joint.getDiX() + " " + joint.getDiY() + ") ");
        }
        System.out.println();

        counter++;
    }

    private void addStageData()
    {
        switch (mousePointer.getType())
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
        }
    }

    /**
     * ステップごとの描画処理.
     */
    @Override
    public void render(GameContainer gc, Graphics g, ImageManager im, FontManager fm)
            throws SlickException
    {
        grid.render(g, im);
        objectPool.render(g, im);
        mousePointer.render(g, im);
        toolBar.render(g, im, fm, stageNum);
        g.setColor(Color.black);
        g.drawString("stage" + (stageNum + 1), 100, 100);
    }

    public boolean isGoToPlay()
    {
        return isGoToPlay;
    }
}
