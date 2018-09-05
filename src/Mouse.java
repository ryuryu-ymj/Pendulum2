import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class Mouse
{
    private static boolean justClicked;
    private static boolean down;

    void update(GameContainer gc)
    {
        if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
        {
            if (!down)
            {
                justClicked = true;
                down = true;
            }
            else
            {
                justClicked = false;
            }
        }
        else
        {
            justClicked = false;
            down = false;
        }
    }

    static boolean isJustClicked()
    {
        return justClicked;
    }

    static boolean isDown()
    {
        return down;
    }
}
