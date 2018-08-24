import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class DamageEffect
{
    private int counter;

    public void init()
    {
        counter = 0;
    }

    public void start()
    {
        counter = 14;
    }

    public void update()
    {
        if (counter > 0)
        {
            counter--;
        }
    }

    public void render(Graphics g)
    {
        if (counter % 7 > 3)
        {
            g.setColor(new Color(1, 0, 0, 0.5f));
            g.fillRect(0, 0, Play.DISPLAY_WIDTH, Play.DISPLAY_HEIGHT);
        }
    }
}
