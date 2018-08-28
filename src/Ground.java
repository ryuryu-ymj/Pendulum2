import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * 地面クラス
 */
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
    private Position position;
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

        public Type next()
        {
            if (ordinal() + 1 >= values().length)
            {
                return values()[0];
            }
            return values()[ordinal() + 1];
        }
    }

    public static class Position
    {
        public boolean hasTop, hasBottom, hasLeft, hasRight;

        Position(boolean hasTop, boolean hasBottom, boolean hasLeft, boolean hasRight)
        {
            this.hasTop = hasTop;
            this.hasBottom = hasBottom;
            this.hasLeft = hasLeft;
            this.hasRight = hasRight;
        }

        Position()
        {
            this(false, false, false, false);
        }

        public String toString()
        {
            return hasTop + "," + hasBottom + "," + hasLeft + "," + hasRight;
        }

        public boolean isCheckCollision()
        {
            if (hasTop && hasBottom && hasLeft && hasRight)
            {
                return false;
            }
            return true;
        }
    }

    /**
     * コンストラクタ
     */
    Ground(ObjectPool objectPool)
    {
        super(objectPool);
        active = false;
        this.width = WIDTH;
        this.height = WIDTH;
        type = Type.NORMAL;
        position = new Position();
        isCheckCollision = true;
    }

    @Override
    public void update(GameContainer gc, float cameraX, float cameraY)
    {
        if (checkLeaving(60))
        {
            active = false;
            objectPool.isGroundDisplayed[num] = false;
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
                im.drawGround(getDiX(), getDiY(), width, height, position);
                break;
            case SPINE:
                im.drawSpine(getDiX(), getDiY(), width, height, position);
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
                im.drawGround(getDiX(), getDiY(), width, height, position);
                break;
            case SPINE:
                im.drawSpine(getDiX(), getDiY(), width, height, position);
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
    public void activate(int x, int y, Type type, Position position, int num)
    {
        this.abX = x;
        this.abY = y;
        this.type = type;
        this.position = position;
        this.num = num;
        active = true;
        isCheckCollision = position.isCheckCollision();
    }

    public Type getType()
    {
        return type;
    }

    public Position getPosition()
    {
        return position;
    }

    public boolean isCheckCollision()
    {
        return isCheckCollision;
    }
}
