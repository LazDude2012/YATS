package YATS.util;

import net.minecraftforge.common.ForgeDirection;

/**
 * I6WayWrenchable is the simple and easy API to let your blocks be rotated by the YATS Plumbing Wrench.
 * Recommended implementation is in the block, not the tile entity.
 */
public interface I6WayWrenchable
{
	/**
	 * This field is self-explanatory; it's the block's current facing.
	 */
	ForgeDirection currentfacing = ForgeDirection.UNKNOWN;

	/**
	 * This is the set accessor for the ForgeDirection.
	 * @param direction The ForgeDirection to rotate to.
	 */
	public void RotateTo(ForgeDirection direction);

	/**
	 * This is the get accessor for the ForgeDirection.
	 * @return The ForgeDirection the block is currently facing toward.
	 */
	public ForgeDirection GetCurrentFacing();
}

