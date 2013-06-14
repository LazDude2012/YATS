package YATS.block;

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
			if(tile instanceof TileTube)
			{
				TileTube tube = (TileTube) tile;
				if(tube.isConnectableOnSide[side.getOpposite().ordinal()])
				{
					tube.isConnectedOnSide[side.getOpposite().ordinal()]=true;
					originator.isConnectedOnSide[side.ordinal()]=true;
				}
				else
				{
					tube.isConnectedOnSide[side.getOpposite().ordinal()] = false;
					originator.isConnectedOnSide[side.ordinal()] = false;
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
