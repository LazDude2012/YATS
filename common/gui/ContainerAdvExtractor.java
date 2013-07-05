package YATS.common.gui;

import YATS.tile.TileAdvExtractor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAdvExtractor extends Container
{
	protected TileAdvExtractor tile;

	public ContainerAdvExtractor(InventoryPlayer playerInv, TileAdvExtractor tile)
	{
		this.tile=tile;
		BindTileSlots();
		BindPlayerSlots(playerInv);
	}

	private void BindTileSlots()
	{
		for(int i = 0; i < 9; i++)
		{
			Slot tempslot = new Slot(tile,i,(8+i*18),57);
			addSlotToContainer(tempslot);
		}
	}

	private void BindPlayerSlots(InventoryPlayer playerInv)
	{
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
			//START OF INVENTORY: 8 x, 99 y; SPACING 18
				addSlotToContainer(new Slot(playerInv, (9*i)+j+9, 8 + 18*j, 99+18*i));
			}
		}
		for(int i = 0;i<9;i++)
		{
			addSlotToContainer(new Slot(playerInv,i, 8+18*i, 157));
		}
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

            if (slotnumber < 9)
            {
                if (!mergeItemStack(itemstack1, 10, 45, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (slotnumber >= 9 && slotnumber < 36)
            {
                if (!mergeItemStack(itemstack1, 36, 45, false))
                {
                    return null;
                }
            }
            else if (slotnumber >= 36 && slotnumber < 45 && !mergeItemStack(itemstack1, 10, 36, false))
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
