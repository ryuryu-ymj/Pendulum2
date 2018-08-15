import org.newdawn.slick.Color;
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
    Cherry[] cherries;
    Camera camera;
    Score score;

    /**
     * 画面上における ground の数の最大値
     */
    public static final int GROUND_MAX = 350;
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

    /**
     * 画面上における cherry の数の最大値
     */
    public static final int CHERRY_MAX = 20;
    /**
     * その cherry が表示されたかどうか
     */
    static boolean[] isCherryDisplayed = new boolean[StageDate.CHERRY_MAX];
    /**
     * その cherry が取られたかどうか
     */
    private boolean[] isCherryTook = new boolean[StageDate.CHERRY_MAX];

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
        cherries = new Cherry[CHERRY_MAX];
        for (int i = 0; i < cherries.length; i++)
        {
            cherries[i] = new Cherry();
        }
        camera = new Camera();
        score = new Score();
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
        score.initScore();
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
        for (int i = 0; i < isCherryDisplayed.length; i++)
        {
            isCherryDisplayed[i] = false;
        }
        for (int i = 0; i < isCherryTook.length; i++)
        {
            isCherryTook[i] = false;
        }
        for (int i = 0; i < cherries.length; i++)
        {
            cherries[i].active = false;
        }
    }

    /**
     * ステップごとの更新.
     */
    public void update(GameContainer gc)
    {
        updateObjects(backObjects, gc);
        updateObjects(joints, gc);
        updateObjects(cherries, gc);
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
        g.setColor(new Color(189, 226, 14));
        g.fillRect(0, 0, Play.DISPLAY_WIDTH, Play.DISPLAY_HEIGHT);
        renderObjects(backObjects, g, im);
        renderObjects(joints, g, im);
        renderObjects(cherries, g, im);
        renderObjects(grounds, g, im);
        if (wire.active)
        {
            wire.render(g, im);
        }
        if (player.active)
        {
            player.render(g, im);
        }
        score.render(g, im);
    }

    /**
     * 新しく ground をactivateする
     *
     * @param x    ground のx座標
     * @param y    ground のy座標
     * @param type ground のtype
     * @return grounds の配列番号　なかったら-1
     */
    public int newGround(int x, int y, Ground.Type type, Ground.Shape shape, boolean isCheckCollision, int num)
    {
        for (int i = 0; i < GROUND_MAX; i++)
        {
            if (!grounds[i].active)
            {
                grounds[i].activate(x, y, type, shape, isCheckCollision, num);
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
    public void moveGrounds(int[] groundXs, int[] groundYs, Ground.Type[] groundTypes, Ground.Shape[] groundShapes, boolean[] groundIsCheckCollisions)
    {
        for (int i = 0; i < groundXs.length; i++)
        {
            if (checkEntering(groundXs[i], groundYs[i], (int) grounds[0].width, (int) grounds[0].height, 60))
            {
                if (!isGroundDisplayed[i])
                {
                    if (newGround(groundXs[i], groundYs[i], groundTypes[i], groundShapes[i], groundIsCheckCollisions[i], i) != -1)
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
     * @param jointXs ground の絶対座標（空の場合は-1）
     * @param jointYs ground の絶対座標（空の場合は-1）
     *                //* @param jointTypes ground の型
     */
    public void moveJoints(int[] jointXs, int[] jointYs, Joint.Type[] jointTypes)
    {
        for (int i = 0; i < jointXs.length; i++)
        {
            if (!isJointDisplayed[i])
            {
                if (checkEntering(jointXs[i], jointYs[i], joints[0].RADIUS * 2, joints[0].RADIUS * 2, 0))
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
     * 新しく cherry をactivateする
     *
     * @param x          cherry のx座標
     * @param y          cherry のy座標
     * @param num        cherry がステージ上のどの cherry を演じているのか（stageDate.cherryXsの配列番号）
     * @return cherries の配列番号　なかったら-1
     */
    public int newCherry(int x, int y, int num)
    {
        for (int i = 0; i < CHERRY_MAX; i++)
        {
            if (!cherries[i].active)
            {
                cherries[i].activate(x, y, num);
                return i;
            }
        }
        return -1;
    }

    /**
     * 画面内にある cherries を newCherry する
     *
     * @param cherryXs cherry の絶対座標（空の場合は-1）
     * @param cherryYs cherry の絶対座標（空の場合は-1）
     */
    public void moveCherries(int[] cherryXs, int[] cherryYs)
    {
        for (int i = 0; i < cherryXs.length; i++)
        {
            if (!isCherryDisplayed[i] && !isCherryTook[i])
            {
                if (checkEntering(cherryXs[i], cherryYs[i], Cherry.RADIUS * 2, Cherry.RADIUS * 2, 0))
                {
                    if (newCherry(cherryXs[i], cherryYs[i], i) != -1)
                    {
                        isCherryDisplayed[i] = true;
                    }
                    else
                    {
                        System.err.println("cherry の数が足りません" + cherryXs[i] + " " + cherryYs[i] + " " + i);
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
    public int newBackObject(int x, int y, BackObject.Type type, BackObject.Layer layer, int num)
    {
        for (int i = 0; i < BACK_OBJECT_MAX; i++)
        {
            if (!backObjects[i].active)
            {
                backObjects[i].activate(x, y, type, layer, num);
                return i;
            }
        }
        return -1;
    }

    /**
     * 画面内にある backObject を newBackObject する

     * @param backObjectXs    backObject の絶対座標（空の場合は-1）
     * @param backObjectYs    backObject の絶対座標（空の場合は-1）
     * @param backObjectTypes backObject の型
     */
    public void moveBackObjects(int[] backObjectXs, int[] backObjectYs, BackObject.Type[] backObjectTypes, BackObject.Layer[] backObjectLayers)
    {
        for (int i = 0; i < backObjectXs.length; i++)
        {
            if (!isBackObjectDisplayed[i])
            {
                if (checkEntering(backObjectXs[i], backObjectYs[i], backObjectTypes[i].WIDTH, backObjectTypes[i].HEIGHT, 0))
                {
                    if (newBackObject(backObjectXs[i], backObjectYs[i], backObjectTypes[i], backObjectLayers[i], i) != -1)
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
                    if (gc.getInput().getMouseX() < joints[i].getDiX() + joints[i].RADIUS * 5 && gc.getInput().getMouseX() > joints[i].getDiX() - joints[i].RADIUS * 5)
                    {
                        if (gc.getInput().getMouseY() < joints[i].getDiY() + joints[i].RADIUS * 5 && gc.getInput().getMouseY() > joints[i].getDiY() - joints[i].RADIUS * 5)
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
        int bound;
        for (Ground ground : grounds)
        {
            check:
            if (ground.active)
            {
                if (ground.isCheckCollision())
                {
                    if (ground.getType() == Ground.Type.NORMAL)
                    {
                        bound = 1;
                    }
                    else
                    {
                        bound = 0;
                    }
                    if (player.abX > ground.abX - ground.width / 2
                            && player.abX < ground.abX + ground.width / 2
                            && player.abY + player.height / 2 > ground.abY - ground.height / 2
                            && player.abY - player.height / 2 < ground.abY + ground.height / 2)
                    {
                        if (player.abY > ground.abY)
                        {
                            player.boundDown(ground.abY + ground.height / 2, bound);
                        }
                        else
                        {
                            player.boundUp(ground.abY - ground.height / 2, bound);
                        }
                    }
                    else if (player.abX + player.height / 2 > ground.abX - ground.width / 2
                            && player.abX - player.height / 2 < ground.abX + ground.width / 2
                            && player.abY > ground.abY - ground.height / 2
                            && player.abY < ground.abY + ground.height / 2)
                    {
                        if (player.abX > ground.abX)
                        {
                            player.boundRight(ground.abX + ground.width / 2, bound);
                        }
                        else
                        {
                            player.boundLeft(ground.abX - ground.width / 2, bound);
                        }
                    }
                }
            }
        }

        // playerとcherry
        for (Cherry cherry : cherries)
        {
            if (cherry.active)
            {
                if (getDistance(player, cherry) < player.width / 2 + cherry.width / 2)
                {
                    cherry.active = false;
                    isCherryDisplayed[cherry.num] = false;
                    isCherryTook[cherry.num] = true;
                    score.addCherry();
                }
            }
        }
    }

    /**
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
     * @param margin 余裕
     * @return オブジェクトが画面内に存在するか
     */
    public boolean checkEntering(int x, int y, int width, int height, int margin)
    {
        if (x + width / 2 > camera.getX() - Play.DISPLAY_WIDTH / 2 - margin
                && x - width / 2 < camera.getX() + Play.DISPLAY_WIDTH / 2 + margin
                && y + height / 2 > camera.getY() - Play.DISPLAY_HEIGHT / 2 - margin
                && y - height / 2 < camera.getY() + Play.DISPLAY_HEIGHT / 2 + margin)
            return true;
        return false;
    }
}
