package YATS.util;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import scala.collection.immutable.StringOps;

import java.util.HashMap;
import java.util.Map;

public class ConfigHandler
{
    public static int blockTubeID;
    public static int blockControllerID;
    public static int blockExtractorID;
    public static int blockAdvExtractorID;
    public static int blockRoutingMachineID;
    public static int blockBufferID;
    public static int blockRemoteExtractorID;
    public static int itemSpannerID;

    public static Map<Colours, float[]> colors = new HashMap<Colours, float[]>();

    public static void Load(FMLPreInitializationEvent e)
    {
        Configuration config = new Configuration(e.getSuggestedConfigurationFile());

        config.load();

        config.addCustomCategoryComment("block", "These settings are the Block IDs. They affect the operation of the mod and WILL break existing worlds if changed.");

        Property tubeblockproperty = config.getBlock("block", "TubeID", 2200, "The Block ID for the Tube.");
        blockTubeID = tubeblockproperty.getInt();

        Property controllerblockproperty = config.getBlock("block", "ControlID", 2201, "The Block ID for the Network Controller.");
        blockControllerID = controllerblockproperty.getInt();

        Property extractorblockproperty = config.getBlock("block", "ExtractorID", 2202, "The Block ID for the Item Extractor.");
        blockExtractorID = extractorblockproperty.getInt();

        Property advextractorblockproperty = config.getBlock("block", "AdvExtractorID", 2203, "The Block ID for the Advanced Extractor.");
        blockAdvExtractorID = advextractorblockproperty.getInt();

        Property routingmachineblockproperty = config.getBlock("block", "RoutingMachineID", 2204, "The Block ID for the Routing Machine.");
        blockRoutingMachineID = routingmachineblockproperty.getInt();

        Property bufferblockproperty = config.getBlock("block", "BufferID", 2205, "The Block ID for the Item Buffer.");
        blockBufferID = bufferblockproperty.getInt();

        Property remoteextractorproperty = config.getBlock("block", "RemoteExtractorID", 2206, "The Block ID for the Remote Extractor");
        blockRemoteExtractorID = remoteextractorproperty.getInt();

        Property spannerproperty = config.getItem("item", "SpannerID", 4242, "The ID for the Spanner.");
        itemSpannerID = spannerproperty.getInt();

        for (Colours c : Colours.values()) {
            Property colorproperty = config.get("Color Values", c.name(), c.defaultColor);
            colors.put(c, getColorFromStr(colorproperty.getString()));
        }

        config.save();
    }

    private static float[] getColorFromStr(String s)
    {
        String[] cols = new String[3];
        (new StringOps(s)).grouped(2).copyToArray(cols);
        return new float[]{Integer.parseInt(cols[0], 16) / 255F, Integer.parseInt(cols[1], 16) / 255F, Integer.parseInt(cols[2], 16) / 255F};
    }
}
