import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class MousePointer extends GameObject
{

    @Override
    public void update(GameContainer gc, float cameraX, float cameraY)
    {
        changeToDisplayPoint(cameraX, cameraY);
    }

    @Override
    public void render(Graphics g, ImageManager im)
    {
        im.drawGround(getDiX(), getDiY(), Ground.WIDTH, Ground.WIDTH);
    }

    public void setPointer(float abX, float abY)
    {
        this.abX = abX;
        this.abY = abY;
    }
}
