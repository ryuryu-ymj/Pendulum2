import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileReader;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * フォントクラス.
 *
 * @author negset
 */
public class Font
{
    /**
     * フォント名
     */
    private String fontName;
    /**
     * フォントサイズ
     */
    private float fontSize;
    /**
     * 文字の総数
     */
    private int glyphNum;
    /**
     * 画像の総数
     */
    private int sheetNum;
    /**
     * 1文字分の画像の幅
     */
    private int spriteSize;
    /**
     * 文字を格納する配列
     */
    private char[] glyph;
    /**
     * 文字の横幅を格納する配列
     */
    private int[] glyphWidth;
    /**
     * 文字画像を格納する配列
     */
    private Image[] sprite;
    /**
     * 文字画像の描画色(RGBA)
     */
    private float r, g, b, a;

    /**
     * コンストラクタ
     *
     * @param path フォントファイルが格納されたディレクトリ
     */
    Font(String path)
    {
        loadProperty(path + "/font.prop");
        glyph = new char[glyphNum];
        glyphWidth = new int[glyphNum];
        loadGlyphData(path + "/glyph.dat");
        sprite = new Image[glyphNum];
        loadSheet(path);
        setColor(1.0f, 1.0f, 1.0f);
    }

    /**
     * フォントプロパティの読み込みを行う.
     *
     * @param path ファイルパス
     */
    private void loadProperty(String path)
    {
        try
        {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null)
            {
                if (line.startsWith("fontName="))
                {
                    fontName = line.substring(9);
                }
                else if (line.startsWith("fontSize="))
                {
                    fontSize = Float.parseFloat(line.substring(9));
                }
                else if (line.startsWith("glyphNum="))
                {
                    glyphNum = Integer.parseInt(line.substring(9));
                }
                else if (line.startsWith("sheetNum="))
                {
                    sheetNum = Integer.parseInt(line.substring(9));
                }
                else if (line.startsWith("spriteSize="))
                {
                    spriteSize = Integer.parseInt(line.substring(11));
                }
            }

            br.close();
            fr.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * グリフデータの読み込みを行う.
     *
     * @param path ファイルパス
     */
    private void loadGlyphData(String path)
    {
        try
        {
            FileInputStream fis = new FileInputStream(path);
            DataInputStream dis = new DataInputStream(fis);
            for (int i = 0; i < glyphNum; i++)
            {
                glyph[i] = dis.readChar();
                glyphWidth[i] = dis.readInt();
            }
            fis.close();
            dis.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * スプライトシートの読み込みを行う.
     *
     * @param path ファイルパス
     */
    private void loadSheet(String path)
    {
        try
        {
            for (int i = 0; i < sheetNum; i++)
            {
                SpriteSheet ss = new SpriteSheet(
                        path + "/sheet" + i + ".png", spriteSize, spriteSize);
                for (int j = 0; j < 256 && i * 256 + j < glyphNum; j++)
                {
                    sprite[i * 256 + j] = ss.getSubImage(j % 16, j / 16);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 描画する際の色を設定する.
     *
     * @param r 赤
     * @param g 緑
     * @param b 青
     * @param a 透明度
     */
    public void setColor(float r, float g, float b, float a)
    {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    /**
     * 描画する際の色を設定する.
     *
     * @param r 赤
     * @param g 緑
     * @param b 青
     */
    public void setColor(float r, float g, float b)
    {
        setColor(r, g, b, 1.0f);
    }

    /**
     * 描画する際の透明度を設定する.
     *
     * @param a 透明度
     */
    public void setAlpha(float a)
    {
        this.a = a;
    }

    /**
     * フォント名を返す.
     *
     * @return フォント名
     */
    public String getName()
    {
        return fontName;
    }

    /**
     * フォントサイズを返す.
     *
     * @return フォントサイズ
     */
    public float getSize()
    {
        return fontSize;
    }

    /**
     * 文字配列におけるインデックスを返す.
     *
     * @param ch 文字
     * @return 配列におけるインデックス
     */
    private int getIndex(char ch)
    {
        for (int i = 0; i < glyphNum; i++)
        {
            if (ch == glyph[i])
            {
                return i;
            }
        }
        return 0;        // 見つからなかった
    }

    /**
     * 文字列を描画するのに必要な横幅を返す.
     *
     * @param str 文字列
     * @return 横幅
     */
    public int getWidth(String str)
    {
        int width = 0;
        char[] ch = str.toCharArray();
        for (int i = 0; i < ch.length; i++)
        {
            int id = getIndex(ch[i]);
            width += glyphWidth[id];
        }
        return width;
    }

    /**
     * 文字列を描画する.
     *
     * @param str 描画する文字列
     * @param x   X座標
     * @param y   Y座標
     */
    public void drawString(String str, float x, float y)
    {
        char[] ch = str.toCharArray();
        for (int i = 0; i < ch.length; i++)
        {
            int id = getIndex(ch[i]);
            sprite[id].setImageColor(r, g, b, a);
            sprite[id].draw(
                    x - (float) (spriteSize - glyphWidth[id]) / 2,
                    y - (float) spriteSize / 2);
            x += glyphWidth[id];
        }
    }

    /**
     * 文字列を中央揃えで描画する.
     *
     * @param str 描画する文字列
     * @param x   中央のX座標
     * @param y   Y座標
     */
    public void drawStringCentered(String str, float x, float y)
    {
        drawString(str, x - getWidth(str) / 2, y);
    }

    /**
     * フォントを廃棄し,メモリを開放する.
     * 廃棄後はフォントにアクセスしてはならない.
     */
    public void destroy()
    {
        for (Image img : sprite)
        {
            try
            {
                img.destroy();
            }
            catch (SlickException e)
            {
                e.printStackTrace();
            }
        }
    }
}