package YATS.tile;

import YATS.api.I6WayWrenchable;
import YATS.api.ICapsule;
import YATS.api.ITubeConnectable;
import YATS.capsule.ItemCapsule;
import YATS.common.YATS;
import YATS.util.Colours;
import YATS.util.LazUtils;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

import java.util.ArrayList;

public class TileExtractor extends TileEntity implements I6WayWrenchable,ITubeConnectable
{
	private ForgeDirection currentfacing = ForgeDirection.WEST;
	private boolean isContinuedSignal;
	public boolean isBusy;
	private ArrayList<ICapsule> contents;
	private boolean[] connections = new boolean[6];
	private boolean deferUpdate = false;
	public void updateEntity()
	{
		if(deferUpdate) worldObj.markBlockForUpdate(xCoord,yCoord,zCoord);
		if(worldObj.isBlockIndirectlyGettingPowered(xCoord,yCoord,zCoord))
		{
			if(!isContinuedSignal)
			{
				isBusy=true;
				ExtractItem();
				isContinuedSignal=true;
			}
		}
		else
		{
			isContinuedSignal=false;
			isBusy=false;
		}
	}

	private void ExtractItem()
	{
		TileEntity tile = LazUtils.XYZCoords.FromTile(this).Next(currentfacing).ToTile();
		if(tile instanceof IInventory)
		{
			if(LazUtils.InventoryCore.ExtractOneFromInventory((IInventory) tile, false) != null)
			{
				ItemStack stack = LazUtils.InventoryCore.ExtractOneFromInventory((IInventory) tile, true);
				if(YATS.IS_DEBUG)
				LazUtils.logNormal("Heartbreak! Extracting item at " + xCoord + ","+yCoord+","+zCoord+ ": ("+stack.toString()+")");
				TileEntity tile2 = LazUtils.XYZCoords.FromTile(this).Next(currentfacing.getOpposite()).ToTile();
				if(tile2 instanceof ITubeConnectable && ((ITubeConnectable) tile2).IsConnectedOnSide(currentfacing))
					((ITubeConnectable) tile2).AcceptCapsule(new ItemCapsule(stack,Colours.ORANGE,currentfacing.getOpposite()));
				else if (tile2 instanceof IInventory && LazUtils.InventoryCore.CanAddToInventory((IInventory) tile2, stack))
					LazUtils.InventoryCore.AddToInventory((IInventory) tile2, stack);
				else
				{
					LazUtils.XYZCoords coords = LazUtils.XYZCoords.FromTile(this).Next(currentfacing.getOpposite());
					EntityItem item = new EntityItem(worldObj,coords.x,coords.y,coords.z,stack);
					worldObj.spawnEntityInWorld(item);
				}
			}
		}
	}

	@Override
	public void RotateTo(ForgeDirection direction)
	{
		currentfacing=direction;
	}

	@Override
	public ForgeDirection GetCurrentFacing()
	{
		return currentfacing;
	}

	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("facing",currentfacing.ordinal());
		nbt.setBoolean("busy",isBusy);
	}

	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		currentfacing = ForgeDirection.getOrientation(nbt.getInteger("facing"));
		isBusy=nbt.getBoolean("busy");
		deferUpdate = true;
	}

	@Override
	public boolean AcceptsItemsOnSide(ForgeDirection side)
	{
		return (side == currentfacing.getOpposite());
	}

	@Override
	public boolean IsConnectedOnSide(ForgeDirection side)
	{
		return connections[side.ordinal()];
	}

	@Override
	public boolean CanAccept(ICapsule capsule)
	{
		return true;
	}

	@Override
	public Boolean CanRoute()
	{
		return !isContinuedSignal;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public int GetPressure()
	{
		return 1;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void SetPressure(int pressure)
	{

	}

	@Override
	public Colours GetColour()
	{
		return Colours.NONE;
	}

	@Override
	public void SetColour(Colours colour)
	{

	}

	@Override
	public int GetAdditionalPriority()
	{
		return 2;
	}

	@Override
	public void AcceptCapsule(ICapsule capsule)
	{
		capsule.resetProgress();
		contents.add(capsule);
	}

	@Override
	public void SetConnectionOnSide(ForgeDirection side, boolean connected)
	{
		connections[side.ordinal()] = connected;
	}

	@Override
	public boolean IsConnectableOnSide(ForgeDirection side)
	{
		return (side == currentfacing || side.getOpposite() == currentfacing);
	}
}
