import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * 背景の更新，描画
 *
 * @author ryuryu
 */
public class Background extends GameObject
{
    private final float alpha = 0.5f;

    Background(ObjectPool objectPool)
    {
        super(objectPool);
        width = 1560;
        height = 900;
    }

    @Override
    public void update(GameContainer gc, float cameraX, float cameraY)
    {
        if (getDiX() < 0)
        {
            abX = abX + width;
        }
        else if (getDiX() > width)
        {
            abX = abX - width;
        }

        if (getDiY() < 0)
        {
            abY = abY + height;
        }
        else if (getDiY() > height)
        {
            abY = abY - height;
        }

        changeToDisplayPoint(cameraX, cameraY);
    }

    @Override
    public void render(Graphics g, ImageManager im)
    {
        im.drawBackGround2(getDiX(), getDiY(), width, height, alpha);
        im.drawBackGround1(getDiX(), getDiY(), width, height, alpha);

        im.drawBackGround2(getDiX(), getDiY() - height, width, height, alpha);
        im.drawBackGround1(getDiX(), getDiY() - height, width, height, alpha);

        im.drawBackGround2(getDiX() - width, getDiY(), width, height, alpha);
        im.drawBackGround1(getDiX() - width, getDiY(), width, height, alpha);

        im.drawBackGround2(getDiX() - width, getDiY() - height, width, height, alpha);
        im.drawBackGround1(getDiX() - width, getDiY() - height, width, height, alpha);
    }
}
