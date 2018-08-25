import org.newdawn.slick.GameContainer;

public abstract class GameItem extends GameObject
{
    /**
     * 半径
     */
    public static final int RADIUS = 20;
    /**
     * item がステージ上のどの item を演じているのか（itemX の配列番号）
     */
    public int num;
    private boolean takenMotionFlag;
    private final int TAKEN_X, TAKEN_Y;

    GameItem(int TAKEN_X, int TAKEN_Y, ObjectPool objectPool)
    {
        super(objectPool);
        width = RADIUS * 2;
        height = RADIUS * 2;
        this.TAKEN_X = TAKEN_X;
        this.TAKEN_Y = TAKEN_Y;
    }

    @Override
    public void update(GameContainer gc, float cameraX, float cameraY)
    {
        if (takenMotionFlag)
        {
            abX += (TAKEN_X - getDiX() + 100) / 5;
            abY += (TAKEN_Y - getDiY() - 100) / 5;

            if (getDiX() > TAKEN_X && getDiY() < TAKEN_Y)
            {
                takenMotionFlag = false;
                disActive();
            }
        }

        if (checkLeaving(0))
        {
            disActive();
        }
        changeToDisplayPoint(cameraX, cameraY);
    }

    public abstract void disActive();

    /**
     * 初期化処理
     * @param abX 中心座標
     * @param abY 中心座標
     * @param num ID
     */
    public void activate(int abX, int abY, int num)
    {
        this.abX = abX;
        this.abY = abY;
        this.num = num;
        active = true;
        takenMotionFlag = false;
    }

    public void taken()
    {
        takenMotionFlag = true;
    }
}
