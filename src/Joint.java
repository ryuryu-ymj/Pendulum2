import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * ジョイント<br>
 * ワイヤーをつなぐことができる接続節
 */
public class Joint extends GameObject
{
    /**
     * 半径
     */
    public static final int RADIUS = 15;
    /**
     * joint がステージ上のどのjointを演じているのか（jointX の配列番号）
     */
    public int num;
    /**
     * 種類
     */
    private Type type;
    /**
     * jointLock有効範囲半径　0なら有効範囲は無限(指定しない)
     */
    private int lockRadius;
    /**
     * playerが一周したかどうか
     */
    public boolean isPlayerLoop;
    /**
     * 弾うち型jointの向く方向の角度
     */
    int angle;
    /**
     * 生存時間（弾を撃つタイミングに使用）
     */
    int counter = 0;

    public enum Type
    {
        NORMAL, GOAL, BEE
    }

    Joint()
    {
        active = false;
    }

    @Override
    public void update(GameContainer gc, float cameraX, float cameraY)
    {
        if (checkLeaving(0))
        {
            active = false;
            ObjectPool.isJointDisplayed[num] = false;
            //System.out.println("delate " + num + " " + (int)abX / 55 + "," + (int)abY / 55);
        }
        changeToDisplayPoint(cameraX, cameraY);
    }

    @Override
    public void render(Graphics g, ImageManager im)
    {
        /*g.setColor(Color.blue);
        g.drawOval((int)getDiX() - RADIUS, (int)getDiY() - RADIUS, RADIUS * 2, RADIUS * 2);*/
        switch (type)
        {
            case NORMAL:
                im.drawJoint(getDiX(), getDiY(), RADIUS * 2, RADIUS * 2);
                break;
            case GOAL:
                g.setColor(Color.red);
                g.drawOval(getDiX() - Joint.RADIUS, getDiY() - Joint.RADIUS, Joint.RADIUS * 2, Joint.RADIUS * 2);
                break;
            case BEE:
                if (isPlayerLoop)
                {
                    im.drawJoint(getDiX(), getDiY(), RADIUS * 2, RADIUS * 2);
                }
                else
                {
                    im.drawBee(getDiX(), getDiY(), RADIUS * 2, RADIUS * 2);
                }
                break;
        }
    }

    public void activate(int abX, int abY, Type type, int lockRadius, boolean isPlayerLoop, int num)
    {
        this.abX = abX;
        this.abY = abY;
        this.type = type;
        this.lockRadius = lockRadius;
        this.isPlayerLoop = isPlayerLoop;
        this.num = num;
        active = true;
    }

    public Type getType()
    {
        return type;
    }

    public int getLockRadius()
    {
        return lockRadius;
    }
}
