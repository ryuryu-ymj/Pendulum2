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
    //public boolean canPutObject;

    public enum Type
    {
        GROUND, JOINT, BACK_OBJECT, CHERRY, HEART, DELETE, OPERATE
    }
    private Type type;

    MousePointer(ObjectPool objectPool)
    {
        super(objectPool);
        type = Type.GROUND;
        ground = new Ground(objectPool);
        joint = new Joint(objectPool.player, objectPool);
        backObject = new BackObject(objectPool);
        cherry = new Cherry(objectPool);
        heart = new Heart(objectPool);
        gameObject = ground;
        //canPutObject = true;
    }

    @Override
    public void update(GameContainer gc, float cameraX, float cameraY)
    {
        if (gc.getInput().isKeyPressed(Input.KEY_G))
        {
            type = Type.GROUND;
            gameObject = ground;
        }
        else if (gc.getInput().isKeyPressed(Input.KEY_J))
        {
            type = Type.JOINT;
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
        else if (gc.getInput().isKeyPressed(Input.KEY_ENTER))
        {
            switch (type)
            {
                case GROUND:
                    ground.activate((int) abX, (int) abY, ground.getType().next(), new Ground.Position(), 0);
                    break;
                case JOINT:
                    joint.activate((int) abX, (int) abY, joint.getType().next(), joint.getLockRadius(), false, 0);
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
    }

    @Override
    public void render(Graphics g, ImageManager im)
    {
        switch (type)
        {
            case DELETE:
                g.setColor(Color.red);
                g.drawOval(getDiX() - Ground.WIDTH / 2, getDiY() - Ground.WIDTH / 2, Ground.WIDTH, Ground.WIDTH);
                break;
            case OPERATE:
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

    public void setType(Type type)
    {
        this.type = type;
        switch (type)
        {
            case GROUND:
                gameObject = ground;
                break;
            case JOINT:
                gameObject = joint;
                break;
            case CHERRY:
                gameObject = cherry;
                break;
            case HEART:
                gameObject = heart;
                break;
            case BACK_OBJECT:
                gameObject = backObject;
                break;
        }
    }
}
