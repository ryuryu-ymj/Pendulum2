import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/**
 * ゲームオブジェクトの管理クラス.
 * オブジェクトのインスタンスを持ち,
 * オブジェクト同士の相互作用(衝突処理など)を一括管理する.
 */
public class ObjectPool
{
    Player player;
    Joint[] joints;
    Wire wire;
    Ground[] grounds;
    BackObject[] backObjects;
    Camera camera;

    /**
     * 画面上における ground の数の最大値
     */
    public static final int GROUND_MAX = 100;
    /**
     * その ground が表示されたかどうか
     */
    public static boolean[] isGroundDisplayed = new boolean[StageDate.GROUND_MAX];

    /**
     * 画面上における joint の数の最大値
     */
    public static final int JOINT_MAX = 10;
    /**
     * その joint が表示されたかどうか
     */
    static boolean[] isJointDisplayed = new boolean[StageDate.JOINT_MAX];

    /**
     * 画面上における backObject の数の最大値
     */
    public static final int BACK_OBJECT_MAX = 20;
    /**
     * その backObject が表示されたかどうか
     */
    static boolean[] isBackObjectDisplayed = new boolean[StageDate.BACK_OBJECT_MAX];

    ObjectPool()
    {
        player = new Player(200, 200);
        wire = new Wire();
        joints = new Joint[JOINT_MAX];
        for (int i = 0; i < JOINT_MAX; i++)
        {
            joints[i] = new Joint();
        }
        grounds = new Ground[GROUND_MAX];
        for (int i = 0; i < grounds.length; i++)
        {
            grounds[i] = new Ground();
        }
        backObjects = new BackObject[BACK_OBJECT_MAX];
        for (int i = 0; i < backObjects.length; i++)
        {
            backObjects[i] = new BackObject();
        }
        camera = new Camera();
        init();
    }

    /**
     * 初期化処理.
     */
    public void init()
    {
        player.init(200, 200);
        camera.init(200, 200);
        wire.init();
        for (int i = 0; i < isJointDisplayed.length; i++)
        {
            isJointDisplayed[i] = false;
        }
        for (int i = 0; i < joints.length; i++)
        {
            joints[i].active = false;
        }
        for (int i = 0; i < isGroundDisplayed.length; i++)
        {
            isGroundDisplayed[i] = false;
        }
        for (int i = 0; i < grounds.length; i++)
        {
            grounds[i].active = false;
        }
        for (int i = 0; i < isBackObjectDisplayed.length; i++)
        {
            isBackObjectDisplayed[i] = false;
        }
        for (int i = 0; i < backObjects.length; i++)
        {
            backObjects[i].active = false;
        }
    }

    /**
     * ステップごとの更新.
     */
    public void update(GameContainer gc)
    {
        updateObjects(backObjects, gc);
        updateObjects(joints, gc);
        updateObjects(grounds, gc);
        if (player.active)
        {
            player.update(gc, camera.getX(), camera.getY());
            if (wire.jointLockedNum != -1)
            {
                player.pendulum(wire.getAngle(), wire.getStringForce());
                //System.out.println(wire.getAngle());
            }
        }
        if (wire.active)
        {
            wire.update(gc, player.getDiX(), player.getDiY(), joints[wire.jointLockedNum].getDiX(), joints[wire.jointLockedNum].getDiY());
        }

        if (camera.active)
        {
            camera.followPlayer(player.abX, player.abY);
        }
    }

    /**
     * ステップごとの描画処理.
     */
    public void render(Graphics g, ImageManager im)
    {
        //g.setLineWidth(1.5f);

        //im.drawBackGround(Play.DISPLAY_WIDTH / 2, Play.DISPLAY_HEIGHT / 2, 4900 / 2, 1800 / 2);
        renderObjects(backObjects, g, im);
        renderObjects(joints, g, im);
        renderObjects(grounds, g, im);
        if (player.active)
        {
            player.render(g, im);
        }
        if (wire.active)
        {
            wire.render(g, im);
        }
    }

    /**
     * 新しく ground をactivateする
     *
     * @param x    ground のx座標
     * @param y    ground のy座標
     * @param type ground のtype
     * @return grounds の配列番号　なかったら-1
     */
    public int newGround(int x, int y, Ground.Type type, Ground.Shape shape, int num)
    {
        for (int i = 0; i < GROUND_MAX; i++)
        {
            if (!grounds[i].active)
            {
                grounds[i].activate(x, y, type, shape, num);
                return i;
            }
        }
        return -1;
    }

