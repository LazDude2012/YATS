package YATS.capsule;

import YATS.api.ICapsule;
import YATS.api.ICapsuleRenderer;
import YATS.common.YATS;
import YATS.render.ItemCapsuleRenderer;
import YATS.util.Colours;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;

public class ItemCapsule implements ICapsule
{
	private ItemStack contents;
	private Colours colourTag;
	private ForgeDirection heading;
	private float progress;

	//USE AT YOUR OWN PERIL... SERIOUSLY, THIS IS A HACKY METHOD, AND A KLUDGE. USING IT MAKES YOU A TERRIBLE PERSON.
	//HITLER WOULD HAVE CALLED THIS CONSTRUCTOR.
	public ItemCapsule() { this(null, Colours.NONE,ForgeDirection.UNKNOWN); }

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

	@Override
	public float getProgress()
	{
		return progress;
	}

	@Override
	public void addProgress(float progress)
	{
		this.progress += progress;
	}

	@Override
	public void resetProgress()
	{
		this.progress = 0;
	}

	public NBTTagCompound getNBT()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("direction",heading.ordinal());
		nbt.setInteger("colour",colourTag.ordinal());
		nbt.setFloat("progress",progress);
		nbt.setBoolean("hasContents",(contents != null));
		if(contents != null)
			contents.writeToNBT(nbt);
		return nbt;
	}

	public void loadFromNBT(NBTTagCompound nbt)
	{
		if(nbt.getBoolean("hasContents"))
			contents.readFromNBT(nbt);
		heading = ForgeDirection.getOrientation(nbt.getInteger("direction"));
		colourTag = Colours.values()[nbt.getInteger("colour")];
		progress = nbt.getFloat("progress");
	}
}
