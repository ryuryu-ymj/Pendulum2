import java.io.*;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * ステージ上のオブジェクトの座標などのデータを管理するクラス
 */
public class StageDate
{
    /**
     * ground の絶対座標（空の場合は-1）
     */
    private ArrayList<Integer> groundXs, groundYs;
    /**
     * ground の型
     */
    private ArrayList<Ground.Type> groundTypes;
    /**
     * ground の位置
     */
    private ArrayList<Ground.Shape> groundShapes;
    /**
     * ground のあたり判定を行うかどうか
     */
    //private ArrayList<Boolean> groundIsCheckCollisions;
    /**
     * ground に接するgroundの数
     */
    private ArrayList<Integer> groundTouchNums;

    /**
     * joint の絶対座標（空の場合は-1）
     */
    private ArrayList<Integer> jointXs, jointYs;
    /**
     * joint の型
     */
    private ArrayList<Joint.Type> jointTypes;

    /**
     * backObjectの絶対座標（空の場合は-1）
     */
    private ArrayList<Integer> backObjectXs;
    /**
     * backObjectの絶対座標（空の場合は-1）
     */
    private ArrayList<Integer> backObjectYs;
    private ArrayList<BackObject.Layer> backObjectLayers;
    private ArrayList<BackObject.Type> backObjectTypes;

    /**
     * 時間制限
     */
    private int timeLimit;

    /**
     * 1ステージにある ground の最大数
     */
    public static final int GROUND_MAX = 1000;
    /**
     * 1ステージにある joint の最大数
     */
    public static final int JOINT_MAX = 50;
    /**
     * 1ステージにある backGround の最大数
     */
    public static final int BACK_OBJECT_MAX = 50;
    /**
     * ステージの最大数
     */
    public static final int STAGE_MAX = 5;

    StageDate()
    {
        groundXs = new ArrayList<>();
        groundYs = new ArrayList<>();
        groundTypes = new ArrayList<>();
        groundShapes = new ArrayList<>();
        groundTouchNums = new ArrayList<>();
        jointXs = new ArrayList<>();
        jointYs = new ArrayList<>();
        jointTypes = new ArrayList<>();
        backObjectXs = new ArrayList<>();
        backObjectYs = new ArrayList<>();
        backObjectLayers = new ArrayList<>();
        backObjectTypes = new ArrayList<>();
    }

