package YATS.common.gui;

import YATS.tile.TileRoutingMarshaller;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerRoutingMarshaller extends Container
{
    protected TileRoutingMarshaller tile;

    public ContainerRoutingMarshaller(InventoryPlayer playerInv, TileRoutingMarshaller tile)
    {
        this.tile = tile;
        BindTileSlots();
        BindPlayerSlots(playerInv);
    }

    private void BindPlayerSlots(InventoryPlayer playerInv)
    {
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                //START OF INVENTORY: 49 x, 163 y; SPACING 18
                addSlotToContainer(new Slot(playerInv, (9*i)+j+9, 49 + 18*j, 163+18*i));
            }
        }
        for(int i = 0;i<9;i++)
        {
            addSlotToContainer(new Slot(playerInv,i, 49+18*i, 221));
        }
    }

    private void BindTileSlots()
    {
        //STARTING POSITION: 8,33. SPACE BETWEEN ROWS = 7px.
        for(int i = 0; i < 40; i++)
            addSlotToContainer(new Slot(tile,i,(i % 8) * 18 + 8, (i/8)*23+33));
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotnumber)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)inventorySlots.get(slotnumber);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (slotnumber < 40)
            {
                if (!mergeItemStack(itemstack1, 41, 76, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (slotnumber >= 40 && slotnumber < 68)
            {
                if (!mergeItemStack(itemstack1, 68, 76, false))
                {
                    return null;
                }
            }
            else if (slotnumber >= 68 && slotnumber < 76 && !mergeItemStack(itemstack1, 40, 68, false))
            {
                return null;
            }

            if (itemstack1.stackSize== 0)
            {
                slot.putStack(null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(player, itemstack);
        }

        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return true;
    }
}
