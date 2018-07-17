import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class Wire
{
    private double x1, y1, x2, y2;
    public boolean active;
    /**
     * ワイヤーの角度<br>
     * 3時の方向から反時計回り　ラジアン
     */
    private double angle;
    /**
     * 張力
     */
    private static double stringForce;
    /**
     * playerが1周回ったかどうかを判定するためのフラグ<p>
     * playerが(配列番号×60)度の角度を通ったらtrue
     */
    public boolean[] isPlayerPass = new boolean[6];
    /**
     * ワイヤーがつながっているジョイントの数字　何もつながっていないと－1
     */
    public int jointLockedNum;

    Wire()
    {
        active = false;
    }

    public void update(GameContainer gc, double playerX, double playerY, double jointX, double jointY)
    {
        this.x1 = playerX;
        this.y1 = playerY;
        this.x2 = jointX;
        this.y2 = jointY;
        angle = Math.atan2(playerY - jointY, playerX - jointX);

        // 加速
        if(gc.getInput().isKeyDown(Input.KEY_SPACE))
        {
            stringForce = 4;
        }
        else
        {
            stringForce = 0;
        }
        //System.out.println(stringForce);
    }

    public void render(Graphics g, ImageManager im)
    {
        Color c = new Color(0, 240, 240);
        g.setColor(c);
        g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
    }

    /**
     * playerが一周したかの判定
     */
    public boolean playerLoop()
    {
        // playerがその角度を通ったかどうかを判定
        for (int i = 0; i < isPlayerPass.length; i++)
        {
            if (angle > (i - 3) * Math.PI / 3 && angle < (i - 2) * Math.PI / 3)
            {
                isPlayerPass[i] = true;
            }
            //System.out.println(i + " " + isPlayerPass[i]);
        }

        // playerが一周したかの判定
        a:
        for (int i = 0; i < isPlayerPass.length; i++)
        {
            if (isPlayerPass[i] == false)
            {
                break a;
            }
            if (i == isPlayerPass.length - 1)
            {
                //System.out.println("a");
                return true;
            }
        }
        return false;
    }

    public double getAngle()
    {
        return angle;
    }

    public static double getStringForce()
    {
        return stringForce;
    }
}
