import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/**
 * マウスポインタ<br>
 * Editクラスにおいて，マウスのカーソルに合わせたオブジェクトの表示，管理など
 */
public class MousePointer extends GameObject
{
    Ground ground;
    Joint joint;
    BackObject backObject;
    Cherry cherry;
    Heart heart;

    GameObject gameObject;

    /**
     * ゲームオブジェクトを置けるかどうか
     */
    public boolean canPutObject;
    public boolean isDragging;

    public enum Type
    {
        OPERATE, GROUND, JOINT, BACK_OBJECT, CHERRY, HEART, DELETE, JOINT_LOCK_RADIUS
    }

    private Type type;

    MousePointer(ObjectPool objectPool)
    {
        super(objectPool);
        type = Type.OPERATE;
        ground = new Ground(objectPool);
        joint = new Joint(objectPool.player, objectPool);
        backObject = new BackObject(objectPool);
        cherry = new Cherry(objectPool);
        heart = new Heart(objectPool);
        gameObject = ground;
        isDragging = false;
        canPutObject = true;
    }

    @Override
    public void update(GameContainer gc, float cameraX, float cameraY)
    {
        if (gc.getInput().getMouseY() < 80)
        {
            canPutObject = false;
        }
        else
        {
            canPutObject = true;
        }

        if (isDragging)
        {
            canPutObject = false;
        }
        else
        {
            canPutObject = true;
        }

        if (gc.getInput().isKeyPressed(Input.KEY_G))
        {
            type = Type.GROUND;
            gameObject = ground;
        }
        else if (gc.getInput().isKeyPressed(Input.KEY_J))
        {
            type = Type.JOINT;
            joint.activate((int) abX, (int) abY, joint.getType(), 0, false, 0);
            gameObject = joint;
        }
        else if (gc.getInput().isKeyPressed(Input.KEY_D))
        {
            type = Type.DELETE;
        }
        else if (gc.getInput().isKeyPressed(Input.KEY_B))
        {
            type = Type.BACK_OBJECT;
            gameObject = backObject;
        }
        else if (gc.getInput().isKeyPressed(Input.KEY_C))
        {
            type = Type.CHERRY;
            gameObject = cherry;
        }
        else if (gc.getInput().isKeyPressed(Input.KEY_H))
        {
            type = Type.HEART;
            gameObject = heart;
        }
        else if (gc.getInput().isKeyPressed(Input.KEY_O))
        {
            type = Type.OPERATE;
        }
        else if (gc.getInput().isKeyPressed(Input.KEY_R))
        {
            type = Type.JOINT_LOCK_RADIUS;
        }
        else if (gc.getInput().isKeyPressed(Input.KEY_ENTER))
        {
            switch (type)
            {
                case GROUND:
                    ground.activate((int) abX, (int) abY, ground.getType().next(), new Ground.Position(), 0);
                    break;
                case JOINT:
                    joint.activate((int) abX, (int) abY, joint.getType().next(), 0, false, 0);
                    break;
                case BACK_OBJECT:
                    backObject.activate((int) abX, (int) abY, backObject.getType().next(), backObject.getLayer(), 0);
                    break;
            }
        }

        gameObject.abX = abX;
        gameObject.abY = abY;
        gameObject.update(gc, cameraX, cameraY);
        gameObject.changeToDisplayPoint(cameraX, cameraY);
        changeToDisplayPoint(cameraX, cameraY);

        //System.out.println(getClass().toString());
    }

    @Override
    public void render(Graphics g, ImageManager im)
    {
        switch (type)
        {
            case DELETE:
                im.drawDelete(getDiX(), getDiY());
                break;
            case OPERATE:
                break;
            case JOINT_LOCK_RADIUS:
                g.setColor(Color.blue);
                g.drawOval(getDiX() - Joint.RADIUS, getDiY() - Joint.RADIUS, Joint.RADIUS * 2, Joint.RADIUS * 2);
                break;
            case GROUND:
                ground.renderEditVer(g, im);
                break;
            default:
                gameObject.render(g, im);
                break;
        }
    }

    public void setPointer(float abX, float abY)
    {
        this.abX = abX;
        this.abY = abY;
    }

    public Type getType()
    {
        return type;
    }

    public void setType(GameObject gameObject)
    {
        if (gameObject == null)
        {
            type = Type.OPERATE;
            return;
        }

        this.gameObject = gameObject;
        switch (gameObject.getClassName())
        {
            case "Ground":
                type = Type.GROUND;
                this.ground = (Ground) gameObject;
                break;
            case "Joint":
                type = Type.JOINT;
                this.joint = (Joint) gameObject;
                break;
            case "Cherry":
                type = Type.CHERRY;
                this.cherry = (Cherry) gameObject;
                break;
            case "Heart":
                type = Type.HEART;
                this.heart = (Heart) gameObject;
                break;
            case "BackObject":
                type = Type.BACK_OBJECT;
                this.backObject = (BackObject) gameObject;
                break;
        }
        //System.out.println(gameObject.getClassName());
    }

    public void setType(Type type)
    {
        this.type = type;
    }
}
