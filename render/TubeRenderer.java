package YATS.render;

import YATS.api.ICapsule;
import YATS.common.YATS;
import YATS.tile.TileTube;
import YATS.util.Colours;
import YATS.util.LazUtils;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import static org.lwjgl.opengl.GL11.*;

public class TubeRenderer extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler
{
	int renderID;
	protected RenderItem renderitem;

	public TubeRenderer(int renderID)
	{
		this.renderID=renderID;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		return true;
	}

	// TPW_RULES is the boss, he showed me what's up with OpenGL.
	private void RenderTube(IBlockAccess world, TileTube tube, double x, double y, double z)
	{
		this.bindTextureByName("/mods/YATS/textures/blocks/yats-tubecore.png");
		glPushMatrix();
		glTranslated(x, y, z);
		glDisable(GL_CULL_FACE);

		if(tube.IsConnectedOnSide(ForgeDirection.NORTH))
		{
			//region NORTH ARM
			//NORTH ARM FACE 1: WEST
			glBegin(GL_QUADS);
			glTexCoord2f(0.0f, 0.25f);
			glVertex3f(0.25f, 0.75f, 0.0f);
			glTexCoord2f(0.0f, 0.75f);
			glVertex3f(0.25f, 0.25f, 0.0f);
			glTexCoord2f(0.25f, 0.75f);
			glVertex3f(0.25f, 0.25f, 0.25f);
			glTexCoord2f(0.25f, 0.25f);
			glVertex3f(0.25f, 0.75f, 0.25f);
			glEnd();

			//NORTH ARM FACE 2: EAST
			glBegin(GL_QUADS);
			glTexCoord2f(0.0f, 0.25f);
			glVertex3f(0.75f, 0.75f, 0.0f);
			glTexCoord2f(0.0f, 0.75f);
			glVertex3f(0.75f, 0.25f, 0.0f);
			glTexCoord2f(0.25f, 0.75f);
			glVertex3f(0.75f, 0.25f, 0.25f);
			glTexCoord2f(0.25f, 0.25f);
			glVertex3f(0.75f, 0.75f, 0.25f);
			glEnd();

			//NORTH ARM FACE 3: TOP
			glBegin(GL_QUADS);
			glTexCoord2f(0.25f, 0.0f);
			glVertex3f(0.25f, 0.75f, 0.0f);
			glTexCoord2f(0.25f, 0.25f);
			glVertex3f(0.25f, 0.75f, 0.25f);
			glTexCoord2f(0.75f, 0.25f);
			glVertex3f(0.75f, 0.75f, 0.25f);
			glTexCoord2f(0.75f, 0.0f);
			glVertex3f(0.75f, 0.75f, 0.0f);
			glEnd();

			//NORTH ARM FACE 4: BOTTOM
			glBegin(GL_QUADS);
			glTexCoord2f(0.25f, 0.0f);
			glVertex3f(0.25f, 0.25f, 0.0f);
			glTexCoord2f(0.25f, 0.25f);
			glVertex3f(0.25f, 0.25f, 0.25f);
			glTexCoord2f(0.75f, 0.25f);
			glVertex3f(0.75f, 0.25f, 0.25f);
			glTexCoord2f(0.75f, 0.0f);
			glVertex3f(0.75f, 0.25f, 0.0f);
			glEnd();
			//endregion NORTH ARM
		} else {
			//region CORE FACE 1 : NORTH
			glBegin(GL_QUADS);
			glTexCoord2f(0.25f, 0.25f);
			glVertex3f(0.25f, 0.75f, 0.25f);
			glTexCoord2f(0.25f, 0.75f);
			glVertex3f(0.25f, 0.25f, 0.25f);
			glTexCoord2f(0.75f, 0.75f);
			glVertex3f(0.75f, 0.25f, 0.25f);
			glTexCoord2f(0.75f, 0.25f);
			glVertex3f(0.75f, 0.75f, 0.25f);
			glEnd();
			//endregion FACE 1
		}
		if(tube.IsConnectedOnSide(ForgeDirection.SOUTH))
		{
			//region SOUTH ARM

			//SOUTH ARM FACE 1: WEST
			glBegin(GL_QUADS);
			glTexCoord2f(1.0f, 0.25f);
			glVertex3f(0.25f, 0.75f, 1.0f);
			glTexCoord2f(1.0f, 0.75f);
			glVertex3f(0.25f, 0.25f, 1.0f);
			glTexCoord2f(0.75f, 0.75f);
			glVertex3f(0.25f, 0.25f, 0.75f);
			glTexCoord2f(0.75f, 0.25f);
			glVertex3f(0.25f, 0.75f, 0.75f);
			glEnd();

			//SOUTH ARM FACE 2: EAST
			glBegin(GL_QUADS);
			glTexCoord2f(1.0f, 0.25f);
			glVertex3f(0.75f, 0.75f, 1.0f);
			glTexCoord2f(1.0f, 0.75f);
			glVertex3f(0.75f, 0.25f, 1.0f);
			glTexCoord2f(0.75f, 0.75f);
			glVertex3f(0.75f, 0.25f, 0.75f);
			glTexCoord2f(0.75f, 0.25f);
			glVertex3f(0.75f, 0.75f, 0.75f);
			glEnd();

			//SOUTH ARM FACE 3: TOP
			glBegin(GL_QUADS);
			glTexCoord2f(0.25f, 1.0f);
			glVertex3f(0.25f, 0.75f, 1.0f);
			glTexCoord2f(0.25f, 0.75f);
			glVertex3f(0.25f, 0.75f, 0.75f);
			glTexCoord2f(0.75f, 0.75f);
			glVertex3f(0.75f, 0.75f, 0.75f);
			glTexCoord2f(0.75f, 1.0f);
			glVertex3f(0.75f, 0.75f, 1.0f);
			glEnd();

			//SOUTH ARM FACE 4: BOTTOM
			glBegin(GL_QUADS);
			glTexCoord2f(0.25f, 1.0f);
			glVertex3f(0.25f, 0.25f, 1.0f);
			glTexCoord2f(0.25f, 0.75f);
			glVertex3f(0.25f, 0.25f, 0.75f);
			glTexCoord2f(0.75f, 0.75f);
			glVertex3f(0.75f, 0.25f, 0.75f);
			glTexCoord2f(0.75f, 1.0f);
			glVertex3f(0.75f, 0.25f, 1.0f);
			glEnd();

			//endregion SOUTH ARM
		} else {
			//region CORE FACE 2 : SOUTH
			glBegin(GL_QUADS);
			glTexCoord2f(0.25f, 0.25f);
			glVertex3f(0.25f, 0.75f, 0.75f);
			glTexCoord2f(0.25f, 0.75f);
			glVertex3f(0.25f, 0.25f, 0.75f);
			glTexCoord2f(0.75f, 0.75f);
			glVertex3f(0.75f, 0.25f, 0.75f);
			glTexCoord2f(0.75f, 0.25f);
			glVertex3f(0.75f, 0.75f, 0.75f);
			glEnd();
			//endregion FACE 2
		}
		if(tube.IsConnectedOnSide(ForgeDirection.WEST))
		{
			//region WEST ARM

			//WEST ARM FACE 1: SOUTH
			glBegin(GL_QUADS);
			glTexCoord2f(0.0f, 0.25f);
			glVertex3f(0.0f, 0.75f, 0.75f);
			glTexCoord2f(0.0f, 0.75f);
			glVertex3f(0.0f, 0.25f, 0.75f);
			glTexCoord2f(0.25f, 0.75f);
			glVertex3f(0.25f, 0.25f, 0.75f);
			glTexCoord2f(0.25f, 0.25f);
			glVertex3f(0.25f, 0.75f, 0.75f);
			glEnd();

			//WEST ARM FACE 2: NORTH
			glBegin(GL_QUADS);
			glTexCoord2f(0.0f, 0.25f);
			glVertex3f(0.0f, 0.75f, 0.25f);
			glTexCoord2f(0.0f, 0.75f);
			glVertex3f(0.0f, 0.25f, 0.25f);
			glTexCoord2f(0.25f, 0.75f);
			glVertex3f(0.25f, 0.25f, 0.25f);
			glTexCoord2f(0.25f, 0.25f);
			glVertex3f(0.25f, 0.75f, 0.25f);
			glEnd();

			//WEST ARM FACE 3: TOP
			glBegin(GL_QUADS);
			glTexCoord2f(0.25f, 0.0f);
			glVertex3f(0.0f, 0.75f, 0.25f);
			glTexCoord2f(0.25f, 0.25f);
			glVertex3f(0.25f, 0.75f, 0.25f);
			glTexCoord2f(0.75f, 0.25f);
			glVertex3f(0.25f, 0.75f, 0.75f);
			glTexCoord2f(0.75f, 0.0f);
			glVertex3f(0.0f, 0.75f, 0.75f);
			glEnd();

			//WEST ARM FACE 4: BOTTOM
			glBegin(GL_QUADS);
			glTexCoord2f(0.25f, 0.0f);
			glVertex3f(0.0f, 0.25f, 0.25f);
			glTexCoord2f(0.25f, 0.25f);
			glVertex3f(0.25f, 0.25f, 0.25f);
			glTexCoord2f(0.75f, 0.25f);
			glVertex3f(0.25f, 0.25f, 0.75f);
			glTexCoord2f(0.75f, 0.0f);
			glVertex3f(0.0f, 0.25f, 0.75f);
			glEnd();

			//endregion
		} else {
			//region CORE FACE 3 : WEST
			glBegin(GL_QUADS);
			glTexCoord2f(0.25f, 0.25f);
			glVertex3f(0.25f, 0.75f, 0.25f);
			glTexCoord2f(0.25f, 0.75f);
			glVertex3f(0.25f, 0.25f, 0.25f);
			glTexCoord2f(0.75f, 0.75f);
			glVertex3f(0.25f, 0.25f, 0.75f);
			glTexCoord2f(0.75f, 0.25f);
			glVertex3f(0.25f, 0.75f, 0.75f);
			glEnd();
			//endregion FACE 3
		}
		if(tube.IsConnectedOnSide(ForgeDirection.EAST))
		{
			//region EAST ARM

			//EAST ARM FACE 1: SOUTH
			glBegin(GL_QUADS);
			glTexCoord2f(1.0f, 0.25f);
			glVertex3f(1.0f, 0.75f, 0.75f);
			glTexCoord2f(1.0f, 0.75f);
			glVertex3f(1.0f, 0.25f, 0.75f);
			glTexCoord2f(0.75f, 0.75f);
			glVertex3f(0.75f, 0.25f, 0.75f);
			glTexCoord2f(0.75f, 0.25f);
			glVertex3f(0.75f, 0.75f, 0.75f);
			glEnd();

			//EAST ARM FACE 2: NORTH
			glBegin(GL_QUADS);
			glTexCoord2f(1.0f, 0.25f);
			glVertex3f(1.0f, 0.75f, 0.25f);
			glTexCoord2f(1.0f, 0.75f);
			glVertex3f(1.0f, 0.25f, 0.25f);
			glTexCoord2f(0.75f, 0.75f);
			glVertex3f(0.75f, 0.25f, 0.25f);
			glTexCoord2f(0.75f, 0.25f);
			glVertex3f(0.75f, 0.75f, 0.25f);
			glEnd();

			//EAST ARM FACE 3: TOP
			glBegin(GL_QUADS);
			glTexCoord2f(1.0f, 0.25f);
			glVertex3f(1.0f, 0.75f, 0.25f);
			glTexCoord2f(1.0f, 0.75f);
			glVertex3f(1.0f, 0.75f, 0.75f);
			glTexCoord2f(0.75f, 0.75f);
			glVertex3f(0.75f, 0.75f, 0.75f);
			glTexCoord2f(0.75f, 0.25f);
			glVertex3f(0.75f, 0.75f, 0.25f);
			glEnd();

			//EAST ARM FACE 4: BOTTOM
			glBegin(GL_QUADS);
			glTexCoord2f(1.0f, 0.25f);
			glVertex3f(1.0f, 0.25f, 0.25f);
			glTexCoord2f(1.0f, 0.75f);
			glVertex3f(1.0f, 0.25f, 0.75f);
			glTexCoord2f(0.75f, 0.75f);
			glVertex3f(0.75f, 0.25f, 0.75f);
			glTexCoord2f(0.75f, 0.25f);
			glVertex3f(0.75f, 0.25f, 0.25f);
			glEnd();

			//endregion
		} else {
			//region CORE FACE 4: EAST
			glBegin(GL_QUADS);
			glTexCoord2f(0.25f, 0.25f);
			glVertex3f(0.75f, 0.75f, 0.25f);
			glTexCoord2f(0.25f, 0.75f);
			glVertex3f(0.75f, 0.25f, 0.25f);
			glTexCoord2f(0.75f, 0.75f);
			glVertex3f(0.75f, 0.25f, 0.75f);
			glTexCoord2f(0.75f, 0.25f);
			glVertex3f(0.75f, 0.75f, 0.75f);
			glEnd();
			//endregion
		}
		if(tube.IsConnectedOnSide(ForgeDirection.UP))
		{
			//region TOP ARM

			//TOP ARM FACE 1: NORTH
			glBegin(GL_QUADS);
			glTexCoord2f(0.25f, 0.0f);
			glVertex3f(0.25f, 1.0f, 0.25f);
			glTexCoord2f(0.25f, 0.25f);
			glVertex3f(0.25f, 0.75f, 0.25f);
			glTexCoord2f(0.75f, 0.25f);
			glVertex3f(0.75f, 0.75f, 0.25f);
			glTexCoord2f(0.75f, 0.0f);
			glVertex3f(0.75f, 1.0f, 0.25f);
			glEnd();

			//TOP ARM FACE 2: SOUTH
			glBegin(GL_QUADS);
			glTexCoord2f(0.25f, 0.0f);
			glVertex3f(0.25f, 1.0f, 0.75f);
			glTexCoord2f(0.25f, 0.25f);
			glVertex3f(0.25f, 0.75f, 0.75f);
			glTexCoord2f(0.75f, 0.25f);
			glVertex3f(0.75f, 0.75f, 0.75f);
			glTexCoord2f(0.75f, 0.0f);
			glVertex3f(0.75f, 1.0f, 0.75f);
			glEnd();

			//TOP ARM FACE 3: WEST
			glBegin(GL_QUADS);
			glTexCoord2f(0.25f, 0.0f);
			glVertex3f(0.25f, 1.0f, 0.25f);
			glTexCoord2f(0.25f, 0.25f);
			glVertex3f(0.25f, 0.75f, 0.25f);
			glTexCoord2f(0.75f, 0.25f);
			glVertex3f(0.25f, 0.75f, 0.75f);
			glTexCoord2f(0.75f, 0.0f);
			glVertex3f(0.25f, 1.0f, 0.75f);
			glEnd();

			//TOP ARM FACE 4: EAST
			glBegin(GL_QUADS);
			glTexCoord2f(0.25f, 0.0f);
			glVertex3f(0.75f, 1.0f, 0.25f);
			glTexCoord2f(0.25f, 0.25f);
			glVertex3f(0.75f, 0.75f, 0.25f);
			glTexCoord2f(0.75f, 0.25f);
			glVertex3f(0.75f, 0.75f, 0.75f);
			glTexCoord2f(0.75f, 0.0f);
			glVertex3f(0.75f, 1.0f, 0.75f);
			glEnd();

			//endregion
		} else {
			//region CORE FACE 5: TOP
			glBegin(GL_QUADS);
			glTexCoord2f(0.25f, 0.25f);
			glVertex3f(0.25f, 0.75f, 0.25f);
			glTexCoord2f(0.25f, 0.75f);
			glVertex3f(0.25f, 0.75f, 0.75f);
			glTexCoord2f(0.75f, 0.75f);
			glVertex3f(0.75f, 0.75f, 0.75f);
			glTexCoord2f(0.75f, 0.25f);
			glVertex3f(0.75f, 0.75f, 0.25f);
			glEnd();
			//endregion FACE 5
		}
		if(tube.IsConnectedOnSide(ForgeDirection.DOWN))
		{
			//region BOTTOM ARM

			//BOTTOM ARM FACE 1: NORTH
			glBegin(GL_QUADS);
			glTexCoord2f(0.25f, 1.0f);
			glVertex3f(0.25f, 0.0f, 0.25f);
			glTexCoord2f(0.25f, 0.75f);
			glVertex3f(0.25f, 0.25f, 0.25f);
			glTexCoord2f(0.75f, 0.75f);
			glVertex3f(0.75f, 0.25f, 0.25f);
			glTexCoord2f(0.75f, 1.0f);
			glVertex3f(0.75f, 0.0f, 0.25f);
			glEnd();

			//BOTTOM ARM FACE 2: SOUTH
			glBegin(GL_QUADS);
			glTexCoord2f(0.25f, 1.0f);
			glVertex3f(0.25f, 0.0f, 0.75f);
			glTexCoord2f(0.25f, 0.75f);
			glVertex3f(0.25f, 0.25f, 0.75f);
			glTexCoord2f(0.75f, 0.75f);
			glVertex3f(0.75f, 0.25f, 0.75f);
			glTexCoord2f(0.75f, 1.0f);
			glVertex3f(0.75f, 0.0f, 0.75f);
			glEnd();

			//BOTTOM ARM FACE 3: WEST
			glBegin(GL_QUADS);
			glTexCoord2f(0.25f, 1.0f);
			glVertex3f(0.25f, 0.0f, 0.25f);
			glTexCoord2f(0.25f, 0.75f);
			glVertex3f(0.25f, 0.25f, 0.25f);
			glTexCoord2f(0.75f, 0.75f);
			glVertex3f(0.25f, 0.25f, 0.75f);
			glTexCoord2f(0.75f, 1.0f);
			glVertex3f(0.25f, 0.0f, 0.75f);
			glEnd();

			//BOTTOM ARM FACE 4: EAST
			glBegin(GL_QUADS);
			glTexCoord2f(0.25f, 1.0f);
			glVertex3f(0.75f, 0.0f, 0.25f);
			glTexCoord2f(0.25f, 0.75f);
			glVertex3f(0.75f, 0.25f, 0.25f);
			glTexCoord2f(0.75f, 0.75f);
			glVertex3f(0.75f, 0.25f, 0.75f);
			glTexCoord2f(0.75f, 1.0f);
			glVertex3f(0.75f, 0.0f, 0.75f);
			glEnd();

			//endregion
		} else {
			// region CORE FACE 6: BOTTOM
			glBegin(GL_QUADS);
			glTexCoord2f(0.25f, 0.25f);
			glVertex3f(0.25f, 0.25f, 0.25f);
			glTexCoord2f(0.25f, 0.75f);
			glVertex3f(0.25f, 0.25f, 0.75f);
			glTexCoord2f(0.75f, 0.75f);
			glVertex3f(0.75f, 0.25f, 0.75f);
			glTexCoord2f(0.75f, 0.25f);
			glVertex3f(0.75f, 0.25f, 0.25f);
			glEnd();
			//endregion
		}
		glEnable(GL_CULL_FACE);
		glPopMatrix();
	}

