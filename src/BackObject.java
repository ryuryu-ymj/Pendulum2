import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class BackObject extends GameObject
{
    BackObject()
    {
        width = 600;
        height = 600;
        abX = 300;
        abY = 660;
    }

    @Override
    public void update(GameContainer gc, float cameraX, float cameraY)
    {
        changeToDisplayPoint(cameraX, cameraY);
    }

    @Override
    public void render(Graphics g, ImageManager im)
    {
        im.drawTree1(getDiX(), getDiY() - height / 2, width, height);
    }
}
