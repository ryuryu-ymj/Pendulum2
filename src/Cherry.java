import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * チェリークラス<br>
 * アイテム
 */
public class Cherry extends GameItem
{
    Cherry(ObjectPool objectPool)
    {
        super(Play.DISPLAY_WIDTH - 250, 50, objectPool);
    }

    @Override
    public void render(Graphics g, ImageManager im)
    {
        im.drawCherry(getDiX(), getDiY(), width, height);
    }

    @Override
    public void disActive()
    {
        active = false;
        objectPool.isCherryDisplayed[num] = false;
    }
}
