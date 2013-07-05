package YATS.common;

import YATS.api.YATSRegistry;
import YATS.capsule.ItemCapsule;
import YATS.client.ClientPacketHandler;
import YATS.common.gui.TabYATS;
import YATS.item.ItemSpanner;
import YATS.tile.*;
import YATS.util.ConfigHandler;
import YATS.util.GuiHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import YATS.block.*;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

@Mod(modid="YATS",name="Yet Another Tube System",version="1.0")
@NetworkMod(clientSideRequired = true,serverSideRequired = true,
clientPacketHandlerSpec = @NetworkMod.SidedPacketHandler(channels="YATS",packetHandler = ClientPacketHandler.class),
serverPacketHandlerSpec = @NetworkMod.SidedPacketHandler(channels="YATS",packetHandler = ServerPacketHandler.class))
public class YATS
{
	@Mod.Instance
	public static YATS instance;

	@SidedProxy(clientSide = "YATS.client.ClientProxy",serverSide="YATS.common.CommonProxy")
	public static CommonProxy proxy;

	public static boolean IS_DEBUG = false;

    public final static int ADVEXTRACTOR_GUI = 1;
    public final static int ROUTINGMARSHALLER_GUI = 2;

	public static Block blockRemoteExtractor;
	public static Block blockRoutingMarshaller;
	public static Block blockExtractor;
	public static Block blockAdvExtractor;
	public static Block blockTube;
	public static Block blockItemBuffer;

	public static Item itemSpanner;
	public static CreativeTabs tabYATS = new TabYATS(CreativeTabs.getNextID(),"YATSTab");
    public static GuiHandler guiHandler;

	@Mod.PreInit
	public void PreInitialise(FMLPreInitializationEvent e)
	{
        guiHandler = new GuiHandler();
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
        NetworkRegistry.instance().registerGuiHandler(this,guiHandler);
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
