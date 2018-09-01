import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Background extends GameObject
{


    Background(ObjectPool objectPool)
    {
        super(objectPool);
        width = 2400;
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
        im.drawBackGround2(getDiX(), getDiY(), width, height);
        im.drawBackGround1(getDiX(), getDiY(), width, height);

        im.drawBackGround2(getDiX(), getDiY() - height, width, height);
        im.drawBackGround1(getDiX(), getDiY() - height, width, height);

        im.drawBackGround2(getDiX() - width, getDiY(), width, height);
        im.drawBackGround1(getDiX() - width, getDiY(), width, height);

        im.drawBackGround2(getDiX() - width, getDiY() - height, width, height);
        im.drawBackGround1(getDiX() - width, getDiY() - height, width, height);
    }
}
