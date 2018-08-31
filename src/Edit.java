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

    public void initStage(int stageNum, int cameraX, int cameraY)
    {
        if (stageData.loadStageDate(stageNum))
        {
            this.stageNum = stageNum;
        }
        objectPool.init();
        objectPool.camera.init(cameraX, cameraY);
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

        //System.out.println(objectPool.wire.jointLockedNum);
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
                            int jointLockRadius = ((int) (objectPool.joints[objectPool.wire.jointLockedNum].getDiX() - gc.getInput().getMouseX()))
                                    / Ground.WIDTH * Ground.WIDTH;
                            Joint joint = objectPool.joints[objectPool.wire.jointLockedNum];
                            stageData.resetJointRadius((int) joint.abX, (int) joint.abY, jointLockRadius);
                        }
                        break;
                    case OPERATE:
                        mousePointer.setType(stageData.deleteObject((int) mousePointer.abX, (int) mousePointer.abY, objectPool));
                        mousePointer.canPutObject = false;
                        break;
                }
                objectPool.init();
            }
        }
        else
        {
            if (!mousePointer.canPutObject)
            {
                mousePointer.canPutObject = true;
                addStageData();
                mousePointer.setType(MousePointer.Type.OPERATE);
                objectPool.init();
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
                    if (!stageData.loadStageDate(stageNum))
                    {
                        stageData.createNewStage(stageNum);
                    }
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
        public void render (GameContainer gc, Graphics g, ImageManager im)
            throws SlickException
        {
            grid.render(g, im);
            objectPool.render(g, im);
            mousePointer.render(g, im);
            g.setColor(Color.black);
            g.drawString("stage" + (stageNum + 1), 100, 100);
        }
    }
