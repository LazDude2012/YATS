/**
 * ItemCapsule.java provides an implementation of the ICapsule API specific to the transportation of items.
 *
 * Original author: LazDude2012
 *
 * Last revision: Unknown
 *
 * Documentation: Timmietimtim
 *
 * Documentation Updated: Mon 6/25/2013 14:30
 */
package YATS.capsule;

import YATS.api.ICapsule;
import YATS.api.ICapsuleRenderer;
import YATS.common.YATS;
import YATS.render.ItemCapsuleRenderer;
import YATS.util.Colours;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;

/**
 * The ItemCapsule implements the ICapsule API in a way that optimizes the transportation of items.
 */
public class ItemCapsule implements ICapsule
{
	    /** An ItemStack listing every item the capsule contains. */
    private ItemStack contents;
        /** By default, a capsule has no colour. Access with getColour() and setColour(). For explanation of colour,
         * see {@link ICapsule}.*/
	private Colours colourTag = Colours.NONE;
        /** Until otherwise declared, no assumptions will be made about the capsule's heading. Access with
         * getHeading() and setHeading(). */
	private ForgeDirection heading = ForgeDirection.UNKNOWN;
        /** All capsules begin with 0% progress through a tube segment. Access with getProgress(), addProgress(), and
         * resetProgress(). For explanation of progress, see {@link ICapsule}.*/
	private float progress = 0.0f;

	//USE AT YOUR OWN PERIL... SERIOUSLY, THIS IS A HACKY METHOD, AND A KLUDGE. USING IT MAKES YOU A TERRIBLE PERSON.
	//HITLER WOULD HAVE CALLED THIS CONSTRUCTOR.
	public ItemCapsule() { this(null, Colours.NONE,ForgeDirection.UNKNOWN); }

    /**
     * Constructor for an ItemCapsule to carry items in a capsule. We do what we can to make this easy.
     * @param stack An ItemStack listing all items to be contained in the new capsule.
     * @param tag A Colour to be associated with the capsule. See {@link ICapsule} for explanation.
     * @param heading The ForgeDirection in which the capsule is to travel initially.
     */
	public ItemCapsule(ItemStack stack, Colours tag, ForgeDirection heading)
	{
		this.contents = stack;
		this.colourTag = tag;
		this.heading = heading;
	}

    /**
     * Override of ICapsule's GetContents(), the get accessor for the capsule's contents.
     * @return An ItemStack of things the capsule contains.
     */
	@Override
	public Object GetContents()
	{
		return contents;
	}

    /**
     * Override of ICapsule's GetColour(), the get accessor for the capsule's colour.
     * @return The Colour associated with the capsule.
     */
	@Override
	public Colours GetColour()
	{
		return colourTag;
	}

    /**
     * Override of ICapsule's SetColour(), the set accessor for the capsule's colour.
     * @param colour The Colour to associate with the capsule.
     */
	@Override
	public void SetColour(Colours colour)
	{
		colourTag = colour;
	}

    /**
     * Override of ICapsule's GetHeading(), the get accessor for the capsule's heading.
     * @return The ForgeDirection the capsule is heading.
     */
	@Override
	public ForgeDirection GetHeading()
	{
		return heading;
	}

    /**
     * Override of ICapsule's SetHeading(), the set accessor for the capsule's heading.
     * @param heading The ForgeDirection the capsule is heading.
     */
	@Override
	public void SetHeading(ForgeDirection heading)
	{
		this.heading = heading;
	}

    /**
     * Override of ICapsule's GetRenderer(); provides for the correct rendering of item capsules.
     * @return An ItemCapsuleRenderer specified to render ItemCapsules.
     */
	@Override
	public ICapsuleRenderer GetRenderer()
	{
		return new ItemCapsuleRenderer();
	}

    /**
     * Override of ICapsule's getProgress(), the get accessor for the capsule's progress through its current segment.
     * @return A float expressing how much progress the capsule has made through a tube segment.
     */
	@Override
	public float getProgress()
	{
		return progress;
	}

    /**
     * Override of ICapsule's addProgress(); increases the capsule's progress through a tube segment.
     * @param progress A float expressing how much the capsule progresses in a tick.
     */
	@Override
	public void addProgress(float progress)
	{
		this.progress += progress;
	}

    /**
     * Override of ICapsule's resetProgress(); returns the capsule's progress through a tube segment to 0 (generally
     * because the capsule has passed into a new tube segment).
     */
	@Override
	public void resetProgress()
	{
		this.progress = 0;
	}

    /**
     * Implementation of ICapsule's getNBT(); sets several values for NBT, including the capsule's heading, colour,
     * progress, and whether the capsule has contents. If it does contain things, the contents are also written.
     * @return An NBTTagCompound for storage in NBT.
     */
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

    /**
     * Implementation of ICapsule's loadFromNBT(). Loads a capsule from NBT, reads contents (if any) and stores them;
     * and sets the heading, colour, and progress of the NBT-stored capsule.
     * @param nbt An NBTTagCompound to be read from NBT, typically a "capsule" compound.
     */
	public void loadFromNBT(NBTTagCompound nbt)
	{
		if(nbt.getBoolean("hasContents"))
		{
			contents = new ItemStack(Block.cobblestone,1);
			contents.readFromNBT(nbt);
		}
		heading = ForgeDirection.getOrientation(nbt.getInteger("direction"));
		colourTag = Colours.values()[nbt.getInteger("colour")];
		progress = nbt.getFloat("progress");
	}
}
