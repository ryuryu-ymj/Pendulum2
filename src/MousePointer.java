import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class MousePointer extends GameObject
{
    public enum Type{DELETE, GROUND, JOINT_NORMAL, JOINT_GOAL}
    public Type type;

    MousePointer()
    {
        type = Type.GROUND;
    }

    @Override
    public void update(GameContainer gc, float cameraX, float cameraY)
    {
        if (gc.getInput().isKeyPressed(Input.KEY_G))
        {
            type = Type.GROUND;
        }
        else if (gc.getInput().isKeyPressed(Input.KEY_J))
        {
            type = Type.JOINT_NORMAL;
        }
        else if (gc.getInput().isKeyPressed(Input.KEY_D))
        {
            type = Type.DELETE;
        }
        else if (gc.getInput().isKeyPressed(Input.KEY_ENTER))
        {
            if (type == Type.JOINT_NORMAL)
            {
                type = Type.JOINT_GOAL;
            }
            else if (type == Type.JOINT_GOAL)
            {
                type = Type.JOINT_NORMAL;
            }
        }

        changeToDisplayPoint(cameraX, cameraY);
    }

    @Override
    public void render(Graphics g, ImageManager im)
    {
        switch (type)
        {
            case GROUND:
                im.drawGround(getDiX(), getDiY(), Ground.WIDTH, Ground.WIDTH, Ground.Shape.NO_GLASS);
                break;
            case JOINT_NORMAL:
                im.drawJoint(getDiX(), getDiY(), Joint.radius * 2, Joint.radius * 2);
                break;
            case JOINT_GOAL:
                g.setColor(Color.red);
                g.drawOval(getDiX() - Joint.radius, getDiY() - Joint.radius, Joint.radius * 2, Joint.radius * 2);
                break;
            case DELETE:
                g.setColor(Color.red);
                g.drawOval(getDiX() - Ground.WIDTH / 2, getDiY() - Ground.WIDTH / 2, Ground.WIDTH, Ground.WIDTH);
                break;
        }
    }

    public void setPointer(float abX, float abY)
    {
        this.abX = abX;
        this.abY = abY;
    }
}
