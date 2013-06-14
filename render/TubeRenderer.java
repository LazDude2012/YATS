package YATS.render;

import YATS.tile.TileTube;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;
import org.lwjgl.opengl.GL11;

public class TubeRenderer extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler
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

	// TPW_RULES is the boss, he showed me what's up with OpenGL.
	private void RenderTube(RenderBlocks renderer, IBlockAccess world, Block block, TileTube tube, int x, int y, int z)
	{
		GL11.glPushMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture("/yats/resources/yats-tubecore.png");
		GL11.glTranslatef(x,y,z);
		GL11.glBegin(GL11.GL_QUADS);

		if(tube.IsConnectedOnSide(ForgeDirection.NORTH))
		{
			//region NORTH ARM
			//NORTH ARM FACE 1: WEST
			GL11.glTexCoord2f(0.0f,0.25f);
			GL11.glVertex3f(0.25f,0.75f,0.0f);
			GL11.glTexCoord2f(0.25f,0.25f);
			GL11.glVertex3f(0.25f,0.75f,0.25f);
			GL11.glTexCoord2f(0.0f,0.75f);
			GL11.glVertex3f(0.25f,0.25f,0.0f);
			GL11.glTexCoord2f(0.25f,0.75f);
			GL11.glVertex3f(0.25f,0.25f,0.25f);

			//NORTH ARM FACE 2: EAST
			GL11.glTexCoord2f(0.0f,0.25f);
			GL11.glVertex3f(0.75f,0.75f,0.0f);
			GL11.glTexCoord2f(0.25f,0.25f);
			GL11.glVertex3f(0.75f,0.75f,0.25f);
			GL11.glTexCoord2f(0.0f,0.75f);
			GL11.glVertex3f(0.75f,0.25f,0.0f);
			GL11.glTexCoord2f(0.25f,0.75f);
			GL11.glVertex3f(0.75f,0.25f,0.25f);

			//NORTH ARM FACE 3: TOP
			GL11.glTexCoord2f(0.25f,0.0f);
			GL11.glVertex3f(0.25f,0.75f,0.0f);
			GL11.glTexCoord2f(0.75f,0.0f);
			GL11.glVertex3f(0.75f,0.75f,0.0f);
			GL11.glTexCoord2f(0.25f,0.25f);
			GL11.glVertex3f(0.25f,0.75f,0.25f);
			GL11.glTexCoord2f(0.75f,0.25f);
			GL11.glVertex3f(0.75f,0.75f,0.25f);

			//NORTH ARM FACE 4: BOTTOM
			GL11.glTexCoord2f(0.25f,0.0f);
			GL11.glVertex3f(0.25f,0.25f,0.0f);
			GL11.glTexCoord2f(0.75f,0.0f);
			GL11.glVertex3f(0.75f,0.25f,0.0f);
			GL11.glTexCoord2f(0.25f,0.25f);
			GL11.glVertex3f(0.25f,0.25f,0.25f);
			GL11.glTexCoord2f(0.75f,0.25f);
			GL11.glVertex3f(0.75f,0.25f,0.25f);
			//endregion NORTH ARM
		} else {
			//region CORE FACE 1 : NORTH
			GL11.glTexCoord2f(0.25f,0.25f);
			GL11.glVertex3f(0.25f,0.75f,0.25f);
			GL11.glTexCoord2f(0.75f,0.25f);
			GL11.glVertex3f(0.75f,0.75f,0.25f);
			GL11.glTexCoord2f(0.25f,0.75f);
			GL11.glVertex3f(0.25f,0.25f,0.25f);
			GL11.glTexCoord2f(0.75f,0.75f);
			GL11.glVertex3f(0.75f,0.25f,0.25f);
			//endregion FACE 1
		}
		if(tube.IsConnectedOnSide(ForgeDirection.SOUTH))
		{
			//region SOUTH ARM

			//SOUTH ARM FACE 1: WEST
			GL11.glTexCoord2f(1.0f,0.25f);
			GL11.glVertex3f(0.25f,0.75f,1.0f);
			GL11.glTexCoord2f(0.75f,0.25f);
			GL11.glVertex3f(0.25f,0.75f,0.75f);
			GL11.glTexCoord2f(1.0f,0.75f);
			GL11.glVertex3f(0.25f,0.25f,1.0f);
			GL11.glTexCoord2f(0.75f,0.75f);
			GL11.glVertex3f(0.25f,0.25f,0.75f);

			//SOUTH ARM FACE 2: EAST
			GL11.glTexCoord2f(1.0f,0.25f);
			GL11.glVertex3f(0.75f,0.75f,1.0f);
			GL11.glTexCoord2f(0.75f,0.25f);
			GL11.glVertex3f(0.75f,0.75f,0.75f);
			GL11.glTexCoord2f(1.0f,0.75f);
			GL11.glVertex3f(0.75f,0.25f,1.0f);
			GL11.glTexCoord2f(0.75f,0.75f);
			GL11.glVertex3f(0.75f,0.25f,0.75f);

			//SOUTH ARM FACE 3: TOP
			GL11.glTexCoord2f(0.25f,1.0f);
			GL11.glVertex3f(0.25f,0.75f,1.0f);
			GL11.glTexCoord2f(0.75f,1.0f);
			GL11.glVertex3f(0.75f,0.75f,1.0f);
			GL11.glTexCoord2f(0.25f,0.75f);
			GL11.glVertex3f(0.25f,0.75f,0.75f);
			GL11.glTexCoord2f(0.75f,0.75f);
			GL11.glVertex3f(0.75f,0.75f,0.75f);

			//SOUTH ARM FACE 4: BOTTOM
			GL11.glTexCoord2f(0.25f,1.0f);
			GL11.glVertex3f(0.25f,0.25f,1.0f);
			GL11.glTexCoord2f(0.75f,1.0f);
			GL11.glVertex3f(0.75f,0.25f,1.0f);
			GL11.glTexCoord2f(0.25f,0.75f);
			GL11.glVertex3f(0.25f,0.25f,0.75f);
			GL11.glTexCoord2f(0.75f,0.75f);
			GL11.glVertex3f(0.75f,0.25f,0.75f);

			//endregion SOUTH ARM
		} else {
			//region CORE FACE 2 : SOUTH
			GL11.glTexCoord2f(0.25f,0.25f);
			GL11.glVertex3f(0.25f,0.75f,0.75f);
			GL11.glTexCoord2f(0.75f,0.25f);
			GL11.glVertex3f(0.75f,0.75f,0.75f);
			GL11.glTexCoord2f(0.25f,0.75f);
			GL11.glVertex3f(0.25f,0.25f,0.75f);
			GL11.glTexCoord2f(0.75f,0.75f);
			GL11.glVertex3f(0.75f,0.25f,0.75f);
			//endregion FACE 2
		}
		if(tube.IsConnectedOnSide(ForgeDirection.WEST))
		{
			//region WEST ARM

			//WEST ARM FACE 1: SOUTH
			GL11.glTexCoord2f(0.0f,0.25f);
			GL11.glVertex3f(0.0f,0.75f,0.75f);
			GL11.glTexCoord2f(0.25f,0.25f);
			GL11.glVertex3f(0.25f,0.75f,0.75f);
			GL11.glTexCoord2f(0.0f,0.75f);
			GL11.glVertex3f(0.0f,0.25f,0.75f);
			GL11.glTexCoord2f(0.25f,0.75f);
			GL11.glVertex3f(0.25f,0.25f,0.75f);

			//WEST ARM FACE 2: NORTH
			GL11.glTexCoord2f(0.0f,0.25f);
			GL11.glVertex3f(0.0f,0.75f,0.25f);
			GL11.glTexCoord2f(0.25f,0.25f);
			GL11.glVertex3f(0.25f,0.75f,0.25f);
			GL11.glTexCoord2f(0.0f,0.75f);
			GL11.glVertex3f(0.0f,0.25f,0.25f);
			GL11.glTexCoord2f(0.25f,0.75f);
			GL11.glVertex3f(0.25f,0.25f,0.25f);

			//WEST ARM FACE 3: TOP
			GL11.glTexCoord2f(0.25f,0.0f);
			GL11.glVertex3f(0.0f,0.75f,0.25f);
			GL11.glTexCoord2f(0.75f,0.0f);
			GL11.glVertex3f(0.0f,0.75f,0.75f);
			GL11.glTexCoord2f(0.25f,0.25f);
			GL11.glVertex3f(0.25f,0.75f,0.25f);
			GL11.glTexCoord2f(0.75f,0.25f);
			GL11.glVertex3f(0.25f,0.75f,0.75f);

			//WEST ARM FACE 4: BOTTOM
			GL11.glTexCoord2f(0.25f,0.0f);
			GL11.glVertex3f(0.0f,0.25f,0.25f);
			GL11.glTexCoord2f(0.75f,0.0f);
			GL11.glVertex3f(0.0f,0.25f,0.75f);
			GL11.glTexCoord2f(0.25f,0.25f);
			GL11.glVertex3f(0.25f,0.25f,0.25f);
			GL11.glTexCoord2f(0.75f,0.25f);
			GL11.glVertex3f(0.25f,0.25f,0.75f);

			//endregion
		} else {
			//region CORE FACE 3 : WEST
			GL11.glTexCoord2f(0.25f,0.25f);
			GL11.glVertex3f(0.25f,0.75f,0.25f);
			GL11.glTexCoord2f(0.75f,0.25f);
			GL11.glVertex3f(0.25f,0.75f,0.75f);
			GL11.glTexCoord2f(0.25f,0.75f);
			GL11.glVertex3f(0.25f,0.25f,0.25f);
			GL11.glTexCoord2f(0.75f,0.75f);
			GL11.glVertex3f(0.25f,0.25f,0.75f);
			//endregion FACE 3
		}
		if(tube.IsConnectedOnSide(ForgeDirection.EAST))
		{
			//region EAST ARM

			//EAST ARM FACE 1: SOUTH
			GL11.glTexCoord2f(1.0f,0.25f);
			GL11.glVertex3f(1.0f,0.75f,0.75f);
			GL11.glTexCoord2f(0.75f,0.25f);
			GL11.glVertex3f(0.75f,0.75f,0.75f);
			GL11.glTexCoord2f(1.0f,0.75f);
			GL11.glVertex3f(1.0f,0.25f,0.75f);
			GL11.glTexCoord2f(0.75f,0.75f);
			GL11.glVertex3f(0.75f,0.25f,0.75f);

			//EAST ARM FACE 2: NORTH
			GL11.glTexCoord2f(1.0f,0.25f);
			GL11.glVertex3f(1.0f,0.75f,0.25f);
			GL11.glTexCoord2f(0.75f,0.25f);
			GL11.glVertex3f(0.75f,0.75f,0.25f);
			GL11.glTexCoord2f(1.0f,0.75f);
			GL11.glVertex3f(1.0f,0.25f,0.25f);
			GL11.glTexCoord2f(0.75f,0.75f);
			GL11.glVertex3f(0.75f,0.25f,0.25f);

			//EAST ARM FACE 3: TOP
			GL11.glTexCoord2f(1.0f,0.25f);
			GL11.glVertex3f(1.0f,0.75f,0.25f);
			GL11.glTexCoord2f(0.75f,0.25f);
			GL11.glVertex3f(0.75f,0.75f,0.25f);
			GL11.glTexCoord2f(1.0f,0.75f);
			GL11.glVertex3f(1.0f,0.75f,0.75f);
			GL11.glTexCoord2f(0.75f,0.75f);
			GL11.glVertex3f(0.75f,0.75f,0.75f);

			//EAST ARM FACE 4: BOTTOM
			GL11.glTexCoord2f(1.0f,0.25f);
			GL11.glVertex3f(1.0f,0.25f,0.25f);
			GL11.glTexCoord2f(0.75f,0.25f);
			GL11.glVertex3f(0.75f,0.25f,0.25f);
			GL11.glTexCoord2f(1.0f,0.75f);
			GL11.glVertex3f(1.0f,0.25f,0.75f);
			GL11.glTexCoord2f(0.75f,0.75f);
			GL11.glVertex3f(0.75f,0.25f,0.75f);

			//endregion
		} else {
			//region CORE FACE 4: EAST
			GL11.glTexCoord2f(0.25f,0.25f);
			GL11.glVertex3f(0.75f,0.75f,0.25f);
			GL11.glTexCoord2f(0.75f,0.25f);
			GL11.glVertex3f(0.75f,0.75f,0.75f);
			GL11.glTexCoord2f(0.25f,0.75f);
			GL11.glVertex3f(0.75f,0.25f,0.25f);
			GL11.glTexCoord2f(0.75f,0.75f);
			GL11.glVertex3f(0.75f,0.25f,0.75f);
			//endregion
		}
		if(tube.IsConnectedOnSide(ForgeDirection.UP))
		{
			//region TOP ARM

			//TOP ARM FACE 1: NORTH
			GL11.glTexCoord2f(0.25f,0.0f);
			GL11.glVertex3f(0.25f,1.0f,0.25f);
			GL11.glTexCoord2f(0.75f,0.0f);
			GL11.glVertex3f(0.75f,1.0f,0.25f);
			GL11.glTexCoord2f(0.25f,0.25f);
			GL11.glVertex3f(0.25f,0.75f,0.25f);
			GL11.glTexCoord2f(0.75f,0.25f);
			GL11.glVertex3f(0.75f,0.75f,0.25f);

			//TOP ARM FACE 2: SOUTH
			GL11.glTexCoord2f(0.25f,0.0f);
			GL11.glVertex3f(0.25f,1.0f,0.75f);
			GL11.glTexCoord2f(0.75f,0.0f);
			GL11.glVertex3f(0.75f,1.0f,0.75f);
			GL11.glTexCoord2f(0.25f,0.25f);
			GL11.glVertex3f(0.25f,0.75f,0.75f);
			GL11.glTexCoord2f(0.75f,0.25f);
			GL11.glVertex3f(0.75f,0.75f,0.75f);

			//TOP ARM FACE 3: WEST
			GL11.glTexCoord2f(0.25f,0.0f);
			GL11.glVertex3f(0.25f,1.0f,0.25f);
			GL11.glTexCoord2f(0.75f,0.0f);
			GL11.glVertex3f(0.25f,1.0f,0.75f);
			GL11.glTexCoord2f(0.25f,0.25f);
			GL11.glVertex3f(0.25f,0.75f,0.25f);
			GL11.glTexCoord2f(0.75f,0.25f);
			GL11.glVertex3f(0.25f,0.75f,0.75f);

			//TOP ARM FACE 4: EAST
			GL11.glTexCoord2f(0.25f,0.0f);
			GL11.glVertex3f(0.75f,1.0f,0.25f);
			GL11.glTexCoord2f(0.75f,0.0f);
			GL11.glVertex3f(0.75f,1.0f,0.75f);
			GL11.glTexCoord2f(0.25f,0.25f);
			GL11.glVertex3f(0.75f,0.75f,0.25f);
			GL11.glTexCoord2f(0.75f,0.25f);
			GL11.glVertex3f(0.75f,0.75f,0.75f);

			//endregion
		} else {
			//region CORE FACE 5: TOP
			GL11.glTexCoord2f(0.25f,0.25f);
			GL11.glVertex3f(0.25f,0.75f,0.25f);
			GL11.glTexCoord2f(0.75f,0.25f);
			GL11.glVertex3f(0.75f,0.75f,0.25f);
			GL11.glTexCoord2f(0.25f,0.75f);
			GL11.glVertex3f(0.25f,0.75f,0.75f);
			GL11.glTexCoord2f(0.75f,0.75f);
			GL11.glVertex3f(0.75f,0.75f,0.75f);
			//endregion FACE 5
		}
		if(tube.IsConnectedOnSide(ForgeDirection.DOWN))
		{
			//region BOTTOM ARM

			//BOTTOM ARM FACE 1: NORTH
			GL11.glTexCoord2f(0.25f,1.0f);
			GL11.glVertex3f(0.25f,0.0f,0.25f);
			GL11.glTexCoord2f(0.75f,1.0f);
			GL11.glVertex3f(0.75f,0.0f,0.25f);
			GL11.glTexCoord2f(0.25f,0.75f);
			GL11.glVertex3f(0.25f,0.25f,0.25f);
			GL11.glTexCoord2f(0.75f,0.75f);
			GL11.glVertex3f(0.75f,0.25f,0.25f);

			//BOTTOM ARM FACE 2: SOUTH
			GL11.glTexCoord2f(0.25f,1.0f);
			GL11.glVertex3f(0.25f,0.0f,0.75f);
			GL11.glTexCoord2f(0.75f,1.0f);
			GL11.glVertex3f(0.75f,0.0f,0.75f);
			GL11.glTexCoord2f(0.25f,0.75f);
			GL11.glVertex3f(0.25f,0.25f,0.75f);
			GL11.glTexCoord2f(0.75f,0.75f);
			GL11.glVertex3f(0.75f,0.25f,0.75f);

			//BOTTOM ARM FACE 3: WEST
			GL11.glTexCoord2f(0.25f,1.0f);
			GL11.glVertex3f(0.25f,0.0f,0.25f);
			GL11.glTexCoord2f(0.75f,1.0f);
			GL11.glVertex3f(0.25f,0.0f,0.75f);
			GL11.glTexCoord2f(0.25f,0.75f);
			GL11.glVertex3f(0.25f,0.25f,0.25f);
			GL11.glTexCoord2f(0.75f,0.75f);
			GL11.glVertex3f(0.25f,0.25f,0.75f);

			//BOTTOM ARM FACE 4: EAST
			GL11.glTexCoord2f(0.25f,1.0f);
			GL11.glVertex3f(0.75f,0.0f,0.25f);
			GL11.glTexCoord2f(0.75f,1.0f);
			GL11.glVertex3f(0.75f,0.0f,0.75f);
			GL11.glTexCoord2f(0.25f,0.75f);
			GL11.glVertex3f(0.75f,0.25f,0.25f);
			GL11.glTexCoord2f(0.75f,0.75f);
			GL11.glVertex3f(0.75f,0.25f,0.75f);

			//endregion
		} else {
			// region CORE FACE 6: BOTTOM
			GL11.glTexCoord2f(0.25f,0.25f);
			GL11.glVertex3f(0.25f,0.25f,0.25f);
			GL11.glTexCoord2f(0.75f,0.25f);
			GL11.glVertex3f(0.75f,0.25f,0.25f);
			GL11.glTexCoord2f(0.25f,0.75f);
			GL11.glVertex3f(0.25f,0.25f,0.75f);
			GL11.glTexCoord2f(0.75f,0.75f);
			GL11.glVertex3f(0.75f,0.25f,0.75f);
			//endregion
		}
		GL11.glEnd();
		GL11.glPopMatrix();
	}

	@Override
	public boolean shouldRender3DInInventory()
	{
		return false;
	}

	@Override
	public int getRenderId()
	{
		return 4242;
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f)
	{
		TileTube tube = (TileTube)tileentity;
		tube.contents.GetRenderer().RenderCapsule(tube);
	}
}
