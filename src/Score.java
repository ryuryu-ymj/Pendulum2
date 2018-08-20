import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.io.FileNotFoundException;

public class Score
{
    private int cherryScore;
    private final int CHERRY_MAX = 10;
    private int heartScore;
    private Font font;

    Score()
    {
        cherryScore = 0;
        heartScore = 0;
        font = new Font("res/font/fontSmall");
        font.setColor(1, 1, 1);
    }

    public void initScore()
    {
        cherryScore = 0;
    }

    public void render(Graphics g, ImageManager im)
    {
        im.drawCherry(Play.DISPLAY_WIDTH - 125, 50, Cherry.RADIUS * 2, Cherry.RADIUS * 2);
        font.drawString("x " + cherryScore, Play.DISPLAY_WIDTH - 100, 50);
    }

    public void addCherry()
    {
        cherryScore++;
        if (cherryScore >= CHERRY_MAX)
        {
            cherryScore = 0;
            heartScore++;
        }
    }

    public void subtractHeart()
    {
        heartScore--;
    }

    public void addHeart()
    {
        heartScore++;
    }

    public boolean isHeartZero()
    {
        return heartScore == 0;
    }
}
