import java.io.*;
import java.util.ArrayList;
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
    private int[] backObjectXs = {120, 240, 360, 480};
    /**
     * backObjectの絶対座標（空の場合は-1）
     */
    private int[] backObjectYs = {570, 570, 570, 570};
    private BackObject.Layer[] backObjectLayers = {BackObject.Layer.LAYER0, BackObject.Layer.LAYER0, BackObject.Layer.LAYER0, BackObject.Layer.LAYER0};
    private BackObject.Type[] backObjectTypes = {BackObject.Type.GLASS1, BackObject.Type.GLASS2, BackObject.Type.GLASS3, BackObject.Type.GLASS4};
    /**
     * 1ステージにあるbackObjectの数
     */
    private int backObjectNum = backObjectXs.length;

    /**
     * 時間制限
     */
    private int timeLimit;

    /**
     * 1ステージにある ground の最大数
     */
    public static final int GROUND_MAX = 150;
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
        jointXs = new ArrayList<>();
        jointYs = new ArrayList<>();
        jointTypes = new ArrayList<>();
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
            jointXs.clear();
            jointYs.clear();
            jointTypes.clear();
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
                                groundTypes.add(Ground.Type.NORMAL);
                            }
                            catch (ArrayIndexOutOfBoundsException e)
                            {
                                System.err.println("1ステージにある ground 数が上限を超えました " + e.getMessage());
                            }
                            break;
                        case "joint":
                            try
                            {
                                jointXs.add(Integer.parseInt(st.nextToken()));
                                jointYs.add(Integer.parseInt(st.nextToken()));
                                jointTypes.add(Joint.Type.valueOf(st.nextToken()));
                            }
                            catch (ArrayIndexOutOfBoundsException e)
                            {
                                System.err.println("1ステージにある joint 数が上限を超えました " + e.getMessage());
                            }
                            break;
                    }
                }
                catch (NoSuchElementException e)
                {
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
                pw.println("ground," + groundXs.get(i) + "," + groundYs.get(i));
            }
            for (int i = 0; i < jointXs.size(); i++)
            {
                pw.println("joint," + jointXs.get(i) + "," + jointYs.get(i) + "," + jointTypes.get(i));
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
        return backObjectXs;
    }

    public int[] getBackObjectYs()
    {
        return backObjectYs;
    }

    public BackObject.Layer[] getBackObjectLayers()
    {
        return backObjectLayers;
    }

    public BackObject.Type[] getBackObjectTypes()
    {
        return backObjectTypes;
    }

    public int getBackObjectNum()
    {
        return backObjectNum;
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

    public void deleteObject(int objectX, int objectY)
    {
        for (int i = 0; i < groundXs.size(); i++)
        {
            if (objectX == groundXs.get(i) && objectY == groundYs.get(i))
            {
                System.out.println(groundXs.get(i) + " " + groundYs.get(i));
                groundXs.remove(i);
                groundYs.remove(i);
                groundTypes.remove(i);
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
    }

    public void deleteAllObject()
    {
        groundXs.clear();
        groundYs.clear();
        groundTypes.clear();
        jointXs.clear();
        jointYs.clear();
        jointTypes.clear();
    }
}
