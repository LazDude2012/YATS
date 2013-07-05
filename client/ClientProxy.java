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
		ClientRegistry.bindTileEntitySpecialRenderer(TileTube.class, new TubeRenderer());
        	MinecraftForgeClient.registerItemRenderer(YATS.blockTube.blockID, new TubeRenderer());
	}
}
