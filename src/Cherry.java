import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Cherry extends GameObject
{
    public static final int RADIUS = 20;
    /**
     * cherry がステージ上のどの cherry を演じているのか（cherryX の配列番号）
     */
    public int num;

    Cherry()
    {
        width = RADIUS * 2;
        height = RADIUS * 2;
    }

    @Override
    public void update(GameContainer gc, float cameraX, float cameraY)
    {
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
