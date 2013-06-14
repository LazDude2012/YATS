package YATS.tile;

import YATS.api.ICapsule;
import YATS.api.ITubeConnectible;
import YATS.util.Colours;
import YATS.util.InventoryCore;
import YATS.util.TubeRouting;
import YATS.util.XYZCoords;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TileTube extends TileEntity implements ITubeConnectible
{
	public int pressure = 1;
	public ICapsule contents;
	public float progressThroughTube;
	public ForgeDirection direction;
	public boolean isConnectableOnSide[] = new boolean[6];
	public boolean isConnectedOnSide[] = new boolean[6];
	public Colours colour = Colours.NONE;
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
	public boolean CanAccept(ICapsule capsule)
	{
		return (capsule.GetColour() == this.colour || capsule.GetColour() == Colours.NONE || this.colour == Colours.NONE);
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
	public void AcceptCapsule(ICapsule capsule)
	{
		this.contents = capsule;
		this.progressThroughTube = 0;
	}

	@Override
	public void SetConnectionOnSide(ForgeDirection side, boolean connected)
	{
		isConnectedOnSide[side.ordinal()] = connected;
	}

	@Override
	public boolean IsConnectableOnSide(ForgeDirection side)
	{
		return isConnectableOnSide[side.ordinal()];
	}

	@Override
	public void updateEntity()
	{
		if(contents == null) return;
		contents.SetHeading(new TubeRouting(worldObj).FindRoute(XYZCoords.FromTile(this), contents.GetHeading(), GetConnectedSides(), contents));
		progressThroughTube += (pressure / 10);
		if(progressThroughTube >= 1)
		{
			XYZCoords coords = XYZCoords.FromTile(this);
			coords.Next(contents.GetHeading());
			TileEntity tile = coords.ToTile();
			if(tile instanceof IInventory && contents.GetContents() instanceof ItemStack &&
					InventoryCore.CanAddToInventory(coords,(ItemStack)contents.GetContents()))
			{
				InventoryCore.AddToInventory((IInventory)tile,(ItemStack)contents.GetContents());
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
