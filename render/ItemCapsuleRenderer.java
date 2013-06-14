package YATS.render;

import YATS.api.ICapsuleRenderer;
import YATS.common.ItemCapsule;
import YATS.tile.TileTube;
import YATS.util.Colours;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;

import javax.swing.plaf.ColorUIResource;

public class ItemCapsuleRenderer implements ICapsuleRenderer
{
	@Override
	public void RenderCapsule(TileTube tube)
	{
		ItemCapsule capsule = (ItemCapsule) tube.contents;
		ItemStack stack = (ItemStack)capsule.GetContents();
		Colours colour = capsule.GetColour();
		ForgeDirection heading = capsule.GetHeading();
		float progress = tube.progressThroughTube;

		EntityItem dummy = new EntityItem(tube.worldObj);
		dummy.setEntityItemStack(stack);

	}
}
