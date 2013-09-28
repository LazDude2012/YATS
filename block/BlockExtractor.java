package YATS.block;

import YATS.api.I6WayWrenchable;
import YATS.common.YATS;
import YATS.tile.TileAdvExtractor;
import YATS.tile.TileExtractor;
import YATS.util.LazUtils;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockExtractor extends Block implements ITileEntityProvider
{
	public static Icon lowmachine_tubeside;
	public static Icon lowmachine_invside;
	public static Icon extractor_side_normal_up;
	public static Icon extractor_side_busy_up;
	public static Icon extractor_side_normal_left;
	public static Icon extractor_side_busy_left;
	public static Icon extractor_side_normal_right;
	public static Icon extractor_side_busy_right;
	public static Icon extractor_side_normal_down;
	public static Icon extractor_side_busy_down;


	public void registerIcons(IconRegister register)
	{
		lowmachine_invside = register.registerIcon("lazdude_yats:lowmachine_invside");
		lowmachine_tubeside = register.registerIcon("lazdude_yats:lowmachine_tubeside");
		extractor_side_busy_up=register.registerIcon("lazdude_yats:extractor_side_busy_up");
		extractor_side_normal_up=register.registerIcon("lazdude_yats:extractor_side_normal_up");
		extractor_side_busy_down=register.registerIcon("lazdude_yats:extractor_side_busy_down");
		extractor_side_normal_down=register.registerIcon("lazdude_yats:extractor_side_normal_down");
		extractor_side_busy_left=register.registerIcon("lazdude_yats:extractor_side_busy_left");
		extractor_side_normal_left=register.registerIcon("lazdude_yats:extractor_side_normal_left");
		extractor_side_busy_right=register.registerIcon("lazdude_yats:extractor_side_busy_right");
		extractor_side_normal_right=register.registerIcon("lazdude_yats:extractor_side_normal_right");
	}

	public BlockExtractor(int id)
	{
		super(id, Material.rock);
		setCreativeTab(YATS.tabYATS);
		setUnlocalizedName("YATSBlockExtractor");
	}
	public TileEntity createNewTileEntity(World world)
	{
		return new TileExtractor();
	}

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack placedItemStack)
    {
        TileExtractor tile = (TileExtractor)world.getBlockTileEntity(x,y,z);
        tile.RotateTo(LazUtils.GetFDFromEntity(placer, true));
    }

    public Icon getIcon(int side, int meta)
    {
        //INVENTORY BLOCKS FACE SOUTH
        ForgeDirection face = ForgeDirection.getOrientation(side);
        switch(face)
        {
            case UP:
                return extractor_side_normal_down;
            case WEST:
                return extractor_side_normal_right;
            case EAST:
                return extractor_side_normal_left;
            case DOWN:
                return extractor_side_normal_up;
            case NORTH:
                return lowmachine_tubeside;
            case SOUTH:
                return lowmachine_invside;
        }
        return lowmachine_invside;
    }

	public Icon getBlockTexture(IBlockAccess world,int x, int y, int z, int side)
{
	TileExtractor ext = (TileExtractor) world.getBlockTileEntity(x,y,z);
	ForgeDirection blockface = ForgeDirection.getOrientation(side);
	if(blockface == ext.GetCurrentFacing()) return lowmachine_invside;
	if(blockface == ext.GetCurrentFacing().getOpposite()) return lowmachine_tubeside;
	if(ext.isBusy())
	{
		switch(ext.GetCurrentFacing())
		{
			case UP:
				return extractor_side_busy_up;
			case DOWN:
				return extractor_side_busy_down;
			case WEST:
				switch(blockface)
				{
					case NORTH:
						return extractor_side_busy_right;
					case SOUTH:
						return extractor_side_busy_left;
					case UP:
						return extractor_side_busy_left;
					case DOWN:
						return extractor_side_busy_right;
				}
			case EAST:
				switch(blockface)
				{
					case NORTH:
						return extractor_side_busy_left;
					case SOUTH:
						return extractor_side_busy_right;
					case UP:
						return extractor_side_busy_right;
					case DOWN:
						return extractor_side_busy_left;
				}
			case NORTH:
				switch(blockface)
				{
					case EAST:
						return extractor_side_busy_right;
					case WEST:
						return extractor_side_busy_left;
					case UP:
						return extractor_side_busy_up;
					case DOWN:
						return extractor_side_busy_down;
				}
			case SOUTH:
				switch(blockface)
				{
					case EAST:
						return extractor_side_busy_left;
					case WEST:
						return extractor_side_busy_right;
					case UP:
						return extractor_side_busy_down;
					case DOWN:
						return extractor_side_busy_up;
				}
		}
	}
	else
	{
		switch(ext.GetCurrentFacing())
		{
			case UP:
				return extractor_side_normal_up;
			case DOWN:
				return extractor_side_normal_down;
			case WEST:
				switch(blockface)
				{
					case NORTH:
						return extractor_side_normal_right;
					case SOUTH:
						return extractor_side_normal_left;
					case UP:
						return extractor_side_normal_left;
					case DOWN:
						return extractor_side_normal_right;
				}
			case EAST:
				switch(blockface)
				{
					case NORTH:
						return extractor_side_normal_left;
					case SOUTH:
						return extractor_side_normal_right;
					case UP:
						return extractor_side_normal_right;
					case DOWN:
						return extractor_side_normal_left;
				}
			case NORTH:
				switch(blockface)
				{
					case EAST:
						return extractor_side_normal_right;
					case WEST:
						return extractor_side_normal_left;
					case UP:
						return extractor_side_normal_up;
					case DOWN:
						return extractor_side_normal_down;
				}
			case SOUTH:
				switch(blockface)
				{
					case EAST:
						return extractor_side_normal_left;
					case WEST:
						return extractor_side_normal_right;
					case UP:
						return extractor_side_normal_down;
					case DOWN:
						return extractor_side_normal_up;
				}
		}
	}
	return lowmachine_tubeside;
	}
}
