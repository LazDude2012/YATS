package YATS.render;

import YATS.api.ICapsule;
import YATS.api.ICapsuleRenderer;
import YATS.tile.TileTube;
import YATS.util.Colours;
import cpw.mods.fml.common.FMLLog;
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

		float renderScale = 0.7f;
		glPushMatrix();
		glTranslated(x, y, z);
		glTranslatef(0, 0.25F, 0);
		glScalef(renderScale, renderScale, renderScale);
		renderitem.doRenderItem(dummy, 0, 0, 0, 0, 0);
		glPopMatrix();

	}

}
