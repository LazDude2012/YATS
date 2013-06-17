package YATS.api;

import YATS.api.ICapsule;
import YATS.tile.TileExtractor;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.Dictionary;

public class YATSRegistry
{
	protected static Dictionary<Integer,Class <? extends ICapsule>> capsuleTypes;
	protected static int nextID;

	public static void registerCapsuleType(Class<? extends ICapsule> capsuleClass)
	{
		capsuleTypes.put(nextID,capsuleClass);

	}
	public static Class<? extends ICapsule> getCapsuleType(int id)
	{
		return capsuleTypes.get(id);
	}
}
