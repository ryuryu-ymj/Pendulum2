import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
    private int startX, startY;
    private int goalX, goalY;
    private int warpX, warpY;
    /** 時間制限 */
    private int timeLimit;
    /**  */
    int groundNum;

    /**
     * 1ステージにあるgroundの最大数
     */
    public static final int GROUND_MAX = 150;
    /**
     * ステージの最大数
     */
    public static final int STAGE_MAX = 5;

    StageDate()
    {
        groundXs = new int[GROUND_MAX];
        groundYs = new int[GROUND_MAX];
        groundTypes = new Ground.Type[GROUND_MAX];
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
            for (int i = 0; i < groundXs.length; i++)
            {
                groundXs[i] = -1;
                groundYs[i] = -1;
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
                                    if (groundNum == groundCnt)
                                    {
                                        groundNum--;
                                    }
                                    System.err.println("1ステージにあるground数が上限を超えました" + (groundCnt + 1));
                                }
                                groundCnt++;
                                //System.out.println(j + " " + i);
                                break;
                            case 's':
                                startX = letterCnt * Ground.WIDTH;
                                startY = lineCnt * Ground.WIDTH;
                                break;
                            case 'g':
                                goalX = letterCnt * Ground.WIDTH;
                                goalY = lineCnt * Ground.WIDTH;
                                break;
                            case 'w':
                                warpX = letterCnt * Ground.WIDTH;
                                warpY = lineCnt * Ground.WIDTH;
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

    public int getGroundNum()
    {
        return groundNum;
    }

    public Ground.Type[] getGroundTypes()
    {
        return groundTypes;
    }

    public int getStartX()
    {
        return startX;
    }

    public int getStartY()
    {
        return startY;
    }

    public int getGoalX()
    {
        return goalX;
    }

    public int getGoalY()
    {
        return goalY;
    }

    public int getWarpX()
    {
        return warpX;
    }

    public int getWarpY()
    {
        return warpY;
    }

    /**
     *
     * @return 時間制限
     */
    public int getTimeLimit()
    {
        return timeLimit;
    }
}
