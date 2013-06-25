/**
 * I6WayWrenchable.java provides an API for blocks which can be rotated with the YATS Spanner.
 *
 * Original author: LazDude2012
 *
 * Last revision: Unknown
 *
 * Documentation: Timmietimtim
 *
 * Documentation Updated: 6/24/2013
 */
package YATS.api;

import net.minecraftforge.common.ForgeDirection;

/**
 * I6WayWrenchable is the simple and easy API to let your blocks be rotated by the YATS Spanner.
 *
 * Recommended implementation is in the block, not the tile entity.
 */
public interface I6WayWrenchable
{
	/**
	 * This field is the block's current facing.
	 */
	ForgeDirection currentfacing = ForgeDirection.UNKNOWN;

	/**
	 * This is the set accessor for the block's ForgeDirection.
	 * @param direction The ForgeDirection to rotate to.
	 */
	public void RotateTo(ForgeDirection direction);

	/**
	 * This is the get accessor for the block's ForgeDirection.
	 * @return The ForgeDirection the block is currently facing.
	 */
	public ForgeDirection GetCurrentFacing();
}

