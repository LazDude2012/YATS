package YATS.block;

import YATS.tile.TileItemBuffer;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockItemBuffer extends Block implements ITileEntityProvider
{
	public BlockItemBuffer(int id)
	{
		super(id, Material.rock);
		setCreativeTab(CreativeTabs.tabMisc);
	}
	public TileEntity createNewTileEntity(World world)
	{
		return new TileItemBuffer();
	}
}