    /**
     * ステージのデータを読み込む
     *
     * @param stageNum ステージ番号(0から)
     */
    public void loadStageDate(int stageNum)
    {
        try
        {
            File file = new File("res/stage/stage" + (stageNum + 1) + ".csv");
            if (!file.exists())
            {
                System.err.println("ファイルが存在しません stage" + (stageNum + 1));
                deleteAllObject();
                saveStageDate(stageNum);
                return;
            }

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            groundXs.clear();
            groundYs.clear();
            groundTypes.clear();
            groundShapes.clear();
            groundTouchNums.clear();
            jointXs.clear();
            jointYs.clear();
            jointTypes.clear();
            backObjectXs.clear();
            backObjectYs.clear();
            backObjectTypes.clear();
            backObjectLayers.clear();
            while ((line = br.readLine()) != null)
            {
                try
                {
                    StringTokenizer st = new StringTokenizer(line, ",");
                    switch (st.nextToken())
                    {
                        case "ground":
                            try
                            {
                                groundXs.add(Integer.parseInt(st.nextToken()));
                                groundYs.add(Integer.parseInt(st.nextToken()));
                                groundTypes.add(Ground.Type.valueOf(st.nextToken()));
                                groundShapes.add(Ground.Shape.valueOf(st.nextToken()));
                                groundTouchNums.add(Integer.parseInt(st.nextToken()));
                            }
                            catch (EmptyStackException e)
                            {
                                System.err.println(e.getMessage());
                            }
                            break;
                        case "joint":
                            try
                            {
                                jointXs.add(Integer.parseInt(st.nextToken()));
                                jointYs.add(Integer.parseInt(st.nextToken()));
                                jointTypes.add(Joint.Type.valueOf(st.nextToken()));
                            }
                            catch (EmptyStackException e)
                            {
                                System.err.println(e.getMessage());
                            }
                            break;
                        case "backobject":
                            try
                            {
                                backObjectXs.add(Integer.parseInt(st.nextToken()));
                                backObjectYs.add(Integer.parseInt(st.nextToken()));
                                backObjectTypes.add(BackObject.Type.valueOf(st.nextToken()));
                                backObjectLayers.add(BackObject.Layer.valueOf(st.nextToken()));
                            }
                            catch (EmptyStackException e)
                            {
                                System.err.println(e.getMessage());
                            }
                            break;
                    }
                }
                catch (NoSuchElementException e)
                {
                    System.err.println(e.getMessage());
                }
            }
            fr.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        /*try
        {
            FileWriter fw = new FileWriter("res/stage/stage" + (stageNum + 1) + ".csv");
            PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
            for (int i = 0; i < groundNum; i++)
            {
                pw.println("ground," + groundXs[i] + "," + groundYs[i]);
            }
            pw.close();
            fw.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }*/
    }

    /**
     * ステージデータを保存する
     *
     * @param stageNum 　ステージ番号(0から)
     */
    public void saveStageDate(int stageNum)
    {
        try
        {
            FileWriter fw = new FileWriter("res/stage/stage" + (stageNum + 1) + ".csv");
            PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
            for (int i = 0; i < groundXs.size(); i++)
            {
                pw.println("ground," + groundXs.get(i) + "," + groundYs.get(i) + "," + groundTypes.get(i) + "," + groundShapes.get(i) + "," + groundTouchNums.get(i));
            }
            for (int i = 0; i < jointXs.size(); i++)
            {
                pw.println("joint," + jointXs.get(i) + "," + jointYs.get(i) + "," + jointTypes.get(i));
            }
            for (int i = 0; i < backObjectXs.size(); i++)
            {
                pw.println("backObject," + backObjectXs.get(i) + "," + backObjectYs.get(i) + "," + backObjectTypes.get(i) + "," + backObjectLayers.get(i));
            }
            pw.close();
            fw.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public int[] getGroundXs()
    {
        int[] groundXs = new int[this.groundXs.size()];
        for (int i = 0; i < groundXs.length; i++)
        {
            groundXs[i] = this.groundXs.get(i);
        }
        return groundXs;
    }

    public int[] getGroundYs()
    {
        int[] groundYs = new int[this.groundYs.size()];
        for (int i = 0; i < groundYs.length; i++)
        {
            groundYs[i] = this.groundYs.get(i);
        }
        return groundYs;
    }

    public Ground.Type[] getGroundTypes()
    {
        return groundTypes.toArray(new Ground.Type[groundTypes.size()]);
    }

    public Ground.Shape[] getGroundShapes()
    {
        return groundShapes.toArray(new Ground.Shape[groundShapes.size()]);
    }

    public boolean[] getGroundIsCheckCollisions()
    {
        boolean[] groundIsCheckCollision = new boolean[this.groundTouchNums.size()];
        for (int i = 0; i < groundIsCheckCollision.length; i++)
        {
            groundIsCheckCollision[i] = this.groundTouchNums.get(i) != 4;
        }
        return groundIsCheckCollision;
    }

    public int[] getJointXs()
    {
        int[] jointXs = new int[this.jointXs.size()];
        for (int i = 0; i < jointXs.length; i++)
        {
            jointXs[i] = this.jointXs.get(i);
        }
        return jointXs;
    }

    public int[] getJointYs()
    {
        int[] jointYs = new int[this.jointYs.size()];
        for (int i = 0; i < jointYs.length; i++)
        {
            jointYs[i] = this.jointYs.get(i);
        }
        return jointYs;
    }

    public Joint.Type[] getJointTypes()
    {
        return jointTypes.toArray(new Joint.Type[jointTypes.size()]);
    }

    public int[] getBackObjectXs()
    {
        int[] backObjectXs = new int[this.backObjectXs.size()];
        for (int i = 0; i < backObjectXs.length; i++)
        {
            backObjectXs[i] = this.backObjectXs.get(i);
        }
        return backObjectXs;
    }

    public int[] getBackObjectYs()
    {
        int[] backObjectYs = new int[this.backObjectYs.size()];
        for (int i = 0; i < backObjectYs.length; i++)
        {
            backObjectYs[i] = this.backObjectYs.get(i);
        }
        return backObjectYs;
    }

    public BackObject.Layer[] getBackObjectLayers()
    {
        return backObjectLayers.toArray(new BackObject.Layer[backObjectLayers.size()]);
    }

    public BackObject.Type[] getBackObjectTypes()
    {
        return backObjectTypes.toArray(new BackObject.Type[backObjectTypes.size()]);
    }

    public int getTimeLimit()
    {
        return timeLimit;
    }

    public void addGround(int groundX, int groundY, Ground.Type groundType)
    {
        for (int i = 0; i < groundXs.size(); i++)
        {
            if (groundX == groundXs.get(i) && groundY == groundYs.get(i))
            {
                //System.out.println("not add " + groundX + " " + groundY);
                return;
            }
        }

        if (groundType == Ground.Type.NORMAL)
        {
            boolean isTop, isBottom, isLeft, isRight;
            isTop = isBottom = isLeft = isRight = false;
            int touchNum = 0;
            for (int i = 0; i < groundXs.size(); i++)
            {
                if (groundTypes.get(i) == Ground.Type.NORMAL)
                {
                    if (groundXs.get(i) == groundX)
                    {
                        if (groundYs.get(i) == groundY + Ground.WIDTH)
                        {
                            //bottom
                            if (groundShapes.get(i) == Ground.Shape.GLASS ||
                                    groundShapes.get(i) == Ground.Shape.GLASS_TOP_EDGE ||
                                    groundShapes.get(i) == Ground.Shape.GLASS_TOP_LEFT_EDGE ||
                                    groundShapes.get(i) == Ground.Shape.GLASS_TOP_RIGHT_EDGE ||
                                    groundShapes.get(i) == Ground.Shape.GLASS_ALL_EDGE)
                            {
                                groundShapes.set(i, Ground.Shape.NO_GLASS);
                            }
                            else if (groundShapes.get(i) == Ground.Shape.GLASS_LEFT_EDGE)
                            {
                                groundShapes.set(i, Ground.Shape.NO_GLASS_BOTTOM_LEFT_EDGE);
                            }
                            else if (groundShapes.get(i) == Ground.Shape.GLASS_RIGHT_EDGE)
                            {
                                groundShapes.set(i, Ground.Shape.NO_GLASS_BOTTOM_RIGHT_EDGE);
                            }
                            groundTouchNums.set(i, groundTouchNums.get(i) + 1);
                            if (groundTypes.get(i) == Ground.Type.NORMAL)
                            {
                                touchNum++;
                            }
                            isBottom = true;
                        }
                        else if (groundYs.get(i) == groundY - Ground.WIDTH)
                        {
                            //top
                            if (groundShapes.get(i) == Ground.Shape.NO_GLASS ||
                                    groundShapes.get(i) == Ground.Shape.NO_GLASS_BOTTOM_EDGE ||
                                    groundShapes.get(i) == Ground.Shape.NO_GLASS_BOTTOM_LEFT_EDGE ||
                                    groundShapes.get(i) == Ground.Shape.NO_GLASS_BOTTOM_RIGHT_EDGE)
                            {
                                groundShapes.set(i, Ground.Shape.NO_GLASS);
                            }
                            else if (groundShapes.get(i) == Ground.Shape.GLASS_ALL_EDGE)
                            {
                                groundShapes.set(i, Ground.Shape.GLASS_TOP_EDGE);
                            }
                            else if (groundShapes.get(i) == Ground.Shape.GLASS_RIGHT_EDGE)
                            {
                                groundShapes.set(i, Ground.Shape.GLASS_TOP_RIGHT_EDGE);
                            }
                            else if (groundShapes.get(i) == Ground.Shape.GLASS_LEFT_EDGE)
                            {
                                groundShapes.set(i, Ground.Shape.GLASS_TOP_LEFT_EDGE);
                            }
                            else
                            {
                                groundShapes.set(i, Ground.Shape.GLASS);
                            }
                            groundTouchNums.set(i, groundTouchNums.get(i) + 1);
                            if (groundTypes.get(i) == Ground.Type.NORMAL)
                            {
                                touchNum++;
                            }
                            isTop = true;
                        }
                    }
                    else if (groundXs.get(i) == groundX + Ground.WIDTH)
                    {
                        if (groundYs.get(i) == groundY)
                        {
                            //right
                            if (groundShapes.get(i) == Ground.Shape.GLASS_TOP_LEFT_EDGE ||
                                    groundShapes.get(i) == Ground.Shape.GLASS_LEFT_EDGE)
                            {
                                groundShapes.set(i, Ground.Shape.GLASS);
                            }
                            else if (groundShapes.get(i) == Ground.Shape.GLASS_ALL_EDGE)
                            {
                                groundShapes.set(i, Ground.Shape.GLASS_RIGHT_EDGE);
                            }
                            else if (groundShapes.get(i) == Ground.Shape.GLASS_TOP_EDGE)
                            {
                                groundShapes.set(i, Ground.Shape.GLASS_TOP_RIGHT_EDGE);
                            }
                            else if (groundShapes.get(i) == Ground.Shape.NO_GLASS_BOTTOM_LEFT_EDGE)
                            {
                                groundShapes.set(i, Ground.Shape.NO_GLASS);
                            }
                            else if (groundShapes.get(i) == Ground.Shape.NO_GLASS_BOTTOM_RIGHT_EDGE ||
                                    groundShapes.get(i) == Ground.Shape.NO_GLASS_BOTTOM_EDGE)
                            {
                                groundShapes.set(i, Ground.Shape.NO_GLASS_BOTTOM_RIGHT_EDGE);
                            }
                            groundTouchNums.set(i, groundTouchNums.get(i) + 1);
                            if (groundTypes.get(i) == Ground.Type.NORMAL)
                            {
                                touchNum++;
                            }
                            isRight = true;
                        }
                    }
                    else if (groundXs.get(i) == groundX - Ground.WIDTH)
                    {
                        if (groundYs.get(i) == groundY)
                        {
                            //left
                            if (groundShapes.get(i) == Ground.Shape.GLASS_TOP_RIGHT_EDGE ||
                                    groundShapes.get(i) == Ground.Shape.GLASS_RIGHT_EDGE)
                            {
                                groundShapes.set(i, Ground.Shape.GLASS);
                            }
                            else if (groundShapes.get(i) == Ground.Shape.GLASS_ALL_EDGE)
                            {
                                groundShapes.set(i, Ground.Shape.GLASS_LEFT_EDGE);
                            }
                            else if (groundShapes.get(i) == Ground.Shape.GLASS_TOP_EDGE)
                            {
                                groundShapes.set(i, Ground.Shape.GLASS_TOP_LEFT_EDGE);
                            }
                            else if (groundShapes.get(i) == Ground.Shape.NO_GLASS_BOTTOM_RIGHT_EDGE)
                            {
                                groundShapes.set(i, Ground.Shape.NO_GLASS);
                            }
                            else if (groundShapes.get(i) == Ground.Shape.NO_GLASS_BOTTOM_LEFT_EDGE ||
                                    groundShapes.get(i) == Ground.Shape.NO_GLASS_BOTTOM_EDGE)
                            {
                                groundShapes.set(i, Ground.Shape.NO_GLASS_BOTTOM_LEFT_EDGE);
                            }
                            groundTouchNums.set(i, groundTouchNums.get(i) + 1);
                            if (groundTypes.get(i) == Ground.Type.NORMAL)
                            {
                                touchNum++;
                            }
                            isLeft = true;
                        }
                    }
                }
            }

            if (isTop)
            {
                if (isBottom)
                {
                    groundShapes.add(Ground.Shape.NO_GLASS);
                }
                else if (isLeft)
                {
                    if (isRight)
                    {
                        groundShapes.add(Ground.Shape.NO_GLASS);
                    }
                    else
                    {
                        groundShapes.add(Ground.Shape.NO_GLASS_BOTTOM_RIGHT_EDGE);
                    }
                }
                else if (isRight)
                {
                    groundShapes.add(Ground.Shape.NO_GLASS_BOTTOM_LEFT_EDGE);
                }
                else
                {
                    groundShapes.add(Ground.Shape.NO_GLASS_BOTTOM_EDGE);
                }
            }
            else
            {
                if (isBottom)
                {
                    if (isLeft)
                    {
                        if (isRight)
                        {
                            groundShapes.add(Ground.Shape.GLASS);
                        }
                        else
                        {
                            groundShapes.add(Ground.Shape.GLASS_TOP_RIGHT_EDGE);
                        }
                    }
                    else if (isRight)
                    {
                        groundShapes.add(Ground.Shape.GLASS_TOP_LEFT_EDGE);
                    }
                    else
                    {
                        groundShapes.add(Ground.Shape.GLASS_TOP_EDGE);
                    }
                }
                else
                {
                    if (isLeft)
                    {
                        if (isRight)
                        {
                            groundShapes.add(Ground.Shape.GLASS);
                        }
                        else
                        {
                            groundShapes.add(Ground.Shape.GLASS_RIGHT_EDGE);
                        }
                    }
                    else if (isRight)
                    {
                        groundShapes.add(Ground.Shape.GLASS_LEFT_EDGE);
                    }
                    else
                    {
                        groundShapes.add(Ground.Shape.GLASS_ALL_EDGE);
                    }
                }
            }
            groundTouchNums.add(touchNum);
        }
        else
        {
            groundShapes.add(Ground.Shape.NO_GLASS);
            groundTouchNums.add(0);
        }
        //System.out.println("add " + groundX + " " + groundY + " " + groundXs[0] + " " + groundYs[0] + " " + groundNum);
        groundXs.add(groundX);
        groundYs.add(groundY);
        groundTypes.add(groundType);
    }

    public void addJoint(int jointX, int jointY, Joint.Type jointType)
    {
        for (int i = 0; i < jointXs.size(); i++)
        {
            if (jointX == jointXs.get(i) && jointY == jointYs.get(i))
            {
                return;
            }
        }
        jointXs.add(jointX);
        jointYs.add(jointY);
        jointTypes.add(jointType);
    }

    public void addBackObject(int x, int y, BackObject.Type type, BackObject.Layer layer)
    {
        for (int i = 0; i < backObjectXs.size(); i++)
        {
            if (x == backObjectXs.get(i) && y == backObjectYs.get(i))
            {
                return;
            }
        }
        backObjectXs.add(x);
        backObjectYs.add(y);
        backObjectTypes.add(type);
        backObjectLayers.add(layer);
    }

    public void deleteObject(int objectX, int objectY)
    {
        for (int i = 0; i < groundXs.size(); i++)
        {
            if (groundXs.get(i) == objectX && groundYs.get(i) == objectY)
            {
                //System.out.println(groundXs.get(i) + " " + groundYs.get(i));
                groundXs.remove(i);
                groundYs.remove(i);
                groundTypes.remove(i);
                groundShapes.remove(i);
                groundTouchNums.remove(i);
            }
        }
        for (int i = 0; i < groundXs.size(); i++)
        {
            if (groundXs.get(i) == objectX)
            {
                if (groundYs.get(i) == objectY + Ground.WIDTH)
                {
                    //bottom
                    resetGround(i);
                }
                else if (groundYs.get(i) == objectY - Ground.WIDTH)
                {
                    //top
                    resetGround(i);
                }
            }
            else if (groundXs.get(i) == objectX + Ground.WIDTH)
            {
                if (groundYs.get(i) == objectY)
                {
                    //right
                    resetGround(i);
                }
            }
            else if (groundXs.get(i) == objectX - Ground.WIDTH)
            {
                if (groundYs.get(i) == objectY)
                {
                    //left
                    resetGround(i);
                }
            }
        }

        for (int i = 0; i < jointXs.size(); i++)
        {
            if (objectX == jointXs.get(i) && objectY == jointYs.get(i))
            {
                jointXs.remove(i);
                jointYs.remove(i);
                jointTypes.remove(i);
            }
        }

        for (int i = 0; i < backObjectXs.size(); i++)
        {
            if (objectX == backObjectXs.get(i) && objectY == backObjectYs.get(i))
            {
                backObjectXs.remove(i);
                backObjectYs.remove(i);
                backObjectTypes.remove(i);
            }
        }
    }

    private void resetGround(int index)
    {
        int groundX = groundXs.get(index);
        int groundY = groundYs.get(index);
        Ground.Type groundType = groundTypes.get(index);
        groundXs.remove(index);
        groundYs.remove(index);
        groundTypes.remove(index);
        groundShapes.remove(index);
        groundTouchNums.remove(index);
        addGround(groundX, groundY, groundType);
    }

    public void deleteAllObject()
    {
        groundXs.clear();
        groundYs.clear();
        groundTypes.clear();
        groundShapes.clear();
        groundTouchNums.clear();
        jointXs.clear();
        jointYs.clear();
        jointTypes.clear();
        backObjectXs.clear();
        backObjectYs.clear();
        backObjectTypes.clear();
        backObjectLayers.clear();
    }
}
