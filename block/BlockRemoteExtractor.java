package YATS.block;

import YATS.tile.TileRemoteExtractor;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockRemoteExtractor extends Block implements ITileEntityProvider
{
	public BlockRemoteExtractor(int id)
	{
		super(id, Material.iron);
		setCreativeTab(CreativeTabs.tabMisc);
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileRemoteExtractor();
	}
}
