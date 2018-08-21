import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * ハートクラス<br>
 * アイテム
 */
public class Heart extends GameObject
{
    /**
     * 半径
     */
    public static final int RADIUS = 20;
    /**
     * heart がステージ上のどの heart を演じているのか（heartX の配列番号）
     */
    public int num;

    Heart()
    {
        width = RADIUS * 2;
        height = RADIUS * 2;
    }

    @Override
    public void update(GameContainer gc, float cameraX, float cameraY)
    {
        if (checkLeaving(0))
        {
            active = false;
            ObjectPool.isHeartDisplayed[num] = false;
        }
        changeToDisplayPoint(cameraX, cameraY);
    }

    @Override
    public void render(Graphics g, ImageManager im)
    {
        im.drawHeart(getDiX(), getDiY(), width, height);
    }

    public void activate(int abX, int abY, int num)
    {
        this.abX = abX;
        this.abY = abY;
        this.num = num;
        active = true;
    }
}
