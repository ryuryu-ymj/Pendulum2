import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Credit extends GameState
{


    @Override
    public void init(GameContainer gc) throws SlickException
    {

    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException
    {

    }

    @Override
    public void render(GameContainer gc, Graphics g, ImageManager im, FontManager fm) throws SlickException
    {
        g.setBackground(new Color(189f / 256, 226f / 256, 14f / 256));
        Font font = fm.getMediumFont();
        font.setColor(102f / 256, 59f / 256, 0 / 256);
        font.drawStringCentered("プログラム　イラスト:", Play.DISPLAY_WIDTH / 2, 200);
        font.drawStringCentered("RyuRyu", Play.DISPLAY_WIDTH / 2, 250);
        font.drawStringCentered("BGM　効果音:", Play.DISPLAY_WIDTH / 2, 350);
        font.drawStringCentered("魔王魂", Play.DISPLAY_WIDTH / 2, 400);
        font.drawStringCentered("効果音:", Play.DISPLAY_WIDTH / 2, 500);
        font.drawStringCentered("無料効果音素材　小森平", Play.DISPLAY_WIDTH / 2, 550);
        font.drawStringCentered("フォント:", Play.DISPLAY_WIDTH / 2, 650);
        font.drawStringCentered("MPLUS1p-Medium", Play.DISPLAY_WIDTH / 2, 700);
    }
}
