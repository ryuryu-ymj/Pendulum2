import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class Camera
{
	/**
	 * 画面の中心座標
	 */
	private float x, y;
	public boolean active;
	private final int moveSpeed = 5;

	Camera()
	{
		x = 0;
		y = 0;
		active = false;
	}

	public void init(int x, int y)
    {
        this.x = x;
        this.y = y;
        active = true;
    }

	public void followPlayer(float playerX, float playerY)
	{
        x += (playerX - x) / 20;
        y += (playerY - 100 - y) / 20;
	}

	public void moveByKey(GameContainer gc)
    {
        if (gc.getInput().isKeyDown(Input.KEY_RIGHT))
        {
            x += moveSpeed;
        }
        else if (gc.getInput().isKeyDown(Input.KEY_LEFT))
        {
            x -= moveSpeed;
        }
        else if (gc.getInput().isKeyDown(Input.KEY_DOWN))
        {
            y += moveSpeed;
        }
        else if (gc.getInput().isKeyDown(Input.KEY_UP))
        {
            y -= moveSpeed;
        }
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }
}
