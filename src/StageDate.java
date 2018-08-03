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
     * groundの絶対座標（空の場合は-1）
     */
    private int[] groundXs, groundYs;
    /**
     * groundの型
     */
    private Ground.Type[] groundTypes;
    /**
     * 1ステージにあるgroundの数
     */
    private int groundNum;

    /**
     * jointの絶対座標（空の場合は-1）
     */
    private int[] jointXs, jointYs;
    /**
     * 1ステージにあるjointの数
     */
    private int jointNum;

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
        groundXs = new int[GROUND_MAX];
        groundYs = new int[GROUND_MAX];
        groundTypes = new Ground.Type[GROUND_MAX];
        jointXs = new int[JOINT_MAX];
        jointYs = new int[JOINT_MAX];
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
                return;
            }

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            groundNum = 0;
            int groundCnt = 0;
            jointNum = 0;
            int jointCnt = 0;
            for (int i = 0; i < groundXs.length; i++)
            {
                groundXs[i] = -1;
                groundYs[i] = -1;
            }
            for (int i = 0; i < jointXs.length; i++)
            {
                jointXs[i] = -1;
                jointYs[i] = -1;
            }
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
                                groundXs[groundCnt] = Integer.parseInt(st.nextToken());
                                groundYs[groundCnt] = Integer.parseInt(st.nextToken());
                                groundNum++;
                            }
                            catch (ArrayIndexOutOfBoundsException e)
                            {
                                System.err.println("1ステージにある ground 数が上限を超えました " + e.getMessage());
                            }
                            groundCnt++;
                            break;
                        case "joint":
                            try
                            {
                                jointXs[jointCnt] = Integer.parseInt(st.nextToken());
                                jointYs[jointCnt] = Integer.parseInt(st.nextToken());
                                jointNum++;
                            }
                            catch (ArrayIndexOutOfBoundsException e)
                            {
                                System.err.println("1ステージにある joint 数が上限を超えました " + e.getMessage());
                            }
                            jointCnt++;
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

    public void saveStageDate(int stageNum)
    {
        try
        {
            FileWriter fw = new FileWriter("res/stage/stage" + (stageNum + 1) + ".csv");
            PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
            for (int i = 0; i < groundNum; i++)
            {
                pw.println("ground," + groundXs[i] + "," + groundYs[i]);
            }
            for (int i = 0; i < jointNum; i++)
            {
                pw.println("joint," + jointXs[i] + "," + jointYs[i]);
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
        return groundXs;
    }

    public int[] getGroundYs()
    {
        return groundYs;
    }

    public Ground.Type[] getGroundTypes()
    {
        return groundTypes;
    }

    public int getGroundNum()
    {
        return groundNum;
    }

    public int[] getJointXs()
    {
        return jointXs;
    }

    public int[] getJointYs()
    {
        return jointYs;
    }

    public int getJointNum()
    {
        return jointNum;
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
        for (int i = 0; i < groundNum; i++)
        {
            if (groundX == groundXs[i] && groundY == groundYs[i])
            {
                //System.out.println("not add " + groundX + " " + groundY);
                return;
            }
        }
        //System.out.println("add " + groundX + " " + groundY + " " + groundXs[0] + " " + groundYs[0] + " " + groundNum);
        groundXs[groundNum] = groundX;
        groundYs[groundNum] = groundY;
        groundTypes[groundNum] = groundType;
        groundNum++;
    }
}
