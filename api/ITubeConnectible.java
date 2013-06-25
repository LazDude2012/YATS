/**
 * ITubeConnectible.java provides an API for YATS tiles that connect to one another.
 *
 * Original author: LazDude2012
 *
 * Last revision: Unknown
 *
 * Documentation: Timmietimtim
 *
 * Documentation Updated: Tue 6/25/2013 14:14
 */
package YATS.api;

import YATS.util.Colours;
import net.minecraftforge.common.ForgeDirection;

/**
 * ITubeConnectible provides an API for YATS tiles (tubes, extractors, etc.) that connect to each other,
 * including behavior and state tests for cooperation between tiles and capsule routing.
 *
 * Tube pressure influences the rate at which capsules progress through tubes. For each tick, the amount of progress
 * added to the capsule equals pressure/100. For instance, a 5-pressure tube will advance 1 block every 20 ticks.
 *
 * Tube colour influences which capsules a tube will accept. For instance, an orange tube will only accept capsules that
 * are orange, or have no colour. A colourless tube will accept any capsule.
 *
 * Priority, in YATS, is the method by which tube routing picks routes through the tubes. See TubeRouting.java
 */
public interface ITubeConnectible
{
    /**
     * Tests whether the tile accepts incoming capsules on the given side.
     * @param side The side which is to be tested for acceptance of capsules, according to tile behavior
     * @return True if the tile accepts items on the given side, otherwise false.
     */
	public boolean AcceptsItemsOnSide(ForgeDirection side);

    /**
     * Tests whether the tile is connected to another connectible tile on the given side.
     * @param side The side which is to be tested for connection to another connectible tile.
     * @return True if the tile is connected to another connectible tile on the given side, otherwise false.
     */
	public boolean IsConnectedOnSide(ForgeDirection side);

    /**
     * Tests whether the tile will accept a capsule (ex. by colour - it's like racism for tubes).
     * @param capsule The capsule to be tested according to the conditions of the tile.
     * @return True if the tile will accept the capsule, otherwise false.
     */
	public boolean CanAccept(ICapsule capsule);

    /**
     * Tests whether the tile can route a capsule.
     * @return True if the tile can be utilized for routing a capsule, otherwise false.
     */
	public Boolean CanRoute();

    /**
     * This is the get accessor for the pressure within the tile.
     * @return An integer value for the tube pressure within the tile.
     */
	public int GetPressure();

    /**
     * This is the set accessor for the pressure within the tile.
     * @param pressure An integer value for the tube pressure within the tile.
     */
	public void SetPressure(int pressure);

    /**
     * This is the get accessor for the tile's colour.
     * @return The Colour associated with the tile.
     */
	public Colours GetColour();

    /**
     * This is the set accessor for the tile's colour.
     * @param colour The Colour with which the tile is to be associated.
     */
	public void SetColour(Colours colour);

    /**
     * Adds additional priority to a tube route.
     * @return An integer representing the additional priority of the tile.
     */
	public int GetAdditionalPriority();

    /**
     * When overridden, provides routines to be performed when the tile is offered and can accept a capsule.
     * @param capsule The capsule, implementing ICapsule, to be accepted into the tile's contents.
     */
	public void AcceptCapsule(ICapsule capsule);

    /**
     * Sets the connection state of a side of the tile.
     * @param side A ForgeDirection side of the tile to be updated.
     * @param connected A boolean; true if the side is connected, false if disconnected.
     */
	public void SetConnectionOnSide(ForgeDirection side, boolean connected);

    /**
     * Tests whether a given side of the tile can accept connection to another connectible
     * @param side A ForgeDirection side of the tile to test for connectibility.
     * @return True if the side is connectible, false if unconnectible.
     */
	public boolean IsConnectableOnSide(ForgeDirection side);
}
