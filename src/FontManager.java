public class FontManager
{
    private Font smallFont;
    private Font mediumFont;

    FontManager()
    {
        smallFont = new Font("res/font/fontSmall");
        mediumFont = new Font("res/font/fontLarge");
    }

    public Font getSmallFont()
    {
        return smallFont;
    }

    public Font getMediumFont()
    {
        return mediumFont;
    }
}
