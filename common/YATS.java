package YATS.common;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(modid="YATS",name="Yet Another Tube System",version="1.0")
public class YATS
{
	@Mod.Instance
	public static YATS instance;

	@SidedProxy(clientSide = "YATS.client.ClientProxy",serverSide="YATS.common.CommonProxy")
	public static CommonProxy proxy;

	@Mod.Init
	public void Initialise(FMLInitializationEvent e)
	{
		RegisterBlocks();
		RegisterRecipes();
		RegisterItems();
	}

	private void RegisterItems()
	{

	}

	private void RegisterRecipes()
	{

	}

	private void RegisterBlocks()
	{

	}
}
