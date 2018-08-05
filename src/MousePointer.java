import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class MousePointer extends GameObject
{
    public enum Type{DELETE, GROUND, JOINT}
    public Type type;

    MousePointer()
    {
        type = Type.GROUND;
    }

    @Override
    public void update(GameContainer gc, float cameraX, float cameraY)
    {
        changeToDisplayPoint(cameraX, cameraY);
    }

    @Override
    public void render(Graphics g, ImageManager im)
    {
        switch (type)
        {
            case GROUND:
                im.drawGround(getDiX(), getDiY(), Ground.WIDTH, Ground.WIDTH);
                break;
            case JOINT:
                im.drawJoint(getDiX(), getDiY(), Joint.radius * 2, Joint.radius * 2);
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
