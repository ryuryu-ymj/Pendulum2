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
    Camera camera;

    /**
     * 画面上におけるgroundの数の最大値
     */
    public static final int GROUND_MAX = 100;
    /**
     * そのgroundが表示されたかどうか
     */
    public static boolean[] isGroundDisplay = new boolean[StageDate.GROUND_MAX];

    /**
     * 画面上におけるjointの数の最大値
     */
    public static final int JOINT_MAX = 10;

    ObjectPool()
    {
        player = new Player(200, 200, 20);
        joints = new Joint[JOINT_MAX];
        for (int i = 0; i < JOINT_MAX; i++)
        {
            joints[i] = new Joint(15);
        }
        grounds = new Ground[GROUND_MAX];
        for (int i = 0; i < grounds.length; i++)
        {
            grounds[i] = new Ground();
        }
        wire = new Wire();
        camera = new Camera();
        init();
    }

    /**
     * 初期化処理.
     */
    public void init()
    {
        player.active = true;
        joints[0].activate(200, 0, 0, 0, true);
        joints[1].activate(-200, 0, 0, 0, true);
        wire.active = false;
        //camera.active = true;
    }

    /**
     * ステップごとの更新.
     */
    public void update(GameContainer gc)
    {
        updateObjects(joints, gc);
        updateObjects(grounds, gc);
        if (player.active)
        {
            player.update(gc, camera.getX(), camera.getY());
            if (wire.jointLockedNum != -1)
            {
                player.pendulum(wire.getAngle(), wire.getStringForce());
            }
        }
        if (wire.active)
        {
            wire.update(gc, player.getDiX(), player.getDiY(), joints[wire.jointLockedNum].getDiX(), joints[wire.jointLockedNum].getDiY());
        }
        if (camera.active)
        {
            camera.update(player.abX, player.abY);
        }
    }

    /**
     * ステップごとの描画処理.
     */
    public void render(Graphics g, ImageManager im)
    {
        //g.setLineWidth(1.5f);

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
     * 新しいgroundを作る
     *
     * @param x    groundのx座標
     * @param y    groundのy座標
     * @param type groundのtype
     * @return groundsの配列番号　なかったら-1
     */
    public int newGround(int x, int y, Ground.Type type, int num)
    {
        for (int i = 0; i < GROUND_MAX; i++)
        {
            if (!grounds[i].active)
            {
                grounds[i].activate(x, y, type, num);
                return i;
            }
        }
        return -1;
    }

    public void moveGrounds(int groundNum, int[] groundXs, int[] groundYs, Ground.Type[] groundTypes)
    {
        for (int i = 0; i < groundNum; i++)
        {
            if (checkEntering(groundXs[i], groundYs[i], (int) grounds[i].width, (int) grounds[i].height))
            {
                if (!isGroundDisplay[i])
                {
                    if (newGround(groundXs[i], groundYs[i], groundTypes[i], i) != -1)
                    {
                        isGroundDisplay[i] = true;
                    }
                    else
                    {
                        System.err.println("groundの数が足りません" + groundXs[i] + " " + groundYs[i] + " " + i);
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
                            if (joints[i].lockRadius == 0 || getDistance(player, joints[i]) < joints[i].lockRadius)
                            {
                                wire.jointLockedNum = i;
                                wire.active = true;
                                camera.active = true;
                            }
                            break f;
                        }
                    }
                }
                if (i == JOINT_MAX - 1)
                {
                    wire.jointLockedNum = -1;
                    wire.active = false;
                    for (int g = 0; g < wire.isPlayerPass.length; g++)
                    {
                        wire.isPlayerPass[g] = false;
                    }
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
    private void updateObjects(GameObject[] object, GameContainer gc)
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
    private void renderObjects(GameObject[] object, Graphics g, ImageManager im)
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
