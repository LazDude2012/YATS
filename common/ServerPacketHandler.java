package YATS.common;

import YATS.tile.TileAdvExtractor;
import YATS.util.Colours;
import YATS.util.PacketTypes;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.util.logging.Level;

public class ServerPacketHandler implements IPacketHandler
{
    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
    {
        ByteArrayDataInput badi = ByteStreams.newDataInput(packet.data);
        PacketTypes type = PacketTypes.values()[badi.readInt()];
        switch(type)
        {
            case ADVEXT_COLOUR:
                int dim = badi.readInt();
                int x = badi.readInt();
                int y = badi.readInt();
                int z = badi.readInt();
                int newColour = badi.readInt();
                World world = DimensionManager.getWorld(dim);
                TileAdvExtractor tile = (TileAdvExtractor)world.getBlockTileEntity(x,y,z);
                tile.setFilterColour(Colours.values()[newColour]);
                break;
            default:
                throw new RuntimeException("People think they can outsmart me. Maybe. But I have yet to meet one who can outsmart RuntimeException.");
        }
    }
}
