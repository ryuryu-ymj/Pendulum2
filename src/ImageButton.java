import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

public class ImageButton
{
    /**
     * 中心点の座標
     */
    private int x, y;
    private int width, height;
    /**
     * ボタンが押されたかどうか
     */
    private boolean isPressed;

    ImageButton(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void checkPressed(GameContainer gc)
    {
        if (Mouse.isJustClicked())
        {
            if (gc.getInput().getMouseX() > x - width / 2 && gc.getInput().getMouseX() < x + width / 2)
            {
                if (gc.getInput().getMouseY() > y - height / 2 && gc.getInput().getMouseY() < y + height / 2)
                {
                    isPressed = true;
                    return;
                }
            }
        }
        isPressed = false;
    }

    public void render(Image image)
    {
        image.draw(x - width / 2, y - height / 2, width, height);
    }

    public boolean isPressed()
    {
        return isPressed;
    }
}
