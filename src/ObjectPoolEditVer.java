import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class ObjectPoolEditVer extends ObjectPool
{
    @Override
    public void init()
    {
        camera.init(200, 200);
        reload();
    }

    public void reload()
    {
        for (int i = 0; i < isJointDisplayed.length; i++)
        {
            isJointDisplayed[i] = false;
        }
        for (int i = 0; i < joints.length; i++)
        {
            joints[i].active = false;
        }
        for (int i = 0; i < isGroundDisplayed.length; i++)
        {
            isGroundDisplayed[i] = false;
        }
        for (int i = 0; i < grounds.length; i++)
        {
            grounds[i].active = false;
        }
        for (int i = 0; i < isBackObjectDisplayed.length; i++)
        {
            isBackObjectDisplayed[i] = false;
        }
        for (int i = 0; i < backObjects.length; i++)
        {
            backObjects[i].active = false;
        }
    }

    @Override
    public void update(GameContainer gc)
    {
        updateObjects(backObjects, gc);
        updateObjects(joints, gc);
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
        renderObjects(grounds, g, im);
    }
}
