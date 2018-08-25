import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * ハートクラス<br>
 * アイテム
 */
public class Heart extends GameItem
{
    Heart(ObjectPool objectPool)
    {
        super(Play.DISPLAY_WIDTH - 125, 50, objectPool);
    }

    @Override
    public void render(Graphics g, ImageManager im)
    {
        im.drawHeart(getDiX(), getDiY(), width, height);
    }

    @Override
    public void disActive()
    {
        active = false;
        objectPool.isHeartDisplayed[num] = false;
    }
}
