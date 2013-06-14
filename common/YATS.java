package YATS.common;

import YATS.tile.*;
import YATS.util.ConfigHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import YATS.block.*;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;

@Mod(modid="YATS",name="Yet Another Tube System",version="1.0")
public class YATS
{
	@Mod.Instance
	public static YATS instance;

	@SidedProxy(clientSide = "YATS.client.ClientProxy",serverSide="YATS.common.CommonProxy")
	public static CommonProxy proxy;

	public Block blockRemoteExtractor;
	public Block blockRoutingMarshaller;
	public Block blockExtractor;
	public Block blockAdvExtractor;
	public Block blockTube;
	public Block blockItemBuffer;

	@Mod.PreInit
	public void PreInitialise(FMLPreInitializationEvent e)
	{
		ConfigHandler.Load(e);

	}

	@Mod.Init
	public void Initialise(FMLInitializationEvent e)
	{
		RegisterBlocksAndTiles();
		RegisterRecipes();
		RegisterItems();
	}

	private void RegisterItems()
	{

	}

	private void RegisterRecipes()
	{

	}

	private void RegisterBlocksAndTiles()
	{
		GameRegistry.registerBlock(blockAdvExtractor,"YATSBlockAdvExtractor");
		GameRegistry.registerTileEntity(TileAdvExtractor.class, "YATSTileAdvExtractor");
		LanguageRegistry.addName(blockAdvExtractor,"Advanced Extractor");

		GameRegistry.registerBlock(blockRoutingMarshaller, "YATSBlockRoutingMarshaller");
		GameRegistry.registerTileEntity(TileRoutingMarshaller.class, "YATSTileRoutingMarshaller");
		LanguageRegistry.addName(blockRoutingMarshaller, "Routing Marshaller");

		GameRegistry.registerBlock(blockExtractor, "YATSBlockExtractor");
		GameRegistry.registerTileEntity(TileExtractor.class, "YATSTileExtractor");
		LanguageRegistry.addName(blockExtractor, "Extractor");

		GameRegistry.registerBlock(blockItemBuffer,"YATSBlockItemBuffer");
		GameRegistry.registerTileEntity(TileItemBuffer.class, "YATSTileItemBuffer");
		LanguageRegistry.addName(blockItemBuffer,"Item Buffer");

		GameRegistry.registerBlock(blockTube,"YATSBlockTube");
		GameRegistry.registerTileEntity(TileTube.class, "YATSTileItemBuffer");
		LanguageRegistry.addName(blockTube, "Transport Tube");

		GameRegistry.registerBlock(blockRemoteExtractor, "YATSBlockRemoteExtractor");
		GameRegistry.registerTileEntity(TileRemoteExtractor.class, "YATSTileRemoteExtractor");
		LanguageRegistry.addName(blockRemoteExtractor,"Remote Extractor");

	}
}