	@Override
	public boolean shouldRender3DInInventory()
	{
		return false;
	}

	@Override
	public int getRenderId()
	{
		return renderID;
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f)
	{
		glPushMatrix();
		glDisable(GL_LIGHTING);
		TileTube tube = (TileTube)tileentity;
		RenderTube(tube.worldObj, tube, x,y,z);
		if(tube.GetColour() != Colours.NONE) RenderPaint(tube.worldObj,tube,x,y,z);
		if(tube.contents.size() != 0 && YATS.IS_DEBUG)
		LazUtils.logNormal("Curiosity! Tube contents size: %s",tube.contents.size());
		for(ICapsule capsule : tube.contents)
		{
			if(YATS.IS_DEBUG)
				FMLLog.info("Sensuality! Rendering capsule at %s, %s, %s!");
			capsule.GetRenderer().RenderCapsule(tube,capsule,x,y,z);
		}
		glEnable(GL_LIGHTING);
		glPopMatrix();
	}

	private void RenderPaint(World world, TileTube tube, double x, double y, double z)
	{
		Colours colour = tube.GetColour();
		if(colour == Colours.NONE) return;
		byte[] rgb = Colours.toRGB(colour);
		bindTextureByName("/mods/YATS/textures/blocks/yats-paintring.png");
		glPushMatrix();
		glTranslated(x,y,z);
		glDisable(GL_CULL_FACE);
		glColor3ub(rgb[0],rgb[1],rgb[2]);

		//region PAINT RING: OUTSIDE FACES

		//COORDS: LOW 0.21875 HIGH 0.78125
		//bottom, top, bottom, top
		glBegin(GL_QUAD_STRIP);
		glTexCoord2f(0f,1f);
		glVertex3d(0.21875,0.21875,0.21875);
		glTexCoord2f(0f,0f);
		glVertex3d(0.21875,0.78125,0.21875);
		glTexCoord2f(1f,1f);
		glVertex3d(0.78125,0.21875,0.21875);
		glTexCoord2f(1f,0f);
		glVertex3d(0.78125,0.78125,0.21875);
		glTexCoord2f(0f,1f);
		glVertex3d(0.78125,0.21875,0.78125);
		glTexCoord2f(0f,0f);
		glVertex3d(0.78125,0.78125,0.78125);
		glTexCoord2f(1f,1f);
		glVertex3d(0.21875,0.21875,0.78125);
		glTexCoord2f(1,0);
		glVertex3d(0.21875,0.78125,0.78125);
		glTexCoord2f(0f,1f);
		glVertex3d(0.21875,0.21875,0.21875);
		glTexCoord2f(0f,0f);
		glVertex3d(0.21875,0.78125,0.21875);
		glEnd();
		glBegin(GL_QUADS);
		glTexCoord2f(0f,0f);
		glVertex3d(0.21875,0.78125,0.21875);
		glTexCoord2f(0f,1f);
		glVertex3d(0.21875,0.78125,0.78125);
		glTexCoord2f(1f,1f);
		glVertex3d(0.78125,0.78125,0.78125);
		glTexCoord2f(1f,0f);
		glVertex3d(0.78125,0.78125,0.21875);
		glTexCoord2f(0f,0f);
		glVertex3d(0.21875,0.21875,0.21875);
		glTexCoord2f(0f,1f);
		glVertex3d(0.21875,0.21875,0.78125);
		glTexCoord2f(1f,1f);
		glVertex3d(0.78125,0.21875,0.78125);
		glTexCoord2f(1f,0f);
		glVertex3d(0.78125,0.21875,0.21875);
		glEnd();
		glPopMatrix();
		glEnable(GL_CULL_FACE);
	}
}
