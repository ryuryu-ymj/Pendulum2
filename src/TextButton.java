import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class TextButton extends ImageButton
{
    /**
     * 中心点の座標
     */
    private int x, y;
    private int width, height;
    private String string;
    private Color frameColor;
    private Color insideColor;
    private int frameWidth;

    TextButton(int x, int y, int width, int height, String string)
    {
        super(x, y, width, height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.string = string;
        insideColor = new Color(120f / 256, 165f / 256, 0 / 256);//new Color(99f / 256, 57f / 256, 0 / 256);
        frameColor = new Color(227f / 256, 255f / 256, 152f / 256);//new Color(120f / 256, 165f / 256, 0 / 256);
        frameWidth = 8;
    }

    public void setColor(Color frameColor, Color insideColor)
    {
        this.frameColor = frameColor;
        this.insideColor = insideColor;
    }

    public int getFrameWidth()
    {
        return frameWidth;
    }

    public void setFrameWidth(int frameWidth)
    {
        this.frameWidth = frameWidth;
    }

    public void render(Graphics g, ImageManager im, FontManager fm)
    {
        g.setColor(frameColor);
        g.fillRoundRect(x - width / 2, y - height / 2, width, height, height / 5);
        g.setColor(insideColor);
        g.fillRoundRect(x - (width - frameWidth) / 2, y - (height - frameWidth) / 2,
                width - frameWidth, height - frameWidth, height / 5);
        Font font = fm.getMediumFont();
        font.setColor(frameColor.r, frameColor.g, frameColor.b);
        font.drawString(string, x - font.getWidth(string) / 2, y);
    }
}
