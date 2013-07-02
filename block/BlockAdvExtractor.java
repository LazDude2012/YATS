package YATS.block;

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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockAdvExtractor extends Block implements ITileEntityProvider
{
	public static Icon advextractor_side_normal_up;
	public static Icon advextractor_side_busy_up;
	public static Icon advextractor_side_normal_left;
	public static Icon advextractor_side_busy_left;
	public static Icon advextractor_side_normal_right;
	public static Icon advextractor_side_busy_right;
	public static Icon advextractor_side_normal_down;
	public static Icon advextractor_side_busy_down;
	public BlockAdvExtractor(int id)
	{
		super(id, Material.rock);
		setCreativeTab(YATS.tabYATS);
		setUnlocalizedName("YATSBlockAdvExtractor");
	}
	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileAdvExtractor();
	}
	public void registerIcons(IconRegister register)
	{
		advextractor_side_busy_up=register.registerIcon("YATS:advextractor_side_busy_up");
		advextractor_side_normal_up=register.registerIcon("YATS:advextractor_side_normal_up");
		advextractor_side_busy_down=register.registerIcon("YATS:advextractor_side_busy_down");
		advextractor_side_normal_down=register.registerIcon("YATS:advextractor_side_normal_down");
		advextractor_side_busy_left=register.registerIcon("YATS:advextractor_side_busy_left");
		advextractor_side_normal_left=register.registerIcon("YATS:advextractor_side_normal_left");
		advextractor_side_busy_right=register.registerIcon("YATS:advextractor_side_busy_right");
		advextractor_side_normal_right=register.registerIcon("YATS:advextractor_side_normal_right");
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving placer, ItemStack placedItemStack)
	{
		TileAdvExtractor tile = (TileAdvExtractor)world.getBlockTileEntity(x,y,z);
		tile.RotateTo(LazUtils.GetFDFromEntity(placer, true));
	}

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player,int side, float hitX, float hitY, float hitZ)
    {
        if(player.getHeldItem() == new ItemStack(YATS.itemSpanner,1)) return false;
        player.openGui(YATS.instance,YATS.ADVEXTRACTOR_GUI,world,x,y,z);
        return true;
    }

	public Icon getBlockTexture(IBlockAccess world,int x, int y, int z, int side)
	{
		TileAdvExtractor ext = (TileAdvExtractor) world.getBlockTileEntity(x,y,z);
		ForgeDirection blockface = ForgeDirection.getOrientation(side);
		if(blockface == ext.GetCurrentFacing()) return BlockExtractor.lowmachine_invside;
		if(blockface == ext.GetCurrentFacing().getOpposite()) return BlockExtractor.lowmachine_tubeside;
		if(ext.isBusy)
		{
			switch(ext.GetCurrentFacing())
			{
				case UP:
					return advextractor_side_busy_up;
				case DOWN:
					return advextractor_side_busy_down;
				case WEST:
					switch(blockface)
					{
						case NORTH:
							return advextractor_side_busy_right;
						case SOUTH:
							return advextractor_side_busy_left;
						case UP:
							return advextractor_side_busy_left;
						case DOWN:
							return advextractor_side_busy_right;
					}
				case EAST:
					switch(blockface)
					{
						case NORTH:
							return advextractor_side_busy_left;
						case SOUTH:
							return advextractor_side_busy_right;
						case UP:
							return advextractor_side_busy_right;
						case DOWN:
							return advextractor_side_busy_left;
					}
				case NORTH:
					switch(blockface)
					{
						case EAST:
							return advextractor_side_busy_right;
						case WEST:
							return advextractor_side_busy_left;
						case UP:
							return advextractor_side_busy_up;
						case DOWN:
							return advextractor_side_busy_down;
					}
				case SOUTH:
					switch(blockface)
					{
						case EAST:
							return advextractor_side_busy_left;
						case WEST:
							return advextractor_side_busy_right;
						case UP:
							return advextractor_side_busy_down;
						case DOWN:
							return advextractor_side_busy_up;
					}
			}
		}
		else
		{
			switch(ext.GetCurrentFacing())
			{
				case UP:
					return advextractor_side_normal_up;
				case DOWN:
					return advextractor_side_normal_down;
				case WEST:
					switch(blockface)
					{
						case NORTH:
							return advextractor_side_normal_right;
						case SOUTH:
							return advextractor_side_normal_left;
						case UP:
							return advextractor_side_normal_left;
						case DOWN:
							return advextractor_side_normal_right;
					}
				case EAST:
					switch(blockface)
					{
						case NORTH:
							return advextractor_side_normal_left;
						case SOUTH:
							return advextractor_side_normal_right;
						case UP:
							return advextractor_side_normal_right;
						case DOWN:
							return advextractor_side_normal_left;
					}
				case NORTH:
					switch(blockface)
					{
						case EAST:
							return advextractor_side_normal_right;
						case WEST:
							return advextractor_side_normal_left;
						case UP:
							return advextractor_side_normal_up;
						case DOWN:
							return advextractor_side_normal_down;
					}
				case SOUTH:
					switch(blockface)
					{
						case EAST:
							return advextractor_side_normal_left;
						case WEST:
							return advextractor_side_normal_right;
						case UP:
							return advextractor_side_normal_down;
						case DOWN:
							return advextractor_side_normal_up;
					}
			}
		}
		return BlockExtractor.lowmachine_tubeside;
	}
}
