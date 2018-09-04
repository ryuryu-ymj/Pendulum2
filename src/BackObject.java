import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * 背景オブジェクト
 */
public class BackObject extends GameObject
{
    /**
     * backObject がステージ上のどの地面を演じているのか（backObjectX の配列番号）
     */
    private int num;
    private Layer layer;
    private Type type;

    /**
     * 奥行き
     */
    public enum Layer
    {
        LAYER0(1),
        /*LAYER1(0.8f),
        LAYER2(0.3f),*/
        ;

        /**
         * 進み具合<br>
         * LAYER0に対しての割合
         */
        public final float PACE;

        Layer(float pace)
        {
            PACE = pace;
        }
    }

    /**
     * 種類
     */
    public enum Type
    {
        //TREE(600, 600),
        GLASS1(120, 120),
        GLASS2(120, 120),
        GLASS3(120, 120),
        GLASS4(120, 120),
        ;

        public final int WIDTH;
        public final int HEIGHT;

        Type(int width, int height)
        {
            WIDTH = width;
            HEIGHT = height;
        }

        public Type next()
        {
            if (ordinal() + 1 >= values().length)
            {
                return values()[0];
            }
            return values()[ordinal() + 1];
        }
    }

    BackObject(ObjectPool objectPool)
    {
        super(objectPool);
        width = 120;
        height = 120;
        abX = 300;
        abY = 660;
        layer = Layer.LAYER0;
        type = Type.GLASS1;
        active = false;
    }

    @Override
    public void update(GameContainer gc, float cameraX, float cameraY)
    {
        changeToDisplayPoint(cameraX, cameraY, layer.PACE);
    }

    @Override
    public void render(Graphics g, ImageManager im)
    {
        switch (type)
        {
            /*case TREE:
                im.drawTree1(getDiX(), getDiY() + 2, width, height);
                break;*/
            case GLASS1:
                im.drawGlass1(getDiX(), getDiY() + 2, width, height);
                break;
            case GLASS2:
                im.drawGlass2(getDiX(), getDiY() + 2, width, height);
                break;
            case GLASS3:
                im.drawGlass3(getDiX(), getDiY() + 2, width, height);
                break;
            case GLASS4:
                im.drawGlass4(getDiX(), getDiY() + 2, width, height);
                break;
        }
    }
    public static void renderIcon(Graphics g, ImageManager im, int x, int y, Type type)
    {
        switch (type)
        {
            /*case TREE:
                im.drawTree1(getDiX(), getDiY() + 2, width, height);
                break;*/
            case GLASS1:
                im.drawGlass1(x, y, 60, 60);
                break;
            case GLASS2:
                im.drawGlass2(x, y, 60, 60);
                break;
            case GLASS3:
                im.drawGlass3(x, y, 60, 60);
                break;
            case GLASS4:
                im.drawGlass4(x, y, 60, 60);
                break;
        }
    }

    /**
     * 初期化処理
     *
     * @param x     オブジェクトの下部中央のx座標
     * @param y     オブジェクトの下部中央のy座標
     * @param layer 奥行き
     * @param type  種類
     * @param num   backObject がステージ上のどの地面を演じているのか（backObjectX の配列番号）
     */
    public void activate(int x, int y, Type type, Layer layer, int num)
    {
        this.abX = x;
        this.abY = y;
        this.layer = layer;
        this.type = type;
        this.num = num;
        width = type.WIDTH;
        height = type.HEIGHT;
        active = true;
    }

    public Layer getLayer()
    {
        return layer;
    }

    public Type getType()
    {
        return type;
    }
}
