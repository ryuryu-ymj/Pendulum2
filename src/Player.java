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
     * 半径
     */
    private int radius;
    /**
     * 口が向く方向の角度<br>
     * 12時から時計回り　ラジアン
     */
    private double angle;
    private final int speedMax = 11;

    Player(int x, int y)
    {
        this.abX = x;
        this.abY = y;
        radius = 25;
        width = radius * 2;
        height = radius * 2;
        speedX = 0;
        speedY = 0;
        angle = 0;
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
     */
    public void boundUp(float groundY)
    {
        if (speedY > 0)
        {
            speedY = -speedY;
        }
        abY = groundY - radius;
    }

    /**
     * 下方向への跳ね返り
     */
    public void boundDown(float groundY)
    {
        if (speedY < 0)
        {
            speedY = -speedY;
        }
        abY = groundY + radius;
    }

    /**
     * 左方向への跳ね返り
     */
    public void boundLeft(float groundX)
    {
        if (speedX > 0)
        {
            speedX = -speedX;
        }
        abX = groundX - radius;
    }

    /**
     * 右方向への跳ね返り
     */
    public void boundRight(float groundX)
    {
        if (speedX < 0)
        {
            speedX = -speedX;
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

        speedX = slowDown(speedX, 0.01f);
        speedY = slowDown(speedY, 0.01f);

        double deltaAngle = Math.atan2(gc.getInput().getMouseY() - getDiY(), gc.getInput().getMouseX() - getDiX()) + Math.PI / 2 - angle;
        if (deltaAngle > Math.PI * 2)
        {
            deltaAngle = deltaAngle - Math.PI * 2;
        }
        if (deltaAngle > Math.PI)
        {
            deltaAngle = -(Math.PI * 2 - deltaAngle);
        }
        angle += deltaAngle / 30;

        changeToDisplayPoint(cameraX, cameraY);
    }

    @Override
    public void render(Graphics g, ImageManager im)
    {
        /*g.setColor(Color.orange);
        g.drawOval((int) getDiX() - radius, (int) getDiY() - radius, radius * 2, radius * 2);*/
        im.drawPlayer(getDiX(), getDiY(), radius * 2, radius * 2, (float) Math.toDegrees(angle));
    }
}
