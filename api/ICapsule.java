/**
 * ICapsule.java provides an API for containers which can be transported through YATS Transportation Tubes.
 *
 * Original author: LazDude2012
 *
 * Last revision: Unknown
 *
 * Documentation: Timmietimtim
 *
 * Documentation Updated:
 */
package YATS.api;

import YATS.util.Colours;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;

/**
 * ICapsule is an interface for capsules which can be transported through YATS tubes.
 *
 * Capsules should be implemented according to what they are expected to contain, for
 * instance {@link YATS.capsule.ItemCapsule}, and rendered in the tubes similarly
 * ({@link YATS.render.ItemCapsuleRenderer}).
 *
 * Tubes will only accept capsules of the same colour or no colour.
 *
 * A capsule's progress is a float determining how often it advances through a tube from segment to segment.
 */
public interface ICapsule
{
    /**
     * This is the get accessor for the capsule's contents.
     * @return A stack consisting of things the capsule contains.
     */
	public Object GetContents();

    /**
     * This is the get accessor for the capsule's colour, for rendering and routing purposes.
     * @return The Colour of the capsule.
     */
	public Colours GetColour();

    /**
     * This is the set accessor for the capsule's colour, for rendering and routing purposes.
     * @param colour The Colour to associate with the capsule.
     */
	public void SetColour(Colours colour);

    /**
     * This is the get accessor for the direction the capsule is currently heading.
     * @return The ForgeDirection the capsule is currently heading.
     */
	public ForgeDirection GetHeading();

    /**
     * This is the set accessor for the direction the capsule is heading.
     * @param heading The ForgeDirection the capsule is heading.
     */
	public void SetHeading(ForgeDirection heading);

    /**
     * This is the general method for returning the correct type of renderer for a specific capsule. Makes it easy to
     * treat all capsule varieties with the same tube rendering code.
     * @return A renderer class unique to the type of capsule being rendered.
     */
	public ICapsuleRenderer GetRenderer();

    /**
     * This is the get accessor for a capsule's progress through a segment of tube.
     * @return A float number expressing how much progress the capsule has made through a tube segment.
     */
	public float getProgress();

    /**
     * This is the standard method of increasing a capsule's progress through a tube segment.
     * @param progress A float expressing how much the capsule progresses in a tick.
     */
	public void addProgress(float progress);

    /**
     * When overridden, this method reduces the capsule's progress to 0, usually when it passes into a new tube segment.
     */
	public void resetProgress();

    /**
     * TODO I can has documentation?
     * @return Who knows what?
     */
	public NBTTagCompound getNBT();

    /**
     * TODO Document this, apparently.
     * @param nbt
     */
	public void loadFromNBT(NBTTagCompound nbt);
}
