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
    private Image groundGlass;
    private Image groundGlassSideEdge;
    private Image groundGlassCornerEdge;
    private Image groundGlassAllEdge;
    private Image groundGlassTopEdge;
    private Image groundNoGlass;
    private Image groundNoGlassBottomEdge;
    private Image groundNoGlassCornerEdge;
    private Image background;
    private Image glass1;
    private Image glass2;
    private Image glass3;
    private Image glass4;
    private Image cherry;
    private Image heart;
    private Image bee;
    private Image sting;

    ImageManager()
    {
        try
        {
            SpriteSheet ss = new SpriteSheet("res/img/object.png", 295, 295);
            player = ss.getSubImage(0, 0);
            joint = ss.getSubImage(1, 0);
            bee = ss.getSubImage(2, 0);
            sting = ss.getSubImage(3, 0);
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
            SpriteSheet ss = new SpriteSheet("res/img/grounds.png", 100, 100);
            groundGlass = ss.getSubImage(0, 0);
            groundGlassSideEdge = ss.getSubImage(1, 0);
            groundGlassCornerEdge = ss.getSubImage(2, 0);
            groundGlassAllEdge = ss.getSubImage(3, 0);
            groundGlassTopEdge = ss.getSubImage(4, 0);
            groundNoGlass = ss.getSubImage(5, 0);
            groundNoGlassBottomEdge = ss.getSubImage(6, 0);
            groundNoGlassCornerEdge = ss.getSubImage(7, 0);
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

        try
        {
            glass1 = new Image("res/img/glass1.png");
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }

        try
        {
            glass2 = new Image("res/img/glass2.png");
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }

        try
        {
            glass3 = new Image("res/img/glass3.png");
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }

        try
        {
            glass4 = new Image("res/img/glass4.png");
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }

        try
        {
            SpriteSheet ss = new SpriteSheet("res/img/item.png", 50, 50);
            cherry = ss.getSubImage(0, 0);
            heart = ss.getSubImage(1, 0);
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
        float margin = 59 * width / (player.getWidth() - 59 * 2);
        player.setCenterOfRotation(width / 2 + margin, height / 2 + margin);
        player.setRotation(angle);
        player.draw(x - width / 2 - margin, y - height / 2 - margin, width + margin * 2, height + margin * 2);
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
        float margin = 108.56f * width / (player.getWidth() - 108.56f * 2);
        joint.draw(x - width / 2 - margin, y - height / 2 - margin, width + margin * 2, height + margin * 2);
    }

    /**
     * bee の画像を表示する
     *
     * @param x      中心点のx座標
     * @param y      中心点のy座標
     * @param width  中心のjointの横幅
     * @param height 中心のjointの縦幅
     */
    public void drawBee(float x, float y, float width, float height, float angle)
    {
        float margin = 108.56f * width / (player.getWidth() - 108.56f * 2);
        bee.setCenterOfRotation(width / 2 + margin, height / 2 + margin);
        bee.setRotation(angle);
        bee.draw(x - width / 2 - margin, y - height / 2 - margin, width + margin * 2, height + margin * 2);
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
     * @param type   種類
     */
    public void drawGround(float x, float y, float width, float height, Ground.Shape type)
    {
        switch (type)
        {
            case GLASS:
                groundGlass.draw(x - width / 2, y - height / 2, width, height);
                break;
            case GLASS_TOP_EDGE:
                groundGlassTopEdge.draw(x - width / 2, y - height / 2, width, height);
                break;
            case GLASS_TOP_LEFT_EDGE:
                groundGlassCornerEdge.draw(x + width / 2, y - height / 2, -width, height);
                break;
            case GLASS_TOP_RIGHT_EDGE:
                groundGlassCornerEdge.draw(x - width / 2, y - height / 2, width, height);
                break;
            case GLASS_LEFT_EDGE:
                groundGlassSideEdge.draw(x + width / 2, y - height / 2, -width, height);
                break;
            case GLASS_RIGHT_EDGE:
                groundGlassSideEdge.draw(x - width / 2, y - height / 2, width, height);
                break;
            case GLASS_ALL_EDGE:
                groundGlassAllEdge.draw(x - width / 2, y - height / 2, width, height);
                break;
            case NO_GLASS:
                groundNoGlass.draw(x - width / 2, y - height / 2, width, height);
                break;
            case NO_GLASS_BOTTOM_EDGE:
                groundNoGlassBottomEdge.draw(x - width / 2, y - height / 2, width, height);
                break;
            case NO_GLASS_BOTTOM_LEFT_EDGE:
                groundNoGlassCornerEdge.draw(x + width / 2, y - height / 2, -width, height);
                break;
            case NO_GLASS_BOTTOM_RIGHT_EDGE:
                groundNoGlassCornerEdge.draw(x - width / 2, y - height / 2, width, height);
                break;
        }
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

    /**
     * glass1 の画像を表示する
     *
     * @param x      中心点のx座標
     * @param y      中心点のy座標
     * @param width  横幅
     * @param height 縦幅
     */
    public void drawGlass1(float x, float y, float width, float height)
    {
        glass1.draw(x - width / 4 * 3, y - height / 4 * 3, width, height);
    }

    /**
     * glass2 の画像を表示する
     *
     * @param x      中心点のx座標
     * @param y      中心点のy座標
     * @param width  横幅
     * @param height 縦幅
     */
    public void drawGlass2(float x, float y, float width, float height)
    {
        glass2.draw(x - width / 4 * 3, y - height / 4 * 3, width, height);
    }

    /**
     * glass3 の画像を表示する
     *
     * @param x      中心点のx座標
     * @param y      中心点のy座標
     * @param width  横幅
     * @param height 縦幅
     */
    public void drawGlass3(float x, float y, float width, float height)
    {
        glass3.draw(x - width / 4 * 3, y - height / 4 * 3, width, height);
    }

    /**
     * glass3 の画像を表示する
     *
     * @param x      中心点のx座標
     * @param y      中心点のy座標
     * @param width  横幅
     * @param height 縦幅
     */
    public void drawGlass4(float x, float y, float width, float height)
    {
        glass4.draw(x - width / 4 * 3, y - height / 4 * 3, width, height);
    }

    /**
     * cherry の画像を表示する
     *
     * @param x      中心点のx座標
     * @param y      中心点のy座標
     * @param width  横幅
     * @param height 縦幅
     */
    public void drawCherry(float x, float y, float width, float height)
    {
        float margin = 10 * width / 50;
        cherry.draw(x - width / 2 - margin, y - height / 2 - margin, width + margin * 2, height + margin * 2);
    }

    /**
     * heart の画像を表示する
     *
     * @param x      中心点のx座標
     * @param y      中心点のy座標
     * @param width  横幅
     * @param height 縦幅
     */
    public void drawHeart(float x, float y, float width, float height)
    {
        float margin = 10 * width / 50;
        heart.draw(x - width / 2 - margin, y - height / 2 - margin, width + margin * 2, height + margin * 2);
    }
}
