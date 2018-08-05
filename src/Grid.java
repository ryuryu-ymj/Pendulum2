import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Grid extends GameObject
{
    @Override
    public void update(GameContainer gc, float cameraX, float cameraY)
    {
        abX = cameraX - Play.DISPLAY_WIDTH / 2 - (cameraX - Play.DISPLAY_WIDTH / 2) % Ground.WIDTH;
        abY = cameraY - Play.DISPLAY_HEIGHT / 2 - (cameraY - Play.DISPLAY_HEIGHT / 2) % Ground.WIDTH;
        changeToDisplayPoint(cameraX, cameraY);
        //System.out.println(getDiX() + " " + getDiY());
    }

    @Override
    public void render(Graphics g, ImageManager im)
    {
        g.setColor(Color.gray);
        for (int i = 0; getDiX() + i * Ground.WIDTH <= Play.DISPLAY_WIDTH; i++)
        {
            g.drawLine(getDiX() + i * Ground.WIDTH, 0, getDiX() + i * Ground.WIDTH, Play.DISPLAY_HEIGHT);
        }
        for (int i = 0; getDiY() + i * Ground.WIDTH <= Play.DISPLAY_HEIGHT; i++)
        {
            g.drawLine(0, getDiY() + i * Ground.WIDTH, Play.DISPLAY_WIDTH, getDiY() + i * Ground.WIDTH);
        }
        g.setColor(Color.red);
    }

    public int getGridCenterAbX(int diX)
    {
        int i = (diX - (int)getDiX()) / Ground.WIDTH;
        return (int)abX + Ground.WIDTH * i + Ground.WIDTH / 2;
    }

    public int getGridCenterAbY(int diY)
    {
        int i = (diY - (int)getDiY()) / Ground.WIDTH;
        return (int)abY + Ground.WIDTH * i + Ground.WIDTH / 2;
    }
}