    /**
     * 画面内にある grounds を newGround する
     *
     * @param groundXs    ground の絶対座標（空の場合は-1）
     * @param groundYs    ground の絶対座標（空の場合は-1）
     * @param groundTypes ground の型
     */
    public void moveGrounds(int[] groundXs, int[] groundYs, Ground.Type[] groundTypes, Ground.Shape[] groundShapes)
    {
        for (int i = 0; i < groundXs.length; i++)
        {
            if (checkEntering(groundXs[i], groundYs[i], (int) grounds[0].width, (int) grounds[0].height))
            {
                if (!isGroundDisplayed[i])
                {
                    if (newGround(groundXs[i], groundYs[i], groundTypes[i], groundShapes[i], i) != -1)
                    {
                        isGroundDisplayed[i] = true;
                    }
                    else
                    {
                        System.err.println("ground の数が足りません" + groundXs[i] + " " + groundYs[i] + " " + i);
                    }
                }
            }
        }
    }

    /**
     * 新しく joint をactivateする
     *
     * @param x          joint のx座標
     * @param y          joint のy座標
     * @param type       joint のtype
     * @param lockRadius joint のlockRadius
     * @param num        joint がステージ上のどの joint を演じているのか（stageDate.jointXsの配列番号）
     * @return joints の配列番号　なかったら-1
     */
    public int newJoint(int x, int y, Joint.Type type, int lockRadius, int num)
    {
        for (int i = 0; i < JOINT_MAX; i++)
        {
            if (!joints[i].active)
            {
                joints[i].activate(x, y, type, lockRadius, num);
                return i;
            }
        }
        return -1;
    }

    /**
     * 画面内にある joints を newJoint する
     *
     * @param jointXs  ground の絶対座標（空の場合は-1）
     * @param jointYs  ground の絶対座標（空の場合は-1）
     *                 //* @param jointTypes ground の型
     */
    public void moveJoints(int[] jointXs, int[] jointYs, Joint.Type[] jointTypes)
    {
        for (int i = 0; i < jointXs.length; i++)
        {
            if (!isJointDisplayed[i])
            {
                if (checkEntering(jointXs[i], jointYs[i], joints[0].radius, joints[0].radius))
                {
                    if (newJoint(jointXs[i], jointYs[i], jointTypes[i], 0, i) != -1)
                    {
                        isJointDisplayed[i] = true;
                    }
                    else
                    {
                        System.err.println("joint の数が足りません" + jointXs[i] + " " + jointYs[i] + " " + i);
                    }
                }
            }
        }
    }

    /**
     * 新しく backObject をactivateする
     *
     * @param x    backObject のx座標
     * @param y    backObject のy座標
     * @param type backObject のtype
     * @param num  backObject がステージ上のどの backObject を演じているのか（stageDate.jointXsの配列番号）
     * @return backObjects の配列番号　なかったら-1
     */
    public int newBackObject(int x, int y, BackObject.Layer layer, BackObject.Type type, int num)
    {
        for (int i = 0; i < BACK_OBJECT_MAX; i++)
        {
            if (!backObjects[i].active)
            {
                backObjects[i].activate(x, y, layer, type, num);
                return i;
            }
        }
        return -1;
    }

    /**
     * 画面内にある backObject を newBackObject する
     *
     * @param backObjectNum   1ステージにある backObject の数
     * @param backObjectXs    backObject の絶対座標（空の場合は-1）
     * @param backObjectYs    backObject の絶対座標（空の場合は-1）
     * @param backObjectTypes backObject の型
     */
    public void moveBackObjects(int backObjectNum, int[] backObjectXs, int[] backObjectYs, BackObject.Layer[] backObjectLayers, BackObject.Type[] backObjectTypes)
    {
        for (int i = 0; i < backObjectNum; i++)
        {
            if (!isBackObjectDisplayed[i])
            {
                if (checkEntering(backObjectXs[i], backObjectYs[i], backObjectTypes[i].WIDTH, backObjectTypes[i].HEIGHT))
                {
                    if (newBackObject(backObjectXs[i], backObjectYs[i], backObjectLayers[i], backObjectTypes[i], i) != -1)
                    {
                        isBackObjectDisplayed[i] = true;
                    }
                    else
                    {
                        System.err.println("backObject の数が足りません" + backObjectXs[i] + " " + backObjectYs[i] + " " + i);
                    }
                }
            }
        }
    }

