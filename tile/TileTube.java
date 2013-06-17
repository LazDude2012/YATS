package YATS.tile;

import YATS.api.ICapsule;
import YATS.api.ITubeConnectible;
import YATS.block.BlockTube;
import YATS.util.*;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

import java.util.ArrayList;

public class TileTube extends TileEntity implements ITubeConnectible
{
	public int pressure = 1;
	public ArrayList<ICapsule> contents;
	public ForgeDirection direction;
	public boolean isConnectableOnSide[] = {true,true,true,true,true,true};
	public boolean isConnectedOnSide[] = new boolean[6];
	public Colours colour = Colours.NONE;
	public double capsuleX,capsuleY,capsuleZ;

	public TileTube()
	{
		super();
		contents = new ArrayList<ICapsule>();
	}

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
		capsule.resetProgress();
		this.contents.add(capsule);
		FMLLog.info("Tolerance! Accepting capsule at "+xCoord+","+yCoord+","+zCoord+"! Contents: "+contents.toString());
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
	public boolean canUpdate() { return true; }

	@Override
	public void updateEntity()
	{
		BlockTube.CheckTubeConnections(worldObj,xCoord,yCoord,zCoord);
		for (ICapsule capsule : contents)
		{
			FMLLog.info("Discovery! We have a capsule, updating. %s, %s, %s", xCoord,yCoord,zCoord);
			capsule.SetHeading(new TubeRouting(worldObj).FindRoute(LazUtils.XYZCoords.FromTile(this), capsule.GetHeading(), GetConnectedSides(), capsule));
			FMLLog.info("Guidance! Capsule routing updated: %s, %s, %s",xCoord,yCoord,zCoord);
			capsule.addProgress((float) pressure / 100);
			FMLLog.info("Ambition! Capsule moved forward successfully, capsule progress %s, pressure %s. %s, %s, %s",capsule.getProgress(),pressure,xCoord,yCoord,zCoord);
			if(capsule.getProgress() >= 1)
			{
				LazUtils.XYZCoords coords = LazUtils.XYZCoords.FromTile(this);
				coords.Next(capsule.GetHeading());
				TileEntity tile = coords.ToTile();
				if(tile instanceof IInventory && capsule.GetContents() instanceof ItemStack &&
						LazUtils.InventoryCore.CanAddToInventory(coords, (ItemStack) capsule.GetContents()))
				{
					contents.remove(capsule);
					LazUtils.InventoryCore.AddToInventory((IInventory) tile, (ItemStack) capsule.GetContents());
				}
				else if(tile instanceof ITubeConnectible && ((ITubeConnectible)tile).CanAccept(capsule))
				{
					contents.remove(capsule);
					((ITubeConnectible)tile).AcceptCapsule(capsule);
				}
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
