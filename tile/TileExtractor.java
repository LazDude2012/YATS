package YATS.tile;

import YATS.api.I6WayWrenchable;
import YATS.api.ICapsule;
import YATS.api.ITubeConnectable;
import YATS.api.YATSRegistry;
import YATS.block.BlockTube;
import YATS.capsule.ItemCapsule;
import YATS.common.YATS;
import YATS.util.Colours;
import YATS.util.LazUtils;
import YATS.util.TubeRouting;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

import java.util.ArrayList;

public class TileExtractor extends TileEntity implements I6WayWrenchable,ITubeConnectable
{
	private ForgeDirection currentfacing = ForgeDirection.WEST;
	private boolean isContinuedSignal;
	public boolean isBusy;
	private ArrayList<ICapsule> contents = new ArrayList<ICapsule>();
	private boolean[] connections = new boolean[6];
	private boolean deferUpdate = false;

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		this.writeToNBT(nbttagcompound);
		Packet132TileEntityData packet = new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 3, nbttagcompound);
		packet.isChunkDataPacket=true;
		return packet;
	}

	@Override
	public void onDataPacket(INetworkManager manager, Packet132TileEntityData packet)
	{
		readFromNBT(packet.customParam1);
		worldObj.markBlockForRenderUpdate(xCoord,yCoord,zCoord);
	}

	@Override
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
		if(!isContinuedSignal)
		{
			ArrayList<ICapsule> capsulesToRemove = new ArrayList<ICapsule>();
			for (ICapsule capsule : contents)
			{
				if(YATS.IS_DEBUG)
					LazUtils.logNormal("Discovery! We have a capsule, updating. %s, %s, %s", xCoord,yCoord,zCoord);
				capsule.addProgress((float) 5 / 100);
				if(YATS.IS_DEBUG)
					LazUtils.logNormal("Ambition! Capsule moved forward successfully, capsule progress %s, pressure %s. %s, %s, %s",capsule.getProgress(),5,xCoord,yCoord,zCoord);
				if(capsule.getProgress() >= 1)
				{
					LazUtils.XYZCoords coords = LazUtils.XYZCoords.FromTile(this);
					coords.Next(capsule.GetHeading());
					TileEntity tile = coords.ToTile();
					if(tile instanceof IInventory && capsule.GetContents() instanceof ItemStack &&
							LazUtils.InventoryCore.CanAddToInventory(coords, (ItemStack) capsule.GetContents()))
					{
						capsulesToRemove.add(capsule);
						LazUtils.InventoryCore.AddToInventory((IInventory) tile, (ItemStack) capsule.GetContents());
					}
					else if(tile instanceof ITubeConnectable && ((ITubeConnectable)tile).CanAccept(capsule))
					{
						capsulesToRemove.add(capsule);
						((ITubeConnectable)tile).AcceptCapsule(capsule);
					}
				}
			}
			for(ICapsule capsule : capsulesToRemove)
			{
				contents.remove(capsule);
			}
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
                    item.setVelocity(0,0,0);
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
		NBTTagList taglist = new NBTTagList();
		for(ICapsule capsule : contents)
		{
			taglist.appendTag(YATSRegistry.getCapsuleNBT(capsule));
		}
		nbt.setTag("contents",taglist);
		if(YATS.IS_DEBUG)
			LazUtils.logNormal("Transparency! Contents of tube at %s,%s,%s are: %s",xCoord,yCoord,zCoord,contents.toString());
	}

	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		currentfacing = ForgeDirection.values()[nbt.getInteger("facing")];
		isBusy=nbt.getBoolean("busy");
		NBTTagList list = (NBTTagList)nbt.getTag("contents");
		contents = new ArrayList<ICapsule>();
		for(int i = 0; i < list.tagCount();i++)
		{
			contents.add(YATSRegistry.handleCapsuleNBT((NBTTagCompound)list.tagAt(i)));
		}
		if(YATS.IS_DEBUG)
			LazUtils.logNormal("Literacy! Read tag list %s into tube at %s, %s, %s",list.toString(),xCoord,yCoord,zCoord);
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
		return 1;
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
