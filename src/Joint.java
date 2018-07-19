import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Joint extends GameObject
{
	public int radius;
	/**
	 * jointがステージ上のどのjointを演じているのか（jointXの番号）
	 */
	int num;
	/**
	 * 0:ノーマル 1:ゴール 2:ハート入り 3:弾うち(3方向) 4:弾うち(狙撃)
	 */
	int type;
	/**
	 * jointLock有効範囲半径　0なら有効範囲は無限(指定しない)
	 */
	int lockRadius;
	/**
	 * playerが一周したかどうか
	 */
	boolean isPlayerLoop;
	/**
	 * 弾うち型jointの向く方向の角度
	 */
	int angle;
	/**
	 * 生存時間（弾を撃つタイミングに使用）
	 */
	int counter = 0;

	Joint(int radius)
	{
		this.radius = radius;
		active = false;
	}

    @Override
    public void update(GameContainer gc, float cameraX, float cameraY)
    {
        if (checkLeaving(0))
        {
            active = false;
            ObjectPool.isJointDisplay[num] = false;
            //System.out.println("delate " + num + " " + (int)abX / 55 + "," + (int)abY / 55);
        }
        changeToDisplayPoint(cameraX, cameraY);
    }

    @Override
    public void render(Graphics g, ImageManager im)
    {
        /*g.setColor(Color.blue);
        g.drawOval((int)getDiX() - radius, (int)getDiY() - radius, radius * 2, radius * 2);*/
        im.drawJoint(getDiX(), getDiY(), radius * 2, radius * 2);
    }

    public void activate(int abX, int abY, int type, int lockRadius, int num)
    {
        this.abX = abX;
        this.abY = abY;
        this.type = type;
        this.lockRadius = lockRadius;
        this.num = num;
        active = true;
    }
}
