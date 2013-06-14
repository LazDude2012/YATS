package YATS.block;

import YATS.tile.TileTube;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockTube extends Block implements ITileEntityProvider
{
	protected Icon[] icons = new Icon[17];
	public BlockTube(int id)
	{
		super(id, Material.rock);
		setCreativeTab(CreativeTabs.tabMisc);
	}

	public TileEntity createNewTileEntity(World world)
	{
		return new TileTube();
	}
}
