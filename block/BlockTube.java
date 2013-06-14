package YATS.block;

import YATS.api.ITubeConnectible;
import YATS.tile.TileTube;
import YATS.util.XYZCoords;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockTube extends Block implements ITileEntityProvider
{
	public int renderID;
	public BlockTube(int id)
	{
		super(id, Material.rock);
		setCreativeTab(CreativeTabs.tabMisc);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entity, ItemStack itemStack)
	{
		CheckTubeConnections(world,x,y,z);
	}

	@Override
	public boolean renderAsNormalBlock() { return false; }

	@Override
	public boolean isOpaqueCube() { return false; }

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int neighborID)
	{
		CheckTubeConnections(world,x,y,z);
	}

	public void CheckTubeConnections(World world, int x, int y, int z)
	{
		TileTube originator = (TileTube)world.getBlockTileEntity(x,y,z);
		for(ForgeDirection side : ForgeDirection.VALID_DIRECTIONS)
		{
			TileEntity tile = XYZCoords.FromTile(originator).Next(side).ToTile();
			if(tile instanceof ITubeConnectible)
			{
				ITubeConnectible tube = (ITubeConnectible) tile;
				if(tube.IsConnectableOnSide(side.getOpposite()))
				{
					tube.SetConnectionOnSide(side.getOpposite(),true);
					originator.SetConnectionOnSide(side,true);
				}
				else
				{
					tube.SetConnectionOnSide(side.getOpposite(),true);
					originator.SetConnectionOnSide(side,true);
				}
			}
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileTube();
	}

	@Override
	public int getRenderType()
	{
		return renderID;
	}
}
