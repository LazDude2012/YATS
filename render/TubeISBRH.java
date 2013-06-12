package YATS.render;

import YATS.tile.TileTube;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

public class TubeISBRH implements ISimpleBlockRenderingHandler
{
	public final float PIPE_MIN_SIZE = 0.25f;
	public final float PIPE_MAX_SIZE = 0.75f;

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		TileEntity tile = world.getBlockTileEntity(x,y,z);
		if(tile instanceof TileTube)
		{
			TileTube tube = (TileTube)tile;
			RenderTube(renderer, world, block, tube, x,y,z);
			return true;
		}
		return false;
	}

	private void RenderTube(RenderBlocks renderer, IBlockAccess world, Block block, TileTube tube, int x, int y, int z)
	{

	}

	@Override
	public boolean shouldRender3DInInventory()
	{
		return false;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public int getRenderId()
	{
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
