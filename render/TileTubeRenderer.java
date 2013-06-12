package YATS.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class TileTubeRenderer implements ISimpleBlockRenderingHandler
{
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		return false;  //To change body of implemented methods use File | Settings | File Templates.
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
