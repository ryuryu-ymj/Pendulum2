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
    Camera camera;

    /** 画面上におけるjointの数の最大値 */
    public static final int JOINT_MAX = 10;

    ObjectPool()
    {
        player = new Player(0, 0, 20);
        joints = new Joint[JOINT_MAX];
        for (int i = 0; i < JOINT_MAX; i++)
        {
            joints[i] = new Joint(15);
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
        if(player.active)
        {
            player.update(gc, camera.getX(), camera.getY());
            if(wire.jointLockedNum != -1)
            {
                player.pendulum(wire.getAngle(), wire.getStringForce());
            }
        }
        if(wire.active)
        {
            wire.update(gc, player.getDiX(), player.getDiY(), joints[wire.jointLockedNum].getDiX(), joints[wire.jointLockedNum].getDiY());
        }
        if(camera.active)
        {
            camera.update(player.abX, player.abY);
        }
    }

    /**
     * ステップごとの描画処理.
     */
    public void render(Graphics g, ImageManager im)
    {
        g.setLineWidth(1.5f);

        renderObjects(joints, g, im);
        if(player.active)
        {
            player.render(g, im);
        }
        if(wire.active)
        {
            wire.render(g, im);
        }
    }

    /**
     * 衝突判定
     */
    public void collisionDetection(GameContainer gc)
    {
        f :
        if(gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON))
        {
            for (int i = 0; i < JOINT_MAX; i++)
            {
                if (joints[i].active && player.active)
                {
                    if (gc.getInput().getMouseX() < joints[i].getDiX() + joints[i].radius * 5 && gc.getInput().getMouseX() > joints[i].getDiX() - joints[i].radius * 5)
                    {
                        if (gc.getInput().getMouseY() < joints[i].getDiY() + joints[i].radius * 5 && gc.getInput().getMouseY() > joints[i].getDiY() - joints[i].radius * 5)
                        {
                            if(joints[i].lockRadius == 0 || getDistance(player, joints[i]) < joints[i].lockRadius)
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
                    for(int g = 0; g < wire.isPlayerPass.length; g++)
                    {
                        wire.isPlayerPass[g] = false;
                    }
                    //camera.active = false;
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
     * @param ga ゲームオブジェクト
     * @param gb 比較先ゲームオブジェクト
     * @return 距離
     */
    public double getDistance(GameObject ga, GameObject gb)
    {
        //三平方の定理
        double Xdiff = Math.abs(ga.abX - gb.abX);
        double Ydiff = Math.abs(ga.abY - gb.abY);
        return Math.sqrt(Math.pow(Xdiff,2) + Math.pow(Ydiff,2));
    }
}
