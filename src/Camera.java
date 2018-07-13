public class Camera
{
	/**
	 * 画面の中心座標
	 */
	private float x, y;
	public boolean active;

	Camera()
	{
		x = 0;
		y = 0;
		active = false;
	}

	public void update(float playerX, float playerY)
	{
        x += (playerX - x) / 20;
        y += (playerY - 100 - y) / 20;
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
