import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

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
        for (int i = 0; i < isJointsActivate.length; i++)
        {
            isJointsActivate[i] = false;
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
        for (int i = 0; i < isBackObjectsActivate.length; i++)
        {
            isBackObjectsActivate[i] = false;
        }
        for (int i = 0; i < backObjects.length; i++)
        {
            backObjects[i].active = false;
        }
        for (int i = 0; i < isCherriesActivate.length; i++)
        {
            isCherriesActivate[i] = false;
        }
        for (int i = 0; i < cherries.length; i++)
        {
            cherries[i].active = false;
        }
        for (int i = 0; i < isHeartsActivate.length; i++)
        {
            isHeartsActivate[i] = false;
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
            camera.moveByMouse(gc);
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

    public void collisionDetection(GameContainer gc)
    {
        f:
        if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON))
        {
            for (int i = 0; i < JOINT_MAX; i++)
            {
                if (joints[i].active)
                {
                    if (gc.getInput().getMouseX() < joints[i].getDiX() + Joint.RADIUS * 2 &&
                            gc.getInput().getMouseX() > joints[i].getDiX() - Joint.RADIUS * 2)
                    {
                        if (gc.getInput().getMouseY() < joints[i].getDiY() + Joint.RADIUS * 2 &&
                                gc.getInput().getMouseY() > joints[i].getDiY() - Joint.RADIUS * 2)
                        {
                            wire.jointLockedNum = i;
                            break f;
                        }
                    }
                }
            }
        }

        if (!gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
        {
            wire.init();
        }
    }
}
