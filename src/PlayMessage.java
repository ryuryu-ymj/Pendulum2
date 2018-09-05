import org.newdawn.slick.Graphics;

public class PlayMessage
{
    private int x, y;

    PlayMessage()
    {
        x = Play.DISPLAY_WIDTH / 2;
        y = Play.DISPLAY_HEIGHT / 2;
    }

    public void renderStageNum(Graphics g, int stageNum, int counter, FontManager fm)
    {
        //fontTitle.setColor(240, 0, 160);
        fm.getMediumFont().setColor(0.4f, 0.23f, 0);
        fm.getMediumFont().drawStringCentered("stage " + (stageNum + 1), x, y - 100);

        fm.getSmallFont().setColor(0.4f, 0.23f, 0);
        fm.getSmallFont().setAlpha((float) Math.abs(counter % 20 - 10) / 10);
        fm.getSmallFont().drawStringCentered("click to start", x, y + 200);
    }

    public void renderGameOver(Graphics g, int counter, FontManager fm)
    {
        fm.getMediumFont().setColor(1, 0, 0);
        fm.getMediumFont().drawStringCentered("GameOver", x, y - 100);

        fm.getSmallFont().setColor(0.4f, 0.23f, 0);
        fm.getSmallFont().setAlpha((float) Math.abs(counter % 20 - 10) / 10);
        fm.getSmallFont().drawStringCentered("click to start", x, y + 200);
    }

    public void renderGameClear(Graphics g, int counter, FontManager fm)
    {
        fm.getMediumFont().setColor(1, 0.5f, 0.76f);
        fm.getMediumFont().drawStringCentered("GameClear", x, y - 200);

        fm.getMediumFont().setColor(1, 0.5f, 0.76f);
        fm.getMediumFont().drawStringCentered("Congratulations!", x, y);

        fm.getSmallFont().setColor(0.4f, 0.23f, 0);
        fm.getSmallFont().setAlpha((float) Math.abs(counter % 20 - 10) / 10);
        fm.getSmallFont().drawStringCentered("click to start", x, y + 200);
    }
}
