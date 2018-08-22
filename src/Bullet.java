import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
* 敵弾クラス<p>
* 移動処理、描画処理など
*/
public class Bullet extends GameObject
{
	public final int RADIUS = 4;
	private float direction;
	private float speed;
	private float speedX;
	private float speedY;

	Bullet(ObjectPool objectPool)
	{
	    super(objectPool);
		active = false;
		width = RADIUS * 2;
		height = RADIUS * 2;
	}

    @Override
    public void update(GameContainer gc, float cameraX, float cameraY)
    {
        abX += speedX;
        abY += speedY;

        if (checkLeaving(0))
        {
            active = false;
        }
        changeToDisplayPoint(cameraX, cameraY);
    }

    @Override
    public void render(Graphics g, ImageManager im)
    {
        g.setColor(Color.blue);
        g.drawOval((int)(getDiX() - RADIUS), (int)(getDiY() - RADIUS), RADIUS * 2, RADIUS * 2);
    }

	/**
	 * インスタンスを有効にする。インスタンスの使い回しをしているので、
	 * 初期化処理もここで行う。
	 * @param x 生成する位置(X座標)
	 * @param y 生成する位置(Y座標)
	 * @param direction 向き(単位は度　0-360)
	 * @param speed 速度(単位はピクセル)
	 */
	public void activate(float x, float y, float direction, float speed)
	{
		abX = x;
		abY = y;
		this.direction = direction;
		this.speed = speed;
		active = true;


		double radian;
		radian = Math.toRadians(this.direction);
		speedX = (float) (this.speed * Math.cos(radian));
		speedY = (float) (this.speed * Math.sin(radian));
	}
}

