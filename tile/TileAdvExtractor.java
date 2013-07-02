package YATS.tile;

import YATS.api.I6WayWrenchable;
import YATS.api.ICapsule;
import YATS.api.ITubeConnectable;
import YATS.api.YATSRegistry;
import YATS.capsule.ItemCapsule;
import YATS.common.YATS;
import YATS.util.Colours;
import YATS.util.LazUtils;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class TileAdvExtractor extends TileEntity implements ITubeConnectable, IInventory, I6WayWrenchable
{
	private ItemStack[] inventory = new ItemStack[9];
	private Colours filterColour = Colours.NONE;
	private ForgeDirection facing = ForgeDirection.WEST;
	private ArrayList<ICapsule> contents = new ArrayList<ICapsule>();
	private boolean[] connections = new boolean[6];
	private boolean isContinuedSignal, deferUpdate;
	public boolean isBusy;

	//region Implementation methods; boring stuff really.

	@Override
	public int getSizeInventory() { return inventory.length; }

	@Override
	public ItemStack getStackInSlot(int i) { return inventory[i]; }

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		ItemStack retStack = inventory[i].splitStack(j);

		if(inventory[i].stackSize == 0)
			inventory = null;

		return retStack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) { return inventory[i]; }

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) { inventory[i]=itemstack; }

	@Override
	public String getInvName() { return "Advanced Extractor"; }

	@Override
	public boolean isInvNameLocalized() { return false; }

	@Override
	public int getInventoryStackLimit() { return 64; }

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) { return true; }

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) { return true; }

	@Override
	public boolean AcceptsItemsOnSide(ForgeDirection side) { return (side == facing || side == facing.getOpposite()); }

	@Override
	public boolean IsConnectedOnSide(ForgeDirection side) { return connections[side.ordinal()]; }

	@Override
	public boolean CanAccept(ICapsule capsule)
	{
		if(capsule instanceof ItemCapsule)
		{
			ItemStack capstack = (ItemStack) capsule.GetContents();
			for(ItemStack stack : inventory)
			{
				if(capstack.isItemEqual(stack)) return true;
			}
		}
		return false;
	}

	@Override
	public Boolean CanRoute() { return true; }

	@Override
	public int GetPressure() { return 5; }

	@Override
	public void SetPressure(int pressure) {}

	@Override
	public Colours GetColour() { return Colours.NONE; }

	@Override
	public void SetColour(Colours colour) {}

	@Override
	public int GetAdditionalPriority() { return 0; }

	@Override
	public void AcceptCapsule(ICapsule capsule)
	{
		capsule.resetProgress();
		contents.add(capsule);
	}

	@Override
	public void SetConnectionOnSide(ForgeDirection side, boolean connected) { connections[side.ordinal()] = connected; }

	@Override
	public boolean IsConnectableOnSide(ForgeDirection side) { return (side == facing || side == facing.getOpposite()); }

	@Override
	public void RotateTo(ForgeDirection direction) { facing = direction; }

	@Override
	public ForgeDirection GetCurrentFacing() { return facing; }

	//endregion

	public void setFilterColour(Colours colour) { filterColour = colour; }
	public Colours getFilterColour() { return filterColour; }

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
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("facing", facing.ordinal());
		nbt.setInteger("filtercolour",filterColour.ordinal());
		nbt.setBoolean("busy",isBusy);
		NBTTagList taglist = new NBTTagList();
		for(ICapsule capsule : contents)
		{
			taglist.appendTag(YATSRegistry.getCapsuleNBT(capsule));
		}
		nbt.setTag("contents",taglist);
		if(YATS.IS_DEBUG)
			LazUtils.logNormal("Transparency! Contents of tube at %s,%s,%s are: %s", xCoord, yCoord, zCoord, contents.toString());
		inventory = new ItemStack[nbt.getInteger("invsize")];
		NBTTagList tagList = nbt.getTagList("Inventory");

		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);

			byte slot = tag.getByte("Slot");

			if (slot >= 0 && slot < inventory.length) {
				inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
	}
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		facing = ForgeDirection.getOrientation(nbt.getInteger("facing"));
		filterColour = Colours.values()[nbt.getInteger("filtercolour")];
		isBusy=nbt.getBoolean("busy");
		NBTTagList list = (NBTTagList)nbt.getTag("contents");
		contents = new ArrayList<ICapsule>();
		for(int i = 0; i < list.tagCount();i++)
		{
			contents.add(YATSRegistry.handleCapsuleNBT((NBTTagCompound)list.tagAt(i)));
		}
		if(YATS.IS_DEBUG)
			LazUtils.logNormal("Literacy! Read tag list %s into tube at %s, %s, %s",list.toString(),xCoord,yCoord,zCoord);
		NBTTagList itemList = new NBTTagList();
		nbt.setInteger("invsize",this.getSizeInventory());
		for (int i = 0; i < inventory.length; i++)
		{
			ItemStack stack = inventory[i];

			if (stack != null)
			{
				NBTTagCompound tag = new NBTTagCompound();

				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}
		nbt.setTag("Inventory",itemList);
		deferUpdate = true;
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
				ExtractStacks();
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
				capsule.SetColour(filterColour);
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

	private void ExtractStacks()
	{
		TileEntity tileFrom = LazUtils.XYZCoords.FromTile(this).Next(facing).ToTile();
		if(!(tileFrom instanceof IInventory)) return;
		IInventory inv = (IInventory)tileFrom;
		for(ItemStack stack : inventory)
		{
			if(LazUtils.InventoryCore.ExtractFromInventory(inv, stack, false))
			{
				LazUtils.InventoryCore.ExtractFromInventory(inv,stack,true);
				TileEntity tileTo = LazUtils.XYZCoords.FromTile(this).Next(facing.getOpposite()).ToTile();
				ItemCapsule capsule = new ItemCapsule(stack,filterColour,facing.getOpposite());
				if(tileTo instanceof ITubeConnectable && ((ITubeConnectable)tileTo).CanAccept(capsule))
				{
					((ITubeConnectable) tileTo).AcceptCapsule(capsule);
					return;
				}
				else if(tileTo instanceof IInventory)
				{
					if(LazUtils.InventoryCore.CanAddToInventory((IInventory)tileTo,stack))
					{
						LazUtils.InventoryCore.AddToInventory((IInventory)tileTo,stack);
						return;
					}
				}
				else
				{
					LazUtils.XYZCoords coords = LazUtils.XYZCoords.FromTile(this).Next(currentfacing.getOpposite());
					EntityItem item = new EntityItem(worldObj,coords.x,coords.y,coords.z,stack);
					worldObj.spawnEntityInWorld(item);
				}
			}
		}
	}
}
