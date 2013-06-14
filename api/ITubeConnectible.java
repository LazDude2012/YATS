package YATS.api;

import YATS.util.Colours;
import net.minecraftforge.common.ForgeDirection;

public interface ITubeConnectible
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
}
