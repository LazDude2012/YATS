package YATS.util;

public enum Colours
{
	WHITE,
	ORANGE,
	MAGENTA,
	LTBLUE,
	YELLOW,
	LIME,
	PINK,
	GREY,
	LTGREY,
	CYAN,
	PURPLE,
	BLUE,
	BROWN,
	GREEN,
	RED,
	BLACK,
	NONE;

	static String[] valid = new String[]{"WHITE","ORANGE","MAGENTA","LTBLUE","YELLOW","LIME","PINK","GREY","LTGREY","CYAN","PURPLE","BLUE","BROWN","GREEN","RED","BLACK"};
	public static Colours fromInt(int i)
	{
		if(i == -1) return NONE;
		return Colours.valueOf(valid[i]);
	}
	public int toInt()
	{
		if(this == NONE) return -1;
		else return ordinal();
	}
}
