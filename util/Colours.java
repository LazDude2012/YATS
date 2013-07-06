package YATS.util;

public enum Colours
{
    WHITE("FFFFFF"),
    ORANGE("D87F33"),
    MAGENTA("B24CD8"),
    LTBLUE("6699D8"),
    YELLOW("E5E533"),
    LIME("7FCC19"),
    PINK("F27FA5"),
    GREY("4C4C4C"),
    LTGREY("999999"),
    CYAN("4C7F99"),
    PURPLE("7F3FB2"),
    BLUE("334CB2"),
    BROWN("664C33"),
    GREEN("667F33"),
    RED("993333"),
    BLACK("191919"),
    NONE("FFFFFF");

    Colours(String defaultParam)
    {
        defaultColor = defaultParam;
    }

    public final String defaultColor;
    static String[] valid = new String[]{"WHITE", "ORANGE", "MAGENTA", "LTBLUE", "YELLOW", "LIME", "PINK", "GREY", "LTGREY", "CYAN", "PURPLE", "BLUE", "BROWN", "GREEN", "RED", "BLACK"};

    public static Colours fromInt(int i)
    {
        if (i == -1) return NONE;
        return Colours.valueOf(valid[i]);
    }

    public static int toInt(Colours colour)
    {
        if (colour == NONE) return -1;
        else return colour.ordinal();
    }

    public static float[] toRGB(Colours colour)
    {
        return ConfigHandler.colors.get(colour);
    }
}
