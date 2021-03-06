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
    private float angle;
    /**
     * 生存時間（弾を撃つタイミングに使用）
     */
    private int counter = 0;
    private Player player;

    public enum Type
    {
        NORMAL, GOAL, BEE_AIM, BEE_ROTATE, HEART_IN;

        public Type next()
        {
            if (ordinal() + 1 >= values().length)
            {
                return values()[0];
            }
            return values()[ordinal() + 1];
        }
    }

    Joint(Player player, ObjectPool objectPool)
    {
        super(objectPool);
        this.player = player;
        active = false;
        type = Type.NORMAL;
    }

    @Override
    public void update(GameContainer gc, float cameraX, float cameraY)
    {
        if (!isPlayerLoop)
        {
            switch (type)
            {
                case BEE_AIM:
                    angle = (float) Math.toDegrees(Math.atan2(player.abY - abY, player.abX - abX));
                    if (counter % 50 == 0)
                    {
                        fireBullet(angle);
                    }
                    break;
                case BEE_ROTATE:
                    angle = counter;
                    if (counter % 50 == 0)
                    {
                        fireBullet(angle);
                    }
                    break;
            }
        }

        b:
        if (checkLeaving(20))
        {
            if (objectPool.wire.jointLockedNum != -1)
            {
                if (this == objectPool.joints[objectPool.wire.jointLockedNum])
                {
                    break b;// ワイヤーがつながっているジョイントは消さない
                }
            }
            active = false;
            objectPool.isJointsActivate[num] = false;
            //System.out.println("delete " + num + " " + objectPool.wire.jointLockedNum);
        }
        changeToDisplayPoint(cameraX, cameraY);
        counter++;
    }

    /**
     * 指定された方向に弾を撃つ
     *
     * @param angle 動かす方向の角度（３時の方向から反時計回り）
     */
    private void fireBullet(float angle)
    {
        if (objectPool.newBullet(abX, abY, angle, 3) == -1)
        {
            System.err.println("bullet の数が足りません");
        }
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
                im.drawJointGoal(getDiX(), getDiY(), RADIUS * 2, RADIUS * 2);
                break;
            case BEE_AIM:
            case BEE_ROTATE:
                if (isPlayerLoop)
                {
                    im.drawJoint(getDiX(), getDiY(), RADIUS * 2, RADIUS * 2);
                }
                else
                {
                    im.drawBee(getDiX(), getDiY(), RADIUS * 2, RADIUS * 2, angle);
                }
                break;
            case HEART_IN:
                if (isPlayerLoop)
                {
                    im.drawJointHeartOut(getDiX(), getDiY(), Heart.RADIUS * 2, Heart.RADIUS * 2);
                }
                else
                {
                    im.drawJointHeartIn(getDiX(), getDiY(), Heart.RADIUS * 2, Heart.RADIUS * 2);
                }
                break;
        }

        if (lockRadius != 0)
        {
            g.setColor(Color.blue);
            g.drawOval((int) getDiX() - lockRadius, (int) getDiY() - lockRadius, lockRadius * 2, lockRadius * 2);
        }
    }

    public static void renderIcon(Graphics g, ImageManager im, int x, int y, float angle, Type type)
    {
        /*g.setColor(Color.blue);
        g.drawOval((int)getDiX() - RADIUS, (int)getDiY() - RADIUS, RADIUS * 2, RADIUS * 2);*/
        switch (type)
        {
            case NORMAL:
                im.drawJoint(x, y, RADIUS * 2, RADIUS * 2);
                break;
            case GOAL:
                im.drawJointGoal(x, y, RADIUS * 2, RADIUS * 2);
                break;
            case BEE_AIM:
            case BEE_ROTATE:
                im.drawBee(x, y, RADIUS * 2, RADIUS * 2, angle);
                break;
            case HEART_IN:
                im.drawJointHeartIn(x, y, Heart.RADIUS * 2, Heart.RADIUS * 2);
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
        angle = 0;
        counter = 0;
    }

    public Type getType()
    {
        return type;
    }

    public int getLockRadius()
    {
        return lockRadius;
    }

    public float getAngle()
    {
        return angle;
    }
}
