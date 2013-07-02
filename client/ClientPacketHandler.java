package YATS.client;

import YATS.tile.TileAdvExtractor;
import YATS.util.LazUtils;
import YATS.util.PacketTypes;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 7/1/13
 * Time: 9:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClientPacketHandler implements IPacketHandler
{
    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
    {

    }
    public static void DispatchAdvExtPkt(TileAdvExtractor tile)
    {
        try
        {
            ByteArrayOutputStream bstream = new ByteArrayOutputStream();
            DataOutputStream stream = new DataOutputStream(bstream);
            stream.writeInt(PacketTypes.ADVEXT_COLOUR.ordinal());
            stream.writeInt(tile.worldObj.getWorldInfo().getDimension());
            stream.writeInt(tile.xCoord);
            stream.writeInt(tile.yCoord);
            stream.writeInt(tile.zCoord);
            stream.writeInt(tile.getFilterColour().ordinal());
            Packet250CustomPayload pkt = new Packet250CustomPayload("YATS",bstream.toByteArray());
            PacketDispatcher.sendPacketToServer(pkt);
        }
        catch (IOException e)
        {
            LazUtils.LogException(e.toString(),e);
        }
    }
}
