package YATS.util;

import YATS.client.gui.GuiAdvExtractor;
import YATS.client.gui.GuiRoutingMarshaller;
import YATS.common.YATS;
import YATS.common.gui.ContainerAdvExtractor;
import YATS.common.gui.ContainerRoutingMarshaller;
import YATS.tile.TileAdvExtractor;
import YATS.tile.TileRoutingMarshaller;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch(ID)
        {
            case YATS.ADVEXTRACTOR_GUI:
                return new ContainerAdvExtractor(player.inventory,(TileAdvExtractor)world.getBlockTileEntity(x,y,z));
            case YATS.ROUTINGMARSHALLER_GUI:
                return new ContainerRoutingMarshaller(player.inventory,(TileRoutingMarshaller)world.getBlockTileEntity(x,y,z));
            default:
                throw new RuntimeException("People think they can outsmart me. Maybe. But I have yet to meet one who can outsmart RuntimeException.");
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch(ID)
        {
            case YATS.ADVEXTRACTOR_GUI:
                return new GuiAdvExtractor(player.inventory,(TileAdvExtractor)world.getBlockTileEntity(x,y,z));
            case YATS.ROUTINGMARSHALLER_GUI:
                return new GuiRoutingMarshaller(player.inventory,(TileRoutingMarshaller)world.getBlockTileEntity(x,y,z));
            default:
                throw new RuntimeException("People think they can outsmart me. Maybe. But I have yet to meet one who can outsmart RuntimeException.");
        }
    }
}
