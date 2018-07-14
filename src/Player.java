import org.newdawn.slick.Color;
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
     * 角度　ラジアン　反時計回り<br>
     * ワイヤーがつながっているときに用いる
     */
    private double angle;
    /**
     * 角速度　ラジアン　反時計回り<br>
     * ワイヤーがつながっているときに用いる
     */
    private double speedAngle;
    /**
     * 半径
     */
    private int radius;
    private final int speedMax = 11;
    /**
     * ジョイントとワイヤーでつながっているかどうか
     */
    public boolean isJointLock;

    Player(int x, int y, int radius)
    {
        this.abX = x;
        this.abY = y;
        this.radius = radius;
        speedX = 0;
        speedY = 0;
        speedAngle = 0;
        angle = 0;
        isJointLock = false;
    }

    /**
     * 振り子運動
     *
     * @param wireAngle  ワイヤーの角度
     * @param wireForce  ワイヤーの張力
     * @param wireLength ワイヤーの長さ
     */
    public void pendulum(double wireAngle, double wireForce, float wireLength, float wireX, float wireY)
    {
        if (!isJointLock)
        {
            angle = wireAngle;
            isJointLock = true;
        }
        double forceAngle = Math.atan2(speedY, speedX);
        double force = Math.sqrt(Math.pow(Math.abs(speedX), 2) + Math.pow(Math.abs(speedY), 2)) * Math.sin(forceAngle + wireAngle);
        speedAngle = Math.atan2(force, wireLength);
        angle += speedAngle;
        abX = wireX + (float) (Math.cos(-angle) * wireLength);
        abY = wireY + (float) (Math.sin(-angle) * wireLength);
        speedX = 0;
        speedY = 0;
        /*speedX = (float) (-force * Math.sin(-wireAngle));
        speedY = (float) (force * Math.cos(-wireAngle));*/

        // 張力による加速
        //speedX -= Math.cos(wireAngle) * wireForce;
        //speedY -= Math.sin(wireAngle) * wireForce;
        System.out.println(angle);
    }

    public void finishPendulum()
    {

    }

    /**
     * x座標の跳ね返り
     */
    public void boundX(float groundX)
    {
        speedX = -speedX;
        // 地面の左
        if (abX < groundX)
        {
            abX = groundX - radius;
        }
        // 地面の右
        else
        {
            abX = groundX + radius;
        }
    }

    /**
     * y座標の跳ね返り
     */
    public void boundY(float groundY)
    {
        speedY = -speedY;
        // 地面の上
        if (abY < groundY)
        {
            abY = groundY - radius;
        }
        // 地面の下
        else
        {
            abY = groundY + radius;
        }
    }

    private float slowDown(float a)
    {
        if (a > 0)
        {
            a -= 0.01;
        }
        else if (a < 0)
        {
            a += 0.01;
        }
        return a;
    }

    @Override
    public void update(GameContainer gc, float cameraX, float cameraY)
    {
        // 重力
        speedY += 0.1;

        // 速度の出過ぎを防止
        if (speedX > speedMax)
        {
            speedX = speedMax;
        }
        if (speedY > speedMax)
        {
            speedY = speedMax;
        }

        abX = abX + speedX;
        abY = abY + speedY;

        //speedX = slowDown(speedX);
        //speedY = slowDown(speedY);

        changeToDisplayPoint(cameraX, cameraY);
    }

    @Override
    public void render(Graphics g, ImageManager im)
    {
        g.setColor(Color.orange);
        g.drawOval((int) getDiX() - radius, (int) getDiY() - radius, radius * 2, radius * 2);
    }
}
