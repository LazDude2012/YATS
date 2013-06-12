package YATS.render;

import YATS.util.Colours;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

@SideOnly(Side.CLIENT)
public class IconProvider
{
	protected static Icon[] tubeicons = new Icon[17];

	public static void Init(IconRegister register)
	{
		for(int i = 0; i < 17; i++)
		{
			tubeicons[i] = register.registerIcon("YATS:blockTube"+Colours.values()[i].name());
		}
	}

	public static Icon GetColourTubeIcon(Colours colour)
	{
		return tubeicons[colour.ordinal()];
	}
}
