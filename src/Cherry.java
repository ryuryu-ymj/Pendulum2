import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * チェリークラス<br>
 * アイテム
 */
public class Cherry extends GameObject
{
    /**
     * 半径
     */
    public static final int RADIUS = 20;
    /**
     * cherry がステージ上のどの cherry を演じているのか（cherryX の配列番号）
     */
    public int num;

    Cherry(ObjectPool objectPool)
    {
        super(objectPool);
        width = RADIUS * 2;
        height = RADIUS * 2;
    }

    @Override
    public void update(GameContainer gc, float cameraX, float cameraY)
    {
        if (checkLeaving(0))
        {
            active = false;
            objectPool.isCherryDisplayed[num] = false;
        }
        changeToDisplayPoint(cameraX, cameraY);
    }

    @Override
    public void render(Graphics g, ImageManager im)
    {
        im.drawCherry(getDiX(), getDiY(), width, height);
    }

    public void activate(int abX, int abY, int num)
    {
        this.abX = abX;
        this.abY = abY;
        this.num = num;
        active = true;
    }
}
