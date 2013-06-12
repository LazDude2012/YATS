package YATS.tile;

import YATS.api.ITubeConnectible;
import YATS.common.Capsule;
import YATS.util.Colours;
import YATS.util.InventoryCore;
import YATS.util.TubeRouting;
import YATS.util.XYZCoords;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TileTube extends TileEntity implements ITubeConnectible
{
	public int pressure = 1;
	public Capsule contents;
	public int progressThroughTube;
	public ForgeDirection direction;
	public boolean isConnectableOnSide[] = new boolean[6];
	public boolean isConnectedOnSide[] = new boolean[6];
	public Colours colour;
	public double capsuleX,capsuleY,capsuleZ;

	@Override
	public boolean AcceptsItemsOnSide(ForgeDirection side)
	{
		return isConnectedOnSide[side.ordinal()];
	}

	@Override
	public boolean IsConnectedOnSide(ForgeDirection side)
	{
		return isConnectedOnSide[side.ordinal()];
	}

	@Override
	public boolean CanAccept(Capsule capsule)
	{
		return (capsule.colourTag == this.colour || capsule.colourTag == Colours.NONE || this.colour == Colours.NONE);
	}

	@Override
	public Boolean CanRoute()
	{
		return true;
	}

	@Override
	public int GetPressure()
	{
		return this.pressure;
	}

	@Override
	public void SetPressure(int pressure)
	{
		this.pressure = pressure;
	}

	@Override
	public Colours GetColour()
	{
		return this.colour;
	}

	@Override
	public void SetColour(Colours colour)
	{
		this.colour = colour;
	}

	@Override
	public int GetAdditionalPriority()
	{
		return 0;
	}

	@Override
	public void AcceptCapsule(Capsule capsule)
	{
		this.contents = capsule;
		this.progressThroughTube = 0;
	}

	@Override
	public void updateEntity()
	{
		if(contents == null) return;
		contents.heading = new TubeRouting(worldObj).FindRoute(XYZCoords.FromTile(this), contents.heading, GetConnectedSides(),contents);
		progressThroughTube += (pressure / 10);
		if(progressThroughTube >= 1)
		{
			XYZCoords coords = XYZCoords.FromTile(this);
			coords.Next(contents.heading);
			TileEntity tile = coords.ToTile();
			if(tile instanceof IInventory && InventoryCore.CanAddToInventory(coords,contents.contents))
			{
				InventoryCore.AddToInventory((IInventory)tile,contents.contents);
				contents = null;
				progressThroughTube=0;
			}
			else if(tile instanceof ITubeConnectible && ((ITubeConnectible)tile).CanAccept(contents))
			{
				((ITubeConnectible)tile).AcceptCapsule(contents);
				contents = null;
				progressThroughTube=0;
			}
		}
	}

	public ArrayList<ForgeDirection> GetConnectedSides()
	{
		ArrayList<ForgeDirection> result = new ArrayList<ForgeDirection>();
		for(int i = 0;i < 6;i++)
		{
			if(isConnectedOnSide[i]) result.add(ForgeDirection.getOrientation(i));
		}
		return result;
	}

}
