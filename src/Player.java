import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Player extends GameObject
{
    /**
     * 速度ベクトル<br>
     * 正確にはabX，abYの1フレーム毎の変位量
     */
    private float speedX, speedY;
    /**
     * 半径
     */
    private int radius;
    /**
     * 口が向く方向の角度<br>
     * 12時から時計回り　ラジアン
     */
    private double angle;
    private final int SPEED_MAX = 11;
    /**
     * 攻撃を受けるかどうか
     */
    private boolean canBeDamaged;
    private int counter;

    Player(int x, int y, ObjectPool objectPool)
    {
        super(objectPool);
        this.abX = x;
        this.abY = y;
        radius = 25;
        width = radius * 2;
        height = radius * 2;
        speedX = 0;
        speedY = 0;
    }

    public void init(int x, int y)
    {
        this.abX = x;
        this.abY = y;
        speedX = 0;
        speedY = 0;
        angle = 0;
        active = true;
        canBeDamaged = true;
        counter = 0;
    }

    /**
     * 振り子運動
     *
     * @param wireAngle ワイヤーの角度
     * @param wireForce ワイヤーの張力
     */
    public void pendulum(double wireAngle, double wireForce)
    {
        double forceAngle = Math.atan2(speedY, speedX);
        double force = Math.sqrt(Math.pow(Math.abs(speedX), 2) + Math.pow(Math.abs(speedY), 2)) * Math.sin(forceAngle - wireAngle);
        speedX = (float) (-force * Math.sin(wireAngle));
        speedY = (float) (force * Math.cos(wireAngle));

        // 張力による加速
        speedX -= Math.cos(wireAngle) * wireForce;
        speedY -= Math.sin(wireAngle) * wireForce;
    }

    /**
     * 上方向への跳ね返り
     *
     * @param bound 反発係数
     */
    public void boundUp(float groundY, int bound)
    {
        if (speedY > 0)
        {
            speedY = -speedY * bound;
        }
        abY = groundY - radius;
    }

    /**
     * 下方向への跳ね返り
     *
     * @param bound 反発係数
     */
    public void boundDown(float groundY, int bound)
    {
        if (speedY < 0)
        {
            speedY = -speedY * bound;
        }
        abY = groundY + radius;
    }

    /**
     * 左方向への跳ね返り
     *
     * @param bound 反発係数
     */
    public void boundLeft(float groundX, int bound)
    {
        if (speedX > 0)
        {
            speedX = -speedX * bound;
        }
        abX = groundX - radius;
    }

    /**
     * 右方向への跳ね返り
     *
     * @param bound 反発係数
     */
    public void boundRight(float groundX, int bound)
    {
        if (speedX < 0)
        {
            speedX = -speedX * bound;
        }
        abX = groundX + radius;
    }

    private float slowDown(float a, float delta)
    {
        if (a > delta)
        {
            a -= delta;
        }
        else if (a >= 0)
        {
            a = 0;
        }
        else if (a < -delta)
        {
            a += delta;
        }
        else if (a <= 0)
        {
            a = 0;
        }
        return a;
    }

    /**
     * ダメージを受けると，一定時間受けなくなる
     */
    public void damage()
    {
        active = false;
    }

    @Override
    public void update(GameContainer gc, float cameraX, float cameraY)
    {
        // 重力
        speedY += 0.1;

        // 速度の出過ぎを防止
        if (speedX > SPEED_MAX)
        {
            speedX = SPEED_MAX;
        }
        else if (speedX < -SPEED_MAX)
        {
            speedX = -SPEED_MAX;
        }
        if (speedY > SPEED_MAX)
        {
            speedY = SPEED_MAX;
        }
        else if (speedY < -SPEED_MAX)
        {
            speedY = -SPEED_MAX;
        }

        abX = abX + speedX;
        abY = abY + speedY;

        speedX = slowDown(speedX, 0.01f);
        speedY = slowDown(speedY, 0.01f);

        double deltaAngle = Math.atan2(gc.getInput().getMouseY() - getDiY(), gc.getInput().getMouseX() - getDiX()) + Math.PI / 2 - angle;
        if (deltaAngle > Math.PI * 2)
        {
            deltaAngle = deltaAngle - Math.PI * 2;
        }
        else if (deltaAngle < 0)
        {
            deltaAngle = deltaAngle + Math.PI * 2;
        }
        if (deltaAngle > Math.PI)
        {
            deltaAngle = -(Math.PI * 2 - deltaAngle);
        }
        angle += deltaAngle / 30;

        changeToDisplayPoint(cameraX, cameraY);


        if (counter > 0)
        {
            counter--;
        }
        else
        {
            canBeDamaged = true;
        }
    }

    @Override
    public void render(Graphics g, ImageManager im)
    {
        /*g.setColor(Color.orange);
        g.drawOval((int) getDiX() - RADIUS, (int) getDiY() - RADIUS, RADIUS * 2, RADIUS * 2);*/
        if (canBeDamaged)
        {
            im.drawPlayer(getDiX(), getDiY(), radius * 2, radius * 2, (float) Math.toDegrees(angle), 1);
        }
        else
        {
            im.drawPlayer(getDiX(), getDiY(), radius * 2, radius * 2, (float) Math.toDegrees(angle), 0.5f);
        }
    }

    public boolean isCanBeDamaged()
    {
        return canBeDamaged;
    }
}
