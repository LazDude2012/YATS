package YATS.client;

import YATS.block.BlockTube;
import YATS.common.CommonProxy;
import YATS.common.YATS;
import YATS.render.TubeRenderer;
import YATS.tile.TileTube;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void RegisterRenderInformation()
	{
		int tubeRenderID = RenderingRegistry.getNextAvailableRenderId();
		TubeRenderer tubeRenderer = new TubeRenderer(tubeRenderID);
		((BlockTube)YATS.blockTube).renderID = tubeRenderID;
		ClientRegistry.bindTileEntitySpecialRenderer(TileTube.class,tubeRenderer);
		RenderingRegistry.registerBlockHandler(tubeRenderer);
	}
}