    /**
     * 衝突判定
     */
    public void collisionDetection(GameContainer gc)
    {
        f:
        if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON))
        {
            for (int i = 0; i < JOINT_MAX; i++)
            {
                if (joints[i].active && player.active)
                {
                    if (gc.getInput().getMouseX() < joints[i].getDiX() + joints[i].radius * 5 && gc.getInput().getMouseX() > joints[i].getDiX() - joints[i].radius * 5)
                    {
                        if (gc.getInput().getMouseY() < joints[i].getDiY() + joints[i].radius * 5 && gc.getInput().getMouseY() > joints[i].getDiY() - joints[i].radius * 5)
                        {
                            if (joints[i].getLockRadius() == 0 || getDistance(player, joints[i]) < joints[i].getLockRadius())
                            {
                                wire.jointLockedNum = i;
                                wire.active = true;
                            }
                            break f;
                        }
                    }
                }
                if (i == JOINT_MAX - 1)
                {
                    wire.init();
                    //camera.active = false;
                }
            }
        }

        // playerとgroundの衝突
        for (Ground ground : grounds)
        {
            check:
            if (ground.active)
            {
                if (ground.isCheckCollision())
                if (player.abX > ground.abX - ground.width / 2
                        && player.abX < ground.abX + ground.width / 2
                        && player.abY + player.height / 2 > ground.abY - ground.height / 2
                        && player.abY - player.height / 2 < ground.abY + ground.height / 2)
                {
                    if (player.abY > ground.abY)
                    {
                        player.boundDown(ground.abY + ground.height / 2);
                    }
                    else
                    {
                        player.boundUp(ground.abY - ground.height / 2);
                    }
                }
                else if (player.abX + player.height / 2 > ground.abX - ground.width / 2
                        && player.abX - player.height / 2 < ground.abX + ground.width / 2
                        && player.abY > ground.abY - ground.height / 2
                        && player.abY < ground.abY + ground.height / 2)
                {
                    if (player.abX > ground.abX)
                    {
                        player.boundRight(ground.abX + ground.width / 2);
                    }
                    else
                    {
                        player.boundLeft(ground.abX - ground.width / 2);
                    }
                }
            }
        }
    }

    /**
     *
     * @return playerがゴールしたかどうか
     */
    public boolean isPlayerGoal()
    {
        if (wire.jointLockedNum != -1)
        {
            return joints[wire.jointLockedNum].getType() == Joint.Type.GOAL;
        }
        return false;
    }

    /**
     * 配列内のすべてのインスタンスを無効にする.
     *
     * @param object ゲームオブジェクトの配列
     */
    private void deactivateObjects(GameObject[] object)
    {
        for (GameObject obj : object)
        {
            obj.active = false;
        }
    }

    /**
     * 配列内のインスタンスのうち,有効な物のみを更新する.
     *
     * @param object ゲームオブジェクトの配列
     */
    protected void updateObjects(GameObject[] object, GameContainer gc)
    {
        for (GameObject obj : object)
        {
            if (obj.active)
            {
                obj.update(gc, camera.getX(), camera.getY());
            }
        }
    }

    /**
     * 配列内のインスタンスのうち,有効な物のみを描画する.
     *
     * @param object ゲームオブジェクトの配列
     */
    protected void renderObjects(GameObject[] object, Graphics g, ImageManager im)
    {
        for (GameObject obj : object)
        {
            if (obj.active)
            {
                obj.render(g, im);
            }
        }
    }

    /**
     * ２点間の距離を返す
     *
     * @param ga ゲームオブジェクト
     * @param gb 比較先ゲームオブジェクト
     * @return 距離
     */
    public double getDistance(GameObject ga, GameObject gb)
    {
        //三平方の定理
        double Xdiff = Math.abs(ga.abX - gb.abX);
        double Ydiff = Math.abs(ga.abY - gb.abY);
        return Math.sqrt(Math.pow(Xdiff, 2) + Math.pow(Ydiff, 2));
    }

    /**
     * オブジェクトが画面内に存在するかの判定
     *
     * @param x      オブジェクトの中心点のx座標
     * @param y      オブジェクトの中心点のy座標
     * @param width  オブジェクトの横幅
     * @param height オブジェクトの縦幅
     * @return オブジェクトが画面内に存在するか
     */
    public boolean checkEntering(int x, int y, int width, int height)
    {
        if (x + width / 2 > camera.getX() - Play.DISPLAY_WIDTH / 2
                && x - width / 2 < camera.getX() + Play.DISPLAY_WIDTH / 2
                && y + height / 2 > camera.getY() - Play.DISPLAY_HEIGHT / 2
                && y - height / 2 < camera.getY() + Play.DISPLAY_HEIGHT / 2)
            return true;
        return false;
    }
}
