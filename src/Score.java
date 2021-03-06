import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Sound;

public class Score
{
    private int cherryScore;
    private final int CHERRY_MAX = 20;
    private int heartScore;
    private Font font;

    Score()
    {
        cherryScore = 0;
        heartScore = 0;
        font = new Font("res/font/fontSmall");
        font.setColor(102f / 256, 59f / 256, 0 / 256);
    }

    public void initScore()
    {
        cherryScore = 0;
        heartScore = 3;
    }

    public void render(Graphics g, ImageManager im)
    {
        im.drawCherry(Play.DISPLAY_WIDTH - 250, 50, Cherry.RADIUS * 2, Cherry.RADIUS * 2);
        font.drawString("x " + cherryScore, Play.DISPLAY_WIDTH - 225, 50);
        im.drawHeart(Play.DISPLAY_WIDTH - 125, 50, Heart.RADIUS * 2, Heart.RADIUS * 2);
        font.drawString("x " + heartScore, Play.DISPLAY_WIDTH - 100, 50);
    }

    public void addCherry(Sound sound)
    {
        cherryScore++;
        if (cherryScore >= CHERRY_MAX)
        {
            cherryScore = 0;
            heartScore++;
            sound.play();
        }
    }

    public void subHeart()
    {
        if (heartScore == 0)
        {
            return;
        }
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
