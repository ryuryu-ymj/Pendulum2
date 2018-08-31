import jdk.nashorn.internal.scripts.JO;

import java.io.*;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * ステージ上のオブジェクトの座標などのデータを管理するクラス
 */
public class StageData
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
    private ArrayList<Ground.Position> groundPositions;
    /**
     * ground に接するgroundの数
     */
    //private ArrayList<Integer> groundTouchNums;

    /**
     * joint の絶対座標（空の場合は-1）
     */
    private ArrayList<Integer> jointXs, jointYs;
    /**
     * joint の型
     */
    private ArrayList<Joint.Type> jointTypes;
    /**
     * joint のjointLock有効範囲半径　0なら有効範囲は無限(指定しない)
     */
    private ArrayList<Integer> jointLockRadiuses;

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
     * cherry の絶対座標（空の場合は-1）
     */
    private ArrayList<Integer> cherryXs, cherryYs;

    /**
     * heart の絶対座標（空の場合は-1）
     */
    private ArrayList<Integer> heartXs, heartYs;

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
     * 1ステージにある cherry の最大数
     */
    public static final int CHERRY_MAX = 50;
    /**
     * 1ステージにある heart の最大数
     */
    public static final int HEART_MAX = 10;
    /**
     * ステージの最大数
     */
    public static final int STAGE_MAX = 5;

    StageData()
    {
        groundXs = new ArrayList<>();
        groundYs = new ArrayList<>();
        groundTypes = new ArrayList<>();
        groundPositions = new ArrayList<>();
        jointXs = new ArrayList<>();
        jointYs = new ArrayList<>();
        jointTypes = new ArrayList<>();
        jointLockRadiuses = new ArrayList<>();
        backObjectXs = new ArrayList<>();
        backObjectYs = new ArrayList<>();
        backObjectLayers = new ArrayList<>();
        backObjectTypes = new ArrayList<>();
        cherryXs = new ArrayList<>();
        cherryYs = new ArrayList<>();
        heartXs = new ArrayList<>();
        heartYs = new ArrayList<>();
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
            groundPositions.clear();
            jointXs.clear();
            jointYs.clear();
            jointTypes.clear();
            jointLockRadiuses.clear();
            backObjectXs.clear();
            backObjectYs.clear();
            backObjectTypes.clear();
            backObjectLayers.clear();
            cherryXs.clear();
            cherryYs.clear();
            heartXs.clear();
            heartYs.clear();
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
                                groundPositions.add(new Ground.Position(Boolean.valueOf(st.nextToken()),
                                        Boolean.valueOf(st.nextToken()),
                                        Boolean.valueOf(st.nextToken()),
                                        Boolean.valueOf(st.nextToken())));
                                //System.out.println(groundPositions.get(groundPositions.size() - 1).toString());
                            }
                            catch (EmptyStackException e)
                            {
                                System.err.println("ground" + e.getMessage());
                            }
                            break;
                        case "joint":
                            try
                            {
                                jointXs.add(Integer.parseInt(st.nextToken()));
                                jointYs.add(Integer.parseInt(st.nextToken()));
                                jointTypes.add(Joint.Type.valueOf(st.nextToken()));
                                jointLockRadiuses.add(Integer.parseInt(st.nextToken()));
                            }
                            catch (EmptyStackException e)
                            {
                                System.err.println("joint" + e.getMessage());
                            }
                            break;
                        case "backObject":
                            try
                            {
                                backObjectXs.add(Integer.parseInt(st.nextToken()));
                                backObjectYs.add(Integer.parseInt(st.nextToken()));
                                backObjectTypes.add(BackObject.Type.valueOf(st.nextToken()));
                                backObjectLayers.add(BackObject.Layer.valueOf(st.nextToken()));
                            }
                            catch (EmptyStackException e)
                            {
                                System.err.println("backObject" + e.getMessage());
                            }
                            break;
                        case "cherry":
                            try
                            {
                                cherryXs.add(Integer.parseInt(st.nextToken()));
                                cherryYs.add(Integer.parseInt(st.nextToken()));
                                //System.out.println("cherryのデータを取得しました");
                            }
                            catch (EmptyStackException e)
                            {
                                System.err.println("cherry" + e.getMessage());
                            }
                            break;
                        case "heart":
                            try
                            {
                                heartXs.add(Integer.parseInt(st.nextToken()));
                                heartYs.add(Integer.parseInt(st.nextToken()));
                            }
                            catch (EmptyStackException e)
                            {
                                System.err.println("heart" + e.getMessage());
                            }
                            break;
                    }
                }
                catch (NoSuchElementException e)
                {
                    System.err.println("読み込みに失敗しました　" + line);
                }
            }
            fr.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
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
                pw.println("ground," + groundXs.get(i) + "," + groundYs.get(i) + "," + groundTypes.get(i)
                        + "," + groundPositions.get(i).toString());
            }
            for (int i = 0; i < jointXs.size(); i++)
            {
                pw.println("joint," + jointXs.get(i) + "," + jointYs.get(i) + "," + jointTypes.get(i) + "," + jointLockRadiuses.get(i));
            }
            for (int i = 0; i < backObjectXs.size(); i++)
            {
                pw.println("backObject," + backObjectXs.get(i) + "," + backObjectYs.get(i) + "," + backObjectTypes.get(i) + "," + backObjectLayers.get(i));
            }
            for (int i = 0; i < cherryXs.size(); i++)
            {
                pw.println("cherry," + cherryXs.get(i) + "," + cherryYs.get(i));
            }
            for (int i = 0; i < heartXs.size(); i++)
            {
                pw.println("heart," + heartXs.get(i) + "," + heartYs.get(i));
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

    public Ground.Position[] getGroundPositions()
    {
        return groundPositions.toArray(new Ground.Position[groundPositions.size()]);
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

    public int[] getJointLockRadiuses()
    {
        int[] jointLockRadiuses = new int[this.jointLockRadiuses.size()];
        for (int i = 0; i < jointLockRadiuses.length; i++)
        {
            jointLockRadiuses[i] = this.jointLockRadiuses.get(i);
        }
        return jointLockRadiuses;
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

    public int[] getCherryXs()
    {
        int[] cherryXs = new int[this.cherryXs.size()];
        for (int i = 0; i < cherryXs.length; i++)
        {
            cherryXs[i] = this.cherryXs.get(i);
        }
        return cherryXs;
    }

    public int[] getCherryYs()
    {
        int[] cherryYs = new int[this.cherryYs.size()];
        for (int i = 0; i < cherryYs.length; i++)
        {
            cherryYs[i] = this.cherryYs.get(i);
        }
        return cherryYs;
    }

    public int[] getHeartXs()
    {
        int[] heartXs = new int[this.heartXs.size()];
        for (int i = 0; i < heartXs.length; i++)
        {
            heartXs[i] = this.heartXs.get(i);
        }
        return heartXs;
    }

    public int[] getHeartYs()
    {
        int[] heartYs = new int[this.heartYs.size()];
        for (int i = 0; i < heartYs.length; i++)
        {
            heartYs[i] = this.heartYs.get(i);
        }
        return heartYs;
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

        groundXs.add(groundX);
        groundYs.add(groundY);
        groundTypes.add(groundType);
        groundPositions.add(new Ground.Position());
        for (int i = 0; i < groundXs.size(); i++)
        {
            if (groundTypes.get(i) == groundTypes.get(groundTypes.size() - 1))
            {
                if (groundXs.get(i) == groundX)
                {
                    if (groundYs.get(i) == groundY + Ground.WIDTH)
                    {
                        //bottom
                        groundPositions.get(i).hasTop = true;
                        groundPositions.get(groundPositions.size() - 1).hasBottom = true;
                    }
                    else if (groundYs.get(i) == groundY - Ground.WIDTH)
                    {
                        //top
                        groundPositions.get(i).hasBottom = true;
                        groundPositions.get(groundPositions.size() - 1).hasTop = true;
                    }
                }
                else if (groundXs.get(i) == groundX + Ground.WIDTH)
                {
                    if (groundYs.get(i) == groundY)
                    {
                        //right
                        groundPositions.get(i).hasLeft = true;
                        groundPositions.get(groundPositions.size() - 1).hasRight = true;
                    }
                }
                else if (groundXs.get(i) == groundX - Ground.WIDTH)
                {
                    if (groundYs.get(i) == groundY)
                    {
                        //left
                        groundPositions.get(i).hasRight = true;
                        groundPositions.get(groundPositions.size() - 1).hasLeft = true;
                    }
                }
            }
        }
        //System.out.println(groundPositions.get(groundPositions.size() - 1).toString());
    }

    public void addJoint(int jointX, int jointY, Joint.Type jointType, int jointLockRadius)
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
        jointLockRadiuses.add(jointLockRadius);
    }

    public void addCherry(int cherryX, int cherryY)
    {
        for (int i = 0; i < cherryXs.size(); i++)
        {
            if (cherryX == cherryXs.get(i) && cherryY == cherryYs.get(i))
            {
                return;
            }
        }
        cherryXs.add(cherryX);
        cherryYs.add(cherryY);
    }

    public void resetJointRadius(int jointX, int jointY, int jointLockRadius)
    {
        for (int i = 0; i < jointXs.size(); i++)
        {
            if (jointX == jointXs.get(i) && jointY == jointYs.get(i))
            {
                jointLockRadiuses.set(i, jointLockRadius);
                //System.out.println("reset jointRadius index:" + 1);
            }
        }
    }

    public void addHeart(int heartX, int heartY)
    {
        for (int i = 0; i < heartXs.size(); i++)
        {
            if (heartX == heartXs.get(i) && heartY == heartYs.get(i))
            {
                return;
            }
        }
        heartXs.add(heartX);
        heartYs.add(heartY);
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

    /**
     *
     * @param objectX
     * @param objectY
     * @return 消されたゲームオブジェクト（該当オブジェクトがない場合null）
     */
    public GameObject deleteObject(int objectX, int objectY, ObjectPool objectPool)
    {
        for (int i = 0; i < groundXs.size(); i++)
        {
            if (groundXs.get(i) == objectX && groundYs.get(i) == objectY)
            {
                //System.out.println(groundXs.get(i) + " " + groundYs.get(i));
                Ground ground = new Ground(objectPool);
                ground.activate(groundXs.get(i), groundYs.get(i), groundTypes.get(i), groundPositions.get(i), 0);
                groundXs.remove(i);
                groundYs.remove(i);
                groundTypes.remove(i);
                groundPositions.remove(i);

                for (int j = 0; j < groundXs.size(); j++)
                {
                    if (groundXs.get(j) == objectX)
                    {
                        if (groundYs.get(j) == objectY + Ground.WIDTH)
                        {
                            //bottom
                            groundPositions.get(j).hasTop = false;
                        }
                        else if (groundYs.get(j) == objectY - Ground.WIDTH)
                        {
                            //top
                            groundPositions.get(j).hasBottom = false;
                        }
                    }
                    else if (groundXs.get(j) == objectX + Ground.WIDTH)
                    {
                        if (groundYs.get(j) == objectY)
                        {
                            //right
                            groundPositions.get(j).hasLeft = false;
                        }
                    }
                    else if (groundXs.get(j) == objectX - Ground.WIDTH)
                    {
                        if (groundYs.get(j) == objectY)
                        {
                            //left
                            groundPositions.get(j).hasRight = false;
                        }
                    }
                }
                return ground;
            }
        }

        for (int i = 0; i < jointXs.size(); i++)
        {
            if (objectX == jointXs.get(i) && objectY == jointYs.get(i))
            {
                Joint joint = new Joint(new  Player(0, 0, objectPool), objectPool);
                joint.activate(jointXs.get(i), jointYs.get(i), jointTypes.get(i), jointLockRadiuses.get(i), false, 0);
                jointXs.remove(i);
                jointYs.remove(i);
                jointTypes.remove(i);
                jointLockRadiuses.remove(i);
                return joint;
            }
        }

        for (int i = 0; i < backObjectXs.size(); i++)
        {
            if (objectX == backObjectXs.get(i) && objectY == backObjectYs.get(i))
            {
                BackObject backObject = new BackObject(objectPool);
                backObject.activate(backObjectXs.get(i), backObjectYs.get(i), backObjectTypes.get(i), backObjectLayers.get(i), 0);
                backObjectXs.remove(i);
                backObjectYs.remove(i);
                backObjectTypes.remove(i);
                return backObject;
            }
        }

        for (int i = 0; i < cherryXs.size(); i++)
        {
            if (objectX == cherryXs.get(i) && objectY == cherryYs.get(i))
            {
                Cherry cherry = new Cherry(objectPool);
                cherry.activate(cherryXs.get(i), cherryYs.get(i), 0);
                cherryXs.remove(i);
                cherryYs.remove(i);
                return cherry;
            }
        }

        for (int i = 0; i < heartXs.size(); i++)
        {
            if (objectX == heartXs.get(i) && objectY == heartYs.get(i))
            {
                Heart heart = new Heart(objectPool);
                heart.activate(heartXs.get(i), heartYs.get(i), 0);
                heartXs.remove(i);
                heartYs.remove(i);
                return heart;
            }
        }

        return null;
    }

    public void deleteAllObject()
    {
        groundXs.clear();
        groundYs.clear();
        groundTypes.clear();
        groundPositions.clear();
        jointXs.clear();
        jointYs.clear();
        jointTypes.clear();
        jointLockRadiuses.clear();
        backObjectXs.clear();
        backObjectYs.clear();
        backObjectTypes.clear();
        backObjectLayers.clear();
        cherryXs.clear();
        cherryYs.clear();
        heartXs.clear();
        heartYs.clear();
    }
}
