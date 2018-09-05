import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class ToolBar
{
    Menu selectedMenu;
    Ground ground;
    Joint joint;
    BackObject backObject;
    Cherry cherry;
    Heart heart;
    private int cameraX = Play.DISPLAY_WIDTH / 2;
    private int cameraY = Play.DISPLAY_HEIGHT / 2;
    private boolean isJointLockRadius;
    private TextButton playButton;
    private boolean addStageNum;
    private boolean subStageNum;

    enum Menu
    {
        OPERATE, GROUND, JOINT, BACK_OBJECT, CHERRY, HEART, DELETE;

        public int getMenuCenterX()
        {
            return 350 + ordinal() * 100;
        }

        public int getMenuCenterY()
        {
            return 40;
        }

        public MousePointer.Type getType()
        {
            try
            {
                return MousePointer.Type.valueOf(toString());
            }
            catch (IllegalArgumentException e)
            {
                return MousePointer.Type.OPERATE;
            }
        }
    }

    ToolBar()
    {
        ObjectPool objectPool = new ObjectPool();
        objectPool.create(objectPool);
        ground = new Ground(objectPool);
        joint = new Joint(objectPool.player, objectPool);
        backObject = new BackObject(objectPool);
        cherry = new Cherry(objectPool);
        heart = new Heart(objectPool);
        playButton = new TextButton(1100, 40, 130, 60, "play");
        playButton.setColor(new Color(0.4f, 0.23f, 0), new Color(28f / 256, 187f / 256, 227f / 256));
        init();
    }

    public void init()
    {
        selectedMenu = Menu.OPERATE;
    }

    public void update(GameContainer gc, MousePointer mousePointer)
    {
        check:
        if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON))
        {
            for (Menu menu : Menu.values())
            {
                if (gc.getInput().getMouseX() > menu.getMenuCenterX() - 40 &&
                        gc.getInput().getMouseX() < menu.getMenuCenterX() + 40)
                {
                    if (gc.getInput().getMouseY() > menu.getMenuCenterY() - 40 &&
                            gc.getInput().getMouseY() < menu.getMenuCenterY() + 40)
                    {
                        if (selectedMenu != menu)
                        {
                            selectedMenu = menu;
                        }
                        else
                        {
                            switch (menu)
                            {
                                case GROUND:
                                    ground.activate((int) ground.abX, (int) ground.abY, ground.getType().next()
                                            , new Ground.Position(), 0);
                                    break;
                                case JOINT:
                                    if (isJointLockRadius)
                                    {
                                        joint.activate((int) joint.abX, (int) joint.abY, Joint.Type.NORMAL
                                                , 0, false, 0);
                                        isJointLockRadius = false;
                                    }
                                    else if (joint.getType() == Joint.Type.HEART_IN)
                                    {
                                        mousePointer.setType(MousePointer.Type.JOINT_LOCK_RADIUS);
                                        isJointLockRadius = true;
                                        break check;
                                    }
                                    else
                                    {
                                        joint.activate((int) joint.abX, (int) joint.abY, joint.getType().next()
                                                , 0, false, 0);
                                        break;
                                    }
                                case BACK_OBJECT:
                                    backObject.activate((int) backObject.abX, (int) backObject.abY, backObject.getType().next()
                                            , backObject.getLayer(), 0);
                                    break;
                            }
                        }

                        setMousePointer(mousePointer);
                        break;
                    }
                }
            }

            playButton.checkDown(gc);

            if (gc.getInput().getMouseY() > 30 &&
                    gc.getInput().getMouseY() < 50)
            {
                if (gc.getInput().getMouseX() > 160 &&
                        gc.getInput().getMouseX() < 180)
                {
                    subStageNum = true;
                }
                else if (gc.getInput().getMouseX() > 240 &&
                        gc.getInput().getMouseX() < 260)
                {
                    addStageNum = true;
                }
            }
        }

        joint.update(gc, cameraX, cameraY);
        ground.update(gc, cameraX, cameraY);
        backObject.update(gc, cameraX, cameraY);
    }

    public boolean isAddStageNum()
    {
        if (addStageNum)
        {
            addStageNum = false;
            return true;
        }
        return false;
    }

    public boolean isSubStageNum()
    {
        if (subStageNum)
        {
            subStageNum = false;
            return true;
        }
        return false;
    }

    private void setMousePointer(MousePointer mousePointer)
    {
        switch (selectedMenu)
        {
            case GROUND:
                mousePointer.setType(ground);
                break;
            case JOINT:
                mousePointer.setType(joint);
                break;
            case BACK_OBJECT:
                mousePointer.setType(backObject);
                break;
            case CHERRY:
                mousePointer.setType(cherry);
                break;
            case HEART:
                mousePointer.setType(heart);
                break;
            case OPERATE:
                mousePointer.setType(MousePointer.Type.OPERATE);
                break;
            case DELETE:
                mousePointer.setType(MousePointer.Type.DELETE);
                break;
        }
    }

    public void render(Graphics g, ImageManager im, FontManager fm, int stageNum)
    {
        im.drawToolBar(0, 0);

        Font font = fm.getMediumFont();
        font.drawString(Integer.toString(stageNum + 1), 210 - font.getWidth(Integer.toString(stageNum + 1)) / 2, 40);

        g.setColor(new Color(1, 1, 0, 0.6f));
        g.fillRect(selectedMenu.getMenuCenterX() - 40, selectedMenu.getMenuCenterY() - 40, 80, 80);

        im.drawCursor(350, 40);
        Ground.renderIcon(g, im, 450, 40, ground.getType());
        if (!isJointLockRadius)
        {
            Joint.renderIcon(g, im, 550, 40, joint.getAngle(), joint.getType());
        }
        else
        {
            g.setColor(Color.blue);
            g.drawOval(550 - Joint.RADIUS, 40 - Joint.RADIUS, Joint.RADIUS * 2, Joint.RADIUS * 2);
        }
        BackObject.renderIcon(g, im, 665, 55, backObject.getType());
        im.drawCherry(750, 40, Cherry.RADIUS * 2, Cherry.RADIUS * 2);
        im.drawHeart(850, 40, Heart.RADIUS * 2, Heart.RADIUS * 2);
        im.drawDelete(950, 40);
        playButton.render(g, im, fm);
    }

    public boolean isStageDataSave()
    {
        return playButton.isPressed();
    }
}
