import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class ToolBar
{

    ToolBar(ObjectPool objectPool)
    {
    }

    public void update(GameContainer gc)
    {
    }

    public void render(Graphics g, ImageManager im)
    {
        im.drawToolBar(0, 0);
        im.drawCursor(350, 40);
        im.drawGround(450, 40, Ground.WIDTH, Ground.WIDTH, new Ground.Position());
        im.drawJoint(550, 40, Joint.RADIUS * 2, Joint.RADIUS * 2);
        im.drawGlass1(665, 55, 60, 60);
        im.drawCherry(750, 40, Cherry.RADIUS * 2, Cherry.RADIUS * 2);
        im.drawHeart(850, 40, Heart.RADIUS * 2, Heart.RADIUS * 2);
        im.drawDelete(950, 40);
    }
}
