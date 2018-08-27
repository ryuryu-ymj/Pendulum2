import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class ObjectPoolEditVer extends ObjectPool
{
    ObjectPoolEditVer()
    {
        super();
    }

    @Override
    public void init()
    {
        player.init(200, 200);
        camera.active = true;
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
        for (int i = 0; i < isCherryDisplayed.length; i++)
        {
            isCherryDisplayed[i] = false;
        }
        for (int i = 0; i < cherries.length; i++)
        {
            cherries[i].active = false;
        }
        for (int i = 0; i < isHeartDisplayed.length; i++)
        {
            isHeartDisplayed[i] = false;
        }
        for (int i = 0; i < hearts.length; i++)
        {
            hearts[i].active = false;
        }
    }

    @Override
    public void update(GameContainer gc)
    {
        updateObjects(backObjects, gc);
        updateObjects(joints, gc);
        updateObjects(cherries, gc);
        updateObjects(hearts, gc);
        updateObjects(grounds, gc);
        player.changeToDisplayPoint(camera.getX(), camera.getY());

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
        player.render(g, im);
    }
}
