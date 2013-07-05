package YATS.block;

import YATS.common.YATS;
import YATS.tile.TileAdvExtractor;
import YATS.tile.TileRoutingMarshaller;
import YATS.tile.TileRoutingMarshaller;
import YATS.util.LazUtils;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockRoutingMarshaller extends Block implements ITileEntityProvider
{
    public static Icon rm_side_normal_up;
    public static Icon rm_side_busy_up;
    public static Icon rm_side_normal_left;
    public static Icon rm_side_busy_left;
    public static Icon rm_side_normal_right;
    public static Icon rm_side_busy_right;
    public static Icon rm_side_normal_down;
    public static Icon rm_side_busy_down;
    public static Icon rm_tubeside;
    public static Icon rm_invside;

	public BlockRoutingMarshaller(int id)
	{
		super(id, Material.rock);
		setCreativeTab(YATS.tabYATS);
        setUnlocalizedName("YATSBlockRoutingMarshaller");
	}

    public void registerIcons(IconRegister register)
    {
        rm_side_busy_up=register.registerIcon("YATS:rm_busy_up");
        rm_side_normal_up=register.registerIcon("YATS:rm_normal_up");
        rm_side_busy_down=register.registerIcon("YATS:rm_busy_down");
        rm_side_normal_down=register.registerIcon("YATS:rm_normal_down");
        rm_side_busy_left=register.registerIcon("YATS:rm_busy_left");
        rm_side_normal_left=register.registerIcon("YATS:rm_normal_left");
        rm_side_busy_right=register.registerIcon("YATS:rm_busy_right");
        rm_side_normal_right=register.registerIcon("YATS:rm_normal_right");
        rm_tubeside = register.registerIcon("YATS:rm_tubeside");
        rm_invside = register.registerIcon("YATS:rm_invside");
    }

@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileRoutingMarshaller();
	}

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player,int side, float hitX, float hitY, float hitZ)
    {
        if(player.getHeldItem().isItemEqual(new ItemStack(YATS.itemSpanner,1))) return false;
        if(world.getBlockTileEntity(x,y,z) instanceof TileRoutingMarshaller)
            player.openGui(YATS.instance,YATS.ROUTINGMARSHALLER_GUI,world,x,y,z);
        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving placer, ItemStack placedItemStack)
    {
        TileRoutingMarshaller tile = (TileRoutingMarshaller)world.getBlockTileEntity(x,y,z);
        tile.RotateTo(LazUtils.GetFDFromEntity(placer, true));
    }

    public Icon getIcon(int side, int meta)
    {
        //INVENTORY BLOCKS FACE SOUTH
        ForgeDirection face = ForgeDirection.getOrientation(side);
        switch(face)
        {
            case UP:
                return rm_side_normal_down;
            case WEST:
                return rm_side_normal_right;
            case EAST:
                return rm_side_normal_left;
            case DOWN:
                return rm_side_normal_up;
            case NORTH:
                return rm_tubeside;
            case SOUTH:
                return rm_invside;
        }
        return rm_invside;
    }

    public Icon getBlockTexture(IBlockAccess world,int x, int y, int z, int side)
    {
        TileRoutingMarshaller ext = (TileRoutingMarshaller) world.getBlockTileEntity(x,y,z);
        ForgeDirection blockface = ForgeDirection.getOrientation(side);
        if(blockface == ext.GetCurrentFacing()) return rm_invside;
        if(blockface == ext.GetCurrentFacing().getOpposite()) return rm_tubeside;
        if(ext.isBusy)
        {
            switch(ext.GetCurrentFacing())
            {
                case UP:
                    return rm_side_busy_up;
                case DOWN:
                    return rm_side_busy_down;
                case WEST:
                    switch(blockface)
                    {
                        case NORTH:
                            return rm_side_busy_right;
                        case SOUTH:
                            return rm_side_busy_left;
                        case UP:
                            return rm_side_busy_left;
                        case DOWN:
                            return rm_side_busy_right;
                    }
                case EAST:
                    switch(blockface)
                    {
                        case NORTH:
                            return rm_side_busy_left;
                        case SOUTH:
                            return rm_side_busy_right;
                        case UP:
                            return rm_side_busy_right;
                        case DOWN:
                            return rm_side_busy_left;
                    }
                case NORTH:
                    switch(blockface)
                    {
                        case EAST:
                            return rm_side_busy_right;
                        case WEST:
                            return rm_side_busy_left;
                        case UP:
                            return rm_side_busy_up;
                        case DOWN:
                            return rm_side_busy_down;
                    }
                case SOUTH:
                    switch(blockface)
                    {
                        case EAST:
                            return rm_side_busy_left;
                        case WEST:
                            return rm_side_busy_right;
                        case UP:
                            return rm_side_busy_down;
                        case DOWN:
                            return rm_side_busy_up;
                    }
            }
        }
        else
        {
            switch(ext.GetCurrentFacing())
            {
                case UP:
                    return rm_side_normal_up;
                case DOWN:
                    return rm_side_normal_down;
                case WEST:
                    switch(blockface)
                    {
                        case NORTH:
                            return rm_side_normal_right;
                        case SOUTH:
                            return rm_side_normal_left;
                        case UP:
                            return rm_side_normal_left;
                        case DOWN:
                            return rm_side_normal_right;
                    }
                case EAST:
                    switch(blockface)
                    {
                        case NORTH:
                            return rm_side_normal_left;
                        case SOUTH:
                            return rm_side_normal_right;
                        case UP:
                            return rm_side_normal_right;
                        case DOWN:
                            return rm_side_normal_left;
                    }
                case NORTH:
                    switch(blockface)
                    {
                        case EAST:
                            return rm_side_normal_right;
                        case WEST:
                            return rm_side_normal_left;
                        case UP:
                            return rm_side_normal_up;
                        case DOWN:
                            return rm_side_normal_down;
                    }
                case SOUTH:
                    switch(blockface)
                    {
                        case EAST:
                            return rm_side_normal_left;
                        case WEST:
                            return rm_side_normal_right;
                        case UP:
                            return rm_side_normal_down;
                        case DOWN:
                            return rm_side_normal_up;
                    }
            }
        }
        return rm_tubeside;
    }
}
