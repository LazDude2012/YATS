package YATS.common;

import YATS.api.YATSRegistry;
import YATS.capsule.ItemCapsule;
import YATS.item.ItemSpanner;
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
import net.minecraft.item.Item;

@Mod(modid="YATS",name="Yet Another Tube System",version="1.0")
public class YATS
{
	@Mod.Instance
	public static YATS instance;

	@SidedProxy(clientSide = "YATS.client.ClientProxy",serverSide="YATS.common.CommonProxy")
	public static CommonProxy proxy;

	public static boolean IS_DEBUG = true;

	public static Block blockRemoteExtractor;
	public static Block blockRoutingMarshaller;
	public static Block blockExtractor;
	public static Block blockAdvExtractor;
	public static Block blockTube;
	public static Block blockItemBuffer;

	public static Item itemSpanner;

	@Mod.PreInit
	public void PreInitialise(FMLPreInitializationEvent e)
	{
		ConfigHandler.Load(e);
		blockTube = new BlockTube(ConfigHandler.blockTubeID);
		blockExtractor=new BlockExtractor(ConfigHandler.blockExtractorID);
		blockRoutingMarshaller=new BlockRoutingMarshaller(ConfigHandler.blockRoutingMachineID);
		blockAdvExtractor=new BlockAdvExtractor(ConfigHandler.blockAdvExtractorID);
		blockRemoteExtractor=new BlockRemoteExtractor(ConfigHandler.blockRemoteExtractorID);
		blockItemBuffer = new BlockItemBuffer(ConfigHandler.blockBufferID);
		itemSpanner = new ItemSpanner(ConfigHandler.itemSpannerID);
	}

	@Mod.Init
	public void Initialise(FMLInitializationEvent e)
	{
		RegisterBlocksAndTiles();
		RegisterRecipes();
		RegisterItems();
		RegisterYATSCapsules();
		proxy.RegisterRenderInformation();
	}

	private void RegisterYATSCapsules()
	{
		YATSRegistry.registerCapsule(ItemCapsule.class);
	}

	private void RegisterItems()
	{
		GameRegistry.registerItem(itemSpanner,"YATSItemSpanner");
		LanguageRegistry.addName(itemSpanner,"Spanner");
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
		GameRegistry.registerTileEntity(TileTube.class, "YATSTileTube");
		LanguageRegistry.addName(blockTube, "Transport Tube");

		GameRegistry.registerBlock(blockRemoteExtractor, "YATSBlockRemoteExtractor");
		GameRegistry.registerTileEntity(TileRemoteExtractor.class, "YATSTileRemoteExtractor");
		LanguageRegistry.addName(blockRemoteExtractor,"Remote Extractor");

	}
}
