package YATS.render;

import YATS.api.ICapsule;
import YATS.api.ICapsuleRenderer;
import YATS.tile.TileTube;
import YATS.util.Colours;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;

import static org.lwjgl.opengl.GL11.*;

public class ItemCapsuleRenderer implements ICapsuleRenderer
{
	public RenderItem renderitem;
	@Override
	public void RenderCapsule(TileTube tube, ICapsule capsule, double x, double y, double z)
	{
		FMLLog.info("Desperation! I'm getting called to render a capsule!");
		ItemStack stack = (ItemStack)capsule.GetContents();
		Colours colour = capsule.GetColour();
		ForgeDirection heading = capsule.GetHeading();
		float progress = capsule.getProgress();

		EntityItem dummy = new EntityItem(tube.worldObj);
		dummy.setEntityItemStack(stack);

		renderitem = new RenderItem()
		{
			public boolean shouldBob() { return false; };
			public boolean shouldSpreadItems() { return false; };
		};
		renderitem.setRenderManager(RenderManager.instance);
		switch(capsule.GetHeading())
		{
			case UP:
				x += 0.5;
				y += progress;
				z += 0.5;
				break;
			case DOWN:
				x += 0.5;
				y += (1 - progress);
				z += 0.5;
				break;
			case WEST:
				x += (1 - progress);
				y += 0.5;
				z += 0.5;
				break;
			case EAST:
				x += progress;
				y += 0.5;
				z += 0.5;
				break;
			case NORTH:
				x += 0.5;
				y += 0.5;
				z += (1-progress);
				break;
			case SOUTH:
				x += 0.5;
				y += 0.5;
				z += progress;
				break;
		}

		float renderScale = 0.8f;
		glPushMatrix();
		glTranslated(x, y, z);
		glTranslatef(0,0.10F,0);
		glScalef(renderScale, renderScale, renderScale);
		renderitem.doRenderItem(dummy, 0, 0, 0, 0, 0);
		if (colour != Colours.NONE)
		{
			byte[] rgb = Colours.toRGB(colour);
			Minecraft.getMinecraft().renderEngine.bindTexture("/mods/YATS/textures/blocks/yats-paintring.png");
			glPushMatrix();
			glDisable(GL_CULL_FACE);
			glColor3ub(rgb[0],rgb[1],rgb[2]);
			glTranslated(-0.5,-0.5,-0.5);
			//region PAINT RING: OUTSIDE FACES

			//COORDS: LOW 0.26 HIGH 0.74
			//bottom, top, bottom, top
			glBegin(GL_QUAD_STRIP);
			glTexCoord2f(0f,1f);
			glVertex3d(0.26,0.26,0.26);
			glTexCoord2f(0f,0f);
			glVertex3d(0.26,0.74,0.26);
			glTexCoord2f(1f,1f);
			glVertex3d(0.74,0.26,0.26);
			glTexCoord2f(1f,0f);
			glVertex3d(0.74,0.74,0.26);
			glTexCoord2f(0f,1f);
			glVertex3d(0.74,0.26,0.74);
			glTexCoord2f(0f,0f);
			glVertex3d(0.74,0.74,0.74);
			glTexCoord2f(1f,1f);
			glVertex3d(0.26,0.26,0.74);
			glTexCoord2f(1,0);
			glVertex3d(0.26,0.74,0.74);
			glTexCoord2f(0f,1f);
			glVertex3d(0.26,0.26,0.26);
			glTexCoord2f(0f,0f);
			glVertex3d(0.26,0.74,0.26);
			glEnd();
			glBegin(GL_QUADS);
			glTexCoord2f(0f,0f);
			glVertex3d(0.26,0.74,0.26);
			glTexCoord2f(0f,1f);
			glVertex3d(0.26,0.74,0.74);
			glTexCoord2f(1f,1f);
			glVertex3d(0.74,0.74,0.74);
			glTexCoord2f(1f,0f);
			glVertex3d(0.74,0.74,0.26);
			glTexCoord2f(0f,0f);
			glVertex3d(0.26,0.26,0.26);
			glTexCoord2f(0f,1f);
			glVertex3d(0.26,0.26,0.74);
			glTexCoord2f(1f,1f);
			glVertex3d(0.74,0.26,0.74);
			glTexCoord2f(1f,0f);
			glVertex3d(0.74,0.26,0.26);
			glEnd();
			glPopMatrix();
			glEnable(GL_CULL_FACE);
			glPopMatrix();
		}

	}

}
