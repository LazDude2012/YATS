package YATS.api;

import YATS.api.ICapsule;
import YATS.tile.TileExtractor;
import YATS.util.LazUtils;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.Dictionary;

public class YATSRegistry
{
	protected static ArrayList<Class<? extends ICapsule>> capsuleTypes = new ArrayList<Class<? extends ICapsule>>();

	public static int registerCapsule(Class<? extends ICapsule> capsule)
	{
		capsuleTypes.add(capsule);
		return capsuleTypes.indexOf(capsule);
	}
	public static int getIDFromCapsule(ICapsule capsule)
	{
		return capsuleTypes.indexOf(capsule.getClass());
	}
	public static Class<? extends ICapsule> getCapsuleTypeFromID(int id)
	{
		return (id < capsuleTypes.size() ? capsuleTypes.get(id) : null);
	}
	public static ICapsule handleCapsuleNBT(NBTTagCompound nbt)
	{
		try
		{
			int id = nbt.getInteger("capsuletype");
			ICapsule capsule = getCapsuleTypeFromID(id).getDeclaredConstructor().newInstance();
			capsule.loadFromNBT(nbt.getCompoundTag("capsule"));
			return capsule;
		}
		catch (Exception e)
		{
			LazUtils.LogException(e.getMessage(),e);
			return null;
		}
	}
	public static NBTTagCompound getCapsuleNBT(ICapsule capsule)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("capsuletype",getIDFromCapsule(capsule));
		nbt.setCompoundTag("capsule", capsule.getNBT());
		return nbt;
	}

}
