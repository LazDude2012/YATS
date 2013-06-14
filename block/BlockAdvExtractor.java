package YATS.block;

import YATS.tile.TileAdvExtractor;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAdvExtractor extends Block implements ITileEntityProvider
{
	public BlockAdvExtractor(int id)
	{
		super(id, Material.rock);
		setCreativeTab(CreativeTabs.tabMisc);
	}
	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileAdvExtractor();
	}
}
