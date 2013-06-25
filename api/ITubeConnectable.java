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
public interface ITubeConnectable
{
	public boolean AcceptsItemsOnSide(ForgeDirection side);
	public boolean IsConnectedOnSide(ForgeDirection side);
	public boolean CanAccept(ICapsule capsule);
	public Boolean CanRoute();
	public int GetPressure();
	public void SetPressure(int pressure);
	public Colours GetColour();
	public void SetColour(Colours colour);
	public int GetAdditionalPriority();
	public void AcceptCapsule(ICapsule capsule);
	public void SetConnectionOnSide(ForgeDirection side, boolean connected);
	public boolean IsConnectableOnSide(ForgeDirection side);
}
