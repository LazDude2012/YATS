package YATS.block;

import YATS.tile.TileRoutingMarshaller;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockRoutingMarshaller extends Block implements ITileEntityProvider
{
	public BlockRoutingMarshaller(int id)
	{
		super(id, Material.rock);
		setCreativeTab(CreativeTabs.tabMisc);
	}
@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileRoutingMarshaller();
	}
}
