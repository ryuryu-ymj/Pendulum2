import org.newdawn.slick.*;

/**
 * タイトル画面の更新、描画を行うクラス
 *
 * @author ryuryu
 */
public class Title extends GameState
{
    private Image image;
    private Button playButton;
    private Button editButton;
    private Button creditButton;

    Title()
    {
        playButton = new Button(Play.DISPLAY_WIDTH / 2, 500, 400, 60, "PLAY");
        editButton = new Button(Play.DISPLAY_WIDTH / 2, 600, 400, 60, "EDIT");
        creditButton = new Button(Play.DISPLAY_WIDTH / 2, 700, 400, 60, "CREDIT");

        try
        {
            image = new Image("res/img/title.png");
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void init(GameContainer gc) throws SlickException
    {
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException
    {
        if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON))
        {
            playButton.update(gc);
            editButton.update(gc);
            creditButton.update(gc);
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g, ImageManager im, FontManager fm) throws SlickException
    {
        image.draw(0, 0, 1200, 900);
        playButton.render(g, im, fm);
        editButton.render(g, im, fm);
        creditButton.render(g, im, fm);
    }

    public boolean isGoToPlay()
    {
        return playButton.isPressed();
    }

    public boolean isGoToEdit()
    {
        return editButton.isPressed();
    }
}
