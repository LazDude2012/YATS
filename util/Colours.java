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
	public static int toInt(Colours colour)
	{
		if(colour == NONE) return -1;
		else return colour.ordinal();
	}
	public static byte[] toRGB(Colours colour)
	{
		switch(colour)
		{
			case WHITE:
				return new byte[]{(byte)243,(byte)239,(byte)230};
			case ORANGE:
				return new byte[]{(byte)210,(byte)108,(byte)43};
			case MAGENTA:
				return new byte[]{(byte)158,(byte)63,(byte)178};
			case LTBLUE:
				return new byte[]{(byte)91,(byte)139,(byte)223};
			case YELLOW:
				return new byte[]{(byte)226,(byte)187,(byte)42};
			case LIME:
				return new byte[]{(byte)86,(byte)163,(byte)63};
			case PINK:
				return new byte[]{(byte)235,(byte)106,(byte)157};
			case GREY:
				return new byte[]{(byte)94,(byte)89,(byte)83};
			case LTGREY:
				return new byte[]{(byte)140,(byte)136,(byte)129};
			case CYAN:
				return new byte[]{(byte)43,(byte)140,(byte)173};
			case PURPLE:
				return new byte[]{(byte)94,(byte)37,(byte)109};
			case BLUE:
				return new byte[]{(byte)46,(byte)79,(byte)159};
			case BROWN:
				return new byte[]{(byte)84,(byte)21,(byte)20};
			case GREEN:
				return new byte[]{(byte)58,(byte)108,(byte)44};
			case RED:
				return new byte[]{(byte)184,(byte)46,(byte)46};
			case BLACK:
				return new byte[]{(byte)41,(byte)39,(byte)36};
			default:
				throw new IllegalArgumentException("Only 16 colours, it's not rocket science.");
		}
	}
}
