import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Ground extends GameObject
{
    /**
     * ground がステージ上のどの地面を演じているのか（groundX の配列番号）
     */
    private int num;
    /**
     * groundの型
     */
    private Type type;
    /**
     * groundの位置
     */
    private Shape shape;
    /**
     * groundの縦幅，横幅
     */
    public static final int WIDTH = 60;

    /**
     * groundの型
     */
    public enum Type
    {
        /** 普通 */
        NORMAL,
        /** トゲトゲ */
        SPINE,
    }
    /**
     * groundの形
     */
    public enum Shape
    {
        GLASS, GLASS_TOP_LEFT_EDGE, GLASS_TOP_RIGHT_EDGE, GLASS_TOP_EDGE, GLASS_LEFT_EDGE, GLASS_RIGHT_EDGE, GLASS_ALL_EDGE, NO_GLASS, NO_GLASS_BOTTOM_EDGE, NO_GLASS_BOTTOM_LEFT_EDGE, NO_GLASS_BOTTOM_RIGHT_EDGE
    }

    /**
     * コンストラクタ
     */
    Ground()
    {
        active = false;
        this.width = WIDTH;
        this.height = WIDTH;
        type = Type.NORMAL;
        shape = Shape.NO_GLASS;
    }

    @Override
    public void update(GameContainer gc, float cameraX, float cameraY)
    {
        if (checkLeaving(0))
        {
            active = false;
            ObjectPool.isGroundDisplayed[num] = false;
            //System.out.println("delate " + num + " " + (int)abX / 55 + "," + (int)abY / 55);
        }
        changeToDisplayPoint(cameraX, cameraY);
        //System.out.print((int)abX / 55 + "," + (int)abY / 55 + " ");
    }

    @Override
    public void render(Graphics g, ImageManager im)
    {
        /*g.setColor(Color.green);
        g.drawRect(getDiX() - width / 2, getDiY() - height / 2, width, height);*/
        im.drawGround(getDiX(), getDiY(), width, height, shape);
    }

    /**
     * 初期化処理
     *
     * @param x
     * @param y
     * @param type 0:ノーマル 1:トゲトゲ
     */
    public void activate(int x, int y, Type type, Shape shape, int num)
    {
        this.abX = x;
        this.abY = y;
        this.type = type;
        this.shape = shape;
        this.num = num;
        active = true;
    }

    public Type getType()
    {
        return type;
    }

    public Shape getShape()
    {
        return shape;
    }

    public boolean isCheckCollision()
    {
        return shape != Shape.NO_GLASS;
    }
}
