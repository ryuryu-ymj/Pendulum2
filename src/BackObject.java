import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class BackObject extends GameObject
{
    Layer layer;

    /**
     * 奥行き
     */
    private enum Layer
    {
        LAYER0(1),
        LAYER1(0.5f),
        LAYER2(0.3f),
        ;

        /**
         * 進み具合<br>
         * LAYER0に対しての割合
         */
        public final float pace;

        Layer(float pace)
        {
            this.pace = pace;
        }
    }

    BackObject()
    {
        width = 600;
        height = 600;
        abX = 300;
        abY = 660;
        layer = Layer.LAYER1;
    }

    @Override
    public void update(GameContainer gc, float cameraX, float cameraY)
    {
        changeToDisplayPoint(cameraX, cameraY, layer.pace);
    }

    @Override
    public void render(Graphics g, ImageManager im)
    {
        im.drawTree1(getDiX(), getDiY() - height / 2, width, height);
    }
}
