import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * ゲームオブジェクトの抽象クラス.
 */
public abstract class GameObject
{
    /**
     * インスタンス有効フラグ(falseならインスタンスは処理されない)
     */
    public boolean active;

    /**
     * ステージにおける中心点のx座標
     */
    public float abX;

    /**
     * ステージにおける中心点のy座標
     */
    public float abY;

    /**
     * 横幅
     */
    public float width;

    /**
     * 縦幅
     */
    public float height;

    /**
     * 画面に表示する中心点のx座標
     */
    private float diX;

    /**
     * 画面に表示する中心点のy座標
     */
    private float diY;

    /**
     * ObjectPoolクラスのインスタンス
     */
    public ObjectPool objectPool;

    GameObject(ObjectPool objectPool)
    {
        this.objectPool = objectPool;
    }

    /**
     * ステップごとの更新.
     */
    public abstract void update(GameContainer gc, float cameraX, float cameraY);

    /**
     * ステップごとの描画処理.
     */
    public abstract void render(Graphics g, ImageManager im);

    /**
     * 絶対座標を画面座標に変換する
     *
     * @param cameraX カメラ（画面）の中心座標
     * @param cameraY カメラ（画面）の中心座標
     */
    public void changeToDisplayPoint(float cameraX, float cameraY)
    {
        changeToDisplayPoint(cameraX, cameraY, 1);
    }

    /**
     * 絶対座標を画面座標に変換する
     *
     * @param cameraX カメラ（画面）の中心座標
     * @param cameraY カメラ（画面）の中心座標
     * @param pace    x軸方向の進み具合<br>
     *                LAYER0に対しての割合
     */
    public void changeToDisplayPoint(float cameraX, float cameraY, float pace)
    {
        diX = abX - cameraX * pace + Play.DISPLAY_WIDTH / 2;
        diY = abY - cameraY + Play.DISPLAY_HEIGHT / 2;
    }

    /**
     * オブジェクトがプレイ領域内にいるかどうかを返す
     *
     * @param margin 余裕
     */
    public boolean checkLeaving(int margin)
    {
        return (diX < -width / 2 - margin
                || diX > Play.DISPLAY_WIDTH + width / 2 + margin
                || diY < -height / 2 - margin
                || diY > Play.DISPLAY_HEIGHT + height / 2 + margin);
    }

    public float getDiX()
    {
        return diX;
    }

    public float getDiY()
    {
        return diY;
    }
}