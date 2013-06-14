package YATS.util;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class ConfigHandler
{
	public static int blockTubeID;
	public static int blockControllerID;
	public static int blockExtractorID;
	public static int blockAdvExtractorID;
	public static int blockRoutingMachineID;
	public static int blockBufferID;

	public static void Load(FMLPreInitializationEvent e)
	{
		Configuration config = new Configuration(e.getSuggestedConfigurationFile());

		config.load();

		config.addCustomCategoryComment("block","These settings are the Block IDs. They affect the operation of the mod and WILL break existing worlds if changed.");

		Property tubeblockproperty = config.getBlock("block","TubeID",2200,"The Block ID for the Tube.");
		blockTubeID = tubeblockproperty.getInt();

		Property controllerblockproperty = config.getBlock("block", "ControlID", 2201, "The Block ID for the Network Controller.");
		blockControllerID = controllerblockproperty.getInt();

		Property extractorblockproperty = config.getBlock("block","ExtractorID", 2202, "The Block ID for the Item Extractor.");
		blockExtractorID = extractorblockproperty.getInt();

		Property advextractorblockproperty = config.getBlock("block", "AdvExtractorID", 2203, "The Block ID for the Advanced Extractor.");
		blockAdvExtractorID = advextractorblockproperty.getInt();

		Property routingmachineblockproperty = config.getBlock("block","RoutingMachineID",2204,"The Block ID for the Routing Machine.");
		blockRoutingMachineID = routingmachineblockproperty.getInt();

		Property bufferblockproperty = config.getBlock("block","BufferID",2205,"The Block ID for the Item Buffer.");
		blockBufferID = bufferblockproperty.getInt();
	}
}
