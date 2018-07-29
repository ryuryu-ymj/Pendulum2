import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
     * 時間制限
     */
    private int timeLimit;

    /**
     * 1ステージにあるgroundの最大数
     */
    public static final int GROUND_MAX = 150;
    /**
     * 1ステージにあるjointの最大数
     */
    public static final int JOINT_MAX = 50;
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
     * @param stageNum ステージ番号
     */
    public void loadStageDate(int stageNum)
    {
        ArrayList<String> stageDate = new ArrayList<>();

        try
        {
            File file = new File("res/stage/stage" + (stageNum + 1) + ".txt");
            if (!file.exists())
            {
                System.err.println("ファイルが存在しません stage" + (stageNum + 1));
                return;
            }

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            int lineCnt = 0;
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
                //System.out.println(line);
                if (lineCnt == 0)
                {
                    try
                    {
                        timeLimit = Integer.valueOf(line);
                    }
                    catch (NumberFormatException e)
                    {
                        System.err.println("timeLimitの取得に失敗しました" + line);
                        timeLimit = 100;
                    }
                }
                else
                {
                    for (int letterCnt = 0; letterCnt < line.length(); letterCnt++)
                    {
                        switch (line.charAt(letterCnt))
                        {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                                try
                                {
                                    groundXs[groundCnt] = letterCnt * Ground.WIDTH;
                                    groundYs[groundCnt] = lineCnt * Ground.WIDTH;
                                    switch (line.charAt(letterCnt))
                                    {
                                        case '0':
                                            groundTypes[groundCnt] = Ground.Type.NORMAL;
                                            break;
                                        case '1':
                                            groundTypes[groundCnt] = Ground.Type.SPINE;
                                            break;
                                    }
                                    //System.out.println(groundCnt + " " + groundXs[groundCnt] + " " + groundYs[groundCnt]);
                                    groundNum++;
                                }
                                catch (ArrayIndexOutOfBoundsException e)
                                {
                                    System.err.println("1ステージにあるground数が上限を超えました " + e.getMessage());
                                }
                                groundCnt++;
                                //System.out.println(j + " " + i);
                                break;
                            case 'j':
                                try
                                {
                                    jointXs[jointCnt] = letterCnt * Ground.WIDTH;
                                    jointYs[jointCnt] = lineCnt * Ground.WIDTH;
                                    jointNum++;
                                }
                                catch (ArrayIndexOutOfBoundsException e)
                                {
                                    System.err.println("1ステージにあるground数が上限を超えました " + e.getMessage());
                                }
                                jointCnt++;
                                break;
                        }
                    }
                }

                lineCnt++;
            }
            fr.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
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

    public int getTimeLimit()
    {
        return timeLimit;
    }
}
