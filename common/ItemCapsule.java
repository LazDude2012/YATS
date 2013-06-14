package YATS.common;

import YATS.api.ICapsule;
import YATS.api.ICapsuleRenderer;
import YATS.render.ItemCapsuleRenderer;
import YATS.util.Colours;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;

public class ItemCapsule implements ICapsule
{
	private ItemStack contents;
	private Colours colourTag;
	private ForgeDirection heading;

	public ItemCapsule(ItemStack stack, Colours tag, ForgeDirection heading)
	{
		this.contents = stack;
		this.colourTag = tag;
		this.heading = heading;
	}

	@Override
	public Object GetContents()
	{
		return contents;
	}

	@Override
	public Colours GetColour()
	{
		return colourTag;
	}

	@Override
	public void SetColour(Colours colour)
	{
		colourTag = colour;
	}

	@Override
	public ForgeDirection GetHeading()
	{
		return heading;
	}

	@Override
	public void SetHeading(ForgeDirection heading)
	{
		this.heading = heading;
	}

	@Override
	public ICapsuleRenderer GetRenderer()
	{
		return new ItemCapsuleRenderer();
	}
}
