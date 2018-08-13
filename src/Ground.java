import org.newdawn.slick.Color;
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
     * ground のあたり判定を行うかどうか
     */
    public boolean isCheckCollision;

    /**
     * groundの型
     */
    public enum Type
    {
        /**
         * 普通
         */
        NORMAL,
        /**
         * トゲトゲ
         */
        SPINE,
        /**
         * 見えない　あたり判定のみ行う
         */
        INVISIBLE;
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
        isCheckCollision = true;
    }

    @Override
    public void update(GameContainer gc, float cameraX, float cameraY)
    {
        if (checkLeaving(60))
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
        switch (type)
        {
            case NORMAL:
                im.drawGround(getDiX(), getDiY(), width, height, shape);
                break;
            case INVISIBLE:
                break;
        }

        //g.drawString(Boolean.toString(isCheckCollision), getDiX(), getDiY());
    }

    public void renderEditVer(Graphics g, ImageManager im)
    {
        switch (type)
        {
            case NORMAL:
                im.drawGround(getDiX(), getDiY(), width, height, shape);
                break;
            case INVISIBLE:
                g.setColor(Color.red);
                g.drawRect(getDiX() - Ground.WIDTH / 2, getDiY() - Ground.WIDTH / 2, Ground.WIDTH, Ground.WIDTH);
                break;
        }
    }

    /**
     * 初期化処理
     */
    public void activate(int x, int y, Type type, Shape shape, boolean isCheckCollision, int num)
    {
        this.abX = x;
        this.abY = y;
        this.type = type;
        this.shape = shape;
        this.num = num;
        this.isCheckCollision = isCheckCollision;
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
        return isCheckCollision;
    }
}
