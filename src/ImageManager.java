import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * ゲームで使う画像の管理、描画
 *
 * @author ryuryu
 */
public class ImageManager
{
    private Image player;
    private Image joint;
    private Image tree1;
    private Image ground;
    private Image background;

    ImageManager()
    {
        try
        {
            SpriteSheet ss = new SpriteSheet("res/img/object.png", 295, 295);
            player = ss.getSubImage(0, 0);
            joint = ss.getSubImage(1, 0);
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }

        try
        {
            tree1 = new Image("res/img/tree1.png");
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }

        try
        {
            ground = new Image("res/img/ground.png");
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }

        try
        {
            background = new Image("res/img/background.png");
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * player の画像を表示する
     *
     * @param x      中心点のx座標
     * @param y      中心点のy座標
     * @param width  横幅
     * @param height 縦幅
     * @param angle  角度　ラジアン
     */
    public void drawPlayer(float x, float y, float width, float height, float angle)
    {
        float mergin = 59 * width / (player.getWidth() - 59 * 2);
        player.setCenterOfRotation(width / 2 + mergin, height / 2 + mergin);
        player.setRotation(angle);
        player.draw(x - width / 2 - mergin, y - height / 2 - mergin, width + mergin * 2, height + mergin * 2);
    }

    /**
     * joint の画像を表示する
     *
     * @param x      中心点のx座標
     * @param y      中心点のy座標
     * @param width  横幅
     * @param height 縦幅
     */
    public void drawJoint(float x, float y, float width, float height)
    {
        float mergin = 108.56f * width / (player.getWidth() - 108.56f * 2);
        joint.draw(x - width / 2 - mergin, y - height / 2 - mergin, width + mergin * 2, height + mergin * 2);
    }

    /**
     * tree1 の画像を表示する
     *
     * @param x      中心点のx座標
     * @param y      中心点のy座標
     * @param width  横幅
     * @param height 縦幅
     */
    public void drawTree1(float x, float y, float width, float height)
    {
        //tree1.setAlpha(0.8f);
        tree1.draw(x - width / 2, y - height / 2, width, height);
    }

    /**
     * ground の画像を表示する
     *
     * @param x      中心点のx座標
     * @param y      中心点のy座標
     * @param width  横幅
     * @param height 縦幅
     */
    public void drawGround(float x, float y, float width, float height)
    {
        ground.draw(x - width / 2, y - height / 2, width, height);
    }

    /**
     * ground の画像を表示する
     *
     * @param x      中心点のx座標
     * @param y      中心点のy座標
     * @param width  横幅
     * @param height 縦幅
     */
    public void drawBackGround(float x, float y, float width, float height)
    {
        background.draw(x - width / 2, y - height / 2, width, height);
    }
}
