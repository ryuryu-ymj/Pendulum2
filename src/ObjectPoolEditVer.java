import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class ObjectPoolEditVer extends ObjectPool
{
    ObjectPoolEditVer()
    {
        super();
    }

    @Override
    public void update(GameContainer gc)
    {
        updateObjects(backObjects, gc);
        updateObjects(joints, gc);
        updateObjects(cherries, gc);
        updateObjects(hearts, gc);
        updateObjects(grounds, gc);

        if (camera.active)
        {
            camera.moveByKey(gc);
        }
    }

    @Override
    public void render(Graphics g, ImageManager im)
    {
        renderObjects(backObjects, g, im);
        renderObjects(joints, g, im);
        renderObjects(cherries, g, im);
        renderObjects(hearts, g, im);
        for (Ground ground : grounds)
        {
            if (ground.active)
            {
                ground.renderEditVer(g, im);
            }
        }
    }
}
