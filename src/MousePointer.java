import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class MousePointer extends GameObject
{
    public enum Type
    {
        DELETE, GROUND_NORMAL, GROUND_INVISIBLE, JOINT_NORMAL, JOINT_GOAL, GLASS1, GLASS2, GLASS3, GLASS4, CHERRY
    }
    public Type type;

    MousePointer()
    {
        type = Type.GROUND_NORMAL;
    }

    @Override
    public void update(GameContainer gc, float cameraX, float cameraY)
    {
        if (gc.getInput().isKeyPressed(Input.KEY_G))
        {
            type = Type.GROUND_NORMAL;
        }
        else if (gc.getInput().isKeyPressed(Input.KEY_J))
        {
            type = Type.JOINT_NORMAL;
        }
        else if (gc.getInput().isKeyPressed(Input.KEY_D))
        {
            type = Type.DELETE;
        }
        else if (gc.getInput().isKeyPressed(Input.KEY_B))
        {
            type = Type.GLASS1;
        }
        else if (gc.getInput().isKeyPressed(Input.KEY_C))
        {
            type = Type.CHERRY;
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

            if (type == Type.GROUND_NORMAL)
            {
                type = Type.GROUND_INVISIBLE;
            }
            else if (type == Type.GROUND_INVISIBLE)
            {
                type = Type.GROUND_NORMAL;
            }

            if (type == Type.GLASS1)
            {
                type = Type.GLASS2;
            }
            else if (type == Type.GLASS2)
            {
                type = Type.GLASS3;
            }
            else if (type == Type.GLASS3)
            {
                type = Type.GLASS4;
            }
            else if (type == Type.GLASS4)
            {
                type = Type.GLASS1;
            }
        }

        changeToDisplayPoint(cameraX, cameraY);
    }

    @Override
    public void render(Graphics g, ImageManager im)
    {
        switch (type)
        {
            case GROUND_NORMAL:
                im.drawGround(getDiX(), getDiY(), Ground.WIDTH, Ground.WIDTH, Ground.Shape.NO_GLASS);
                break;
            case GROUND_INVISIBLE:
                g.setColor(Color.red);
                g.drawRect(getDiX() - Ground.WIDTH / 2, getDiY() - Ground.WIDTH / 2, Ground.WIDTH, Ground.WIDTH);
                break;
            case JOINT_NORMAL:
                im.drawJoint(getDiX(), getDiY(), Joint.RADIUS * 2, Joint.RADIUS * 2);
                break;
            case JOINT_GOAL:
                g.setColor(Color.red);
                g.drawOval(getDiX() - Joint.RADIUS, getDiY() - Joint.RADIUS, Joint.RADIUS * 2, Joint.RADIUS * 2);
                break;
            case GLASS1:
                im.drawGlass1(getDiX(), getDiY(), BackObject.Type.GLASS1.WIDTH, BackObject.Type.GLASS1.HEIGHT);
                break;
            case GLASS2:
                im.drawGlass2(getDiX(), getDiY(), BackObject.Type.GLASS2.WIDTH, BackObject.Type.GLASS2.HEIGHT);
                break;
            case GLASS3:
                im.drawGlass3(getDiX(), getDiY(), BackObject.Type.GLASS3.WIDTH, BackObject.Type.GLASS3.HEIGHT);
                break;
            case GLASS4:
                im.drawGlass4(getDiX(), getDiY(), BackObject.Type.GLASS4.WIDTH, BackObject.Type.GLASS4.HEIGHT);
                break;
            case CHERRY:
                im.drawCherry(getDiX(), getDiY(), Cherry.RADIUS * 2, Cherry.RADIUS * 2);
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
