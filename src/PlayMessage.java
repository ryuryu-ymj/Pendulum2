import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class PlayMessage
{
    private int x, y;
    private Font fontTitle;
    private Font fontMessage;

    PlayMessage()
    {
        x = Play.DISPLAY_WIDTH / 2;
        y = Play.DISPLAY_HEIGHT / 2;
        fontTitle = new Font("res/font/fontLarge");
        fontMessage = new Font("res/font/fontSmall");
    }

    public void renderStageNum(Graphics g, int stageNum, int counter)
    {
        //fontTitle.setColor(240, 0, 160);
        fontTitle.setColor(0.4f, 0.23f, 0);
        fontTitle.drawString("stage " + (stageNum + 1), x - fontTitle.getWidth("stage " + (stageNum + 1)) / 2, y - 100);

        fontMessage.setColor(0.4f, 0.23f, 0);
        fontMessage.setAlpha((float) Math.abs(counter % 20 - 10) / 10);
        fontMessage.drawString("click left mouse", x - fontMessage.getWidth("click left mouse") / 2, y + 200);
    }

    public void renderGameOver(Graphics g, int counter)
    {
        fontTitle.setColor(1, 0, 0);
        fontTitle.drawString("GameOver", x - fontTitle.getWidth("GameOver") / 2, y - 100);

        fontMessage.setColor(0.4f, 0.23f, 0);
        fontMessage.setAlpha((float) Math.abs(counter % 20 - 10) / 10);
        fontMessage.drawString("click left mouse", x - fontMessage.getWidth("click left mouse") / 2, y + 200);
    }
}
