package YATS.tile;

import YATS.api.I6WayWrenchable;
import YATS.api.ICapsule;
import YATS.api.ITubeConnectable;
import YATS.api.YATSRegistry;
import YATS.capsule.ItemCapsule;
import YATS.common.YATS;
import YATS.util.Colours;
import YATS.util.LazUtils;
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
import static YATS.util.Colours.NONE;

import java.util.ArrayList;

public class TileRoutingMarshaller extends TileEntity implements IInventory, I6WayWrenchable, ITubeConnectable
{
    public ItemStack[] inventory = new ItemStack[40];
    public Colours[] rowColours = {NONE,NONE,NONE,NONE,NONE};
    public int activeRow = 0;
    private ArrayList<ICapsule> contents = new ArrayList<ICapsule>();
    private ArrayList<ICapsule> pending = new ArrayList<ICapsule>();
    private ForgeDirection currentfacing = ForgeDirection.UNKNOWN;
    private boolean[] connections = new boolean[6];
    public boolean isBusy;
    private boolean isContinuedSignal,deferUpdate;

    @Override
    public void RotateTo(ForgeDirection direction) { currentfacing = direction; }

    @Override
    public ForgeDirection GetCurrentFacing() { return currentfacing; }

    @Override
    public int getSizeInventory() { return 40; }

    @Override
    public ItemStack getStackInSlot(int i) { return inventory[i]; }

    @Override
    public ItemStack decrStackSize(int i, int j)
    {
        ItemStack retStack = inventory[i].splitStack(j);

        if(inventory[i].stackSize == 0)
            inventory[i] = null;

        return retStack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) { return inventory[i]; }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) { inventory[i] = itemstack; }

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
    public boolean isItemValidForSlot(int i, ItemStack itemstack){ return true; }

    @Override
    public boolean AcceptsItemsOnSide(ForgeDirection side) { return (side == currentfacing); }

    @Override
    public boolean IsConnectedOnSide(ForgeDirection side) { return connections[side.ordinal()]; }

    @Override
    public boolean CanAccept(ICapsule capsule) { return true; }

    @Override
    public boolean CanRoute() { return true; }

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
    public void AcceptCapsule(ICapsule capsule) { pending.add(capsule); }

    @Override
    public void SetConnectionOnSide(ForgeDirection side, boolean connected) { connections[side.ordinal()] = connected; }

    @Override
    public boolean IsConnectableOnSide(ForgeDirection side) { return (side == currentfacing || side == currentfacing.getOpposite()); }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        Packet132TileEntityData pkt = new Packet132TileEntityData(xCoord,yCoord,zCoord,3,nbt);
        pkt.isChunkDataPacket = true;
        return pkt;
    }

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
    {
        readFromNBT(pkt.data);
        worldObj.markBlockForRenderUpdate(xCoord,yCoord,zCoord);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("facing", currentfacing.ordinal());
        for(int i = 0; i < 5; i++)
        {
            nbt.setInteger("colour"+i,rowColours[i].ordinal());
        }
        nbt.setBoolean("busy",isBusy);
        NBTTagList taglist = new NBTTagList();
        for(ICapsule capsule : contents)
        {
            taglist.appendTag(YATSRegistry.getCapsuleNBT(capsule));
        }
        nbt.setTag("contents",taglist);
        if(YATS.IS_DEBUG)
            LazUtils.logNormal("Transparency! Contents of tube at %s,%s,%s are: %s", xCoord, yCoord, zCoord, contents.toString());

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
    }
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        currentfacing = ForgeDirection.values()[nbt.getInteger("facing")];
        for(int i = 0; i < 5; i++)
        {
            rowColours[i] = Colours.values()[nbt.getInteger("colour"+i)];
        }
        isBusy=nbt.getBoolean("busy");
        NBTTagList list = (NBTTagList)nbt.getTag("contents");
        contents = new ArrayList<ICapsule>();
        for(int i = 0; i < list.tagCount();i++)
        {
            contents.add(YATSRegistry.handleCapsuleNBT((NBTTagCompound)list.tagAt(i)));
        }
        if(YATS.IS_DEBUG)
            LazUtils.logNormal("Literacy! Read tag list %s into tube at %s, %s, %s",list.toString(),xCoord,yCoord,zCoord);
        inventory = new ItemStack[nbt.getInteger("invsize")];
        NBTTagList tagList = nbt.getTagList("Inventory");

        for (int i = 0; i < tagList.tagCount(); i++)
        {
            NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);

            byte slot = tag.getByte("Slot");

            if (slot >= 0 && slot < inventory.length)
                inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
        }
        deferUpdate = true;
    }
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
            for(ICapsule capsule : pending)
                contents.add(capsule);
            pending.clear();
            for (ICapsule capsule : contents)
            {
                if(YATS.IS_DEBUG)
                    LazUtils.logNormal("Discovery! We have a capsule, updating. %s, %s, %s", xCoord,yCoord,zCoord);
                capsule.addProgress((float) 5 / 100);
                if(capsule instanceof ItemCapsule)
                {
                    for(int i = 0; i < 40; i++)
                    {
                        ItemStack invstack = inventory[i];
                        ItemStack capstack = (ItemStack) capsule.GetContents();
                        if(invstack.isItemEqual(capstack))
                            capsule.SetColour(rowColours[i/8]);
                    }
                }
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
        TileEntity tileFrom = LazUtils.XYZCoords.FromTile(this).Next(currentfacing).ToTile();
        if(!(tileFrom instanceof IInventory)) return;
        IInventory inv = (IInventory)tileFrom;
        for(int i = 0+activeRow * 8; i < activeRow*8+8; i++)
        {
            ItemStack stack = inventory[i];
            if(stack == null) continue;
            if(LazUtils.InventoryCore.ExtractFromInventory(inv, stack, false))
            {
                LazUtils.InventoryCore.ExtractFromInventory(inv,stack,true);
                TileEntity tileTo = LazUtils.XYZCoords.FromTile(this).Next(currentfacing.getOpposite()).ToTile();
                ItemCapsule capsule = new ItemCapsule(stack,rowColours[i/8],currentfacing.getOpposite());
                if(tileTo instanceof ITubeConnectable && ((ITubeConnectable)tileTo).CanAccept(capsule))
                {
                    ((ITubeConnectable) tileTo).AcceptCapsule(capsule);
                    continue;
                }
                else if(tileTo instanceof IInventory)
                {
                    if(LazUtils.InventoryCore.CanAddToInventory((IInventory)tileTo,stack))
                    {
                        LazUtils.InventoryCore.AddToInventory((IInventory)tileTo,stack);
                        continue;
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
        activeRow = (activeRow == 4 ? 0 : activeRow + 1); 
    }
}
