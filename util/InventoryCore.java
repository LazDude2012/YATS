package YATS.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class InventoryCore
{
	public static boolean AddToInventory(IInventory inv, ItemStack stack)
	{
		return AddToInventory(inv, stack, 0, inv.getSizeInventory(),true);
	}
	public static boolean AddToInventory(IInventory inv, ItemStack stack, int slotStart, int numSlots, boolean commit)
	{
		for (int n = slotStart; n < slotStart + numSlots; n++) {
			ItemStack invStack = inv.getStackInSlot(n);
			if (invStack == null) {
				if (!commit) return true;

			}
			else if ((stack.isItemEqual(invStack)) &&
					(ItemStack.areItemStackTagsEqual(stack, invStack)))
			{
				int dfs = Math.min(invStack.getMaxStackSize(), inv.getInventoryStackLimit());

				dfs -= invStack.stackSize;
				if (dfs > 0) {
					int si = Math.min(dfs, stack.stackSize);
					if (!commit) return true;

					invStack.stackSize += si;
					inv.setInventorySlotContents(n, invStack);
					stack.stackSize -= si;
					if (stack.stackSize == 0)
						return true;
				}
			}
		}
		if (!commit) return false;
		for (int n = slotStart; n < slotStart + numSlots; n++) {
			ItemStack invStack = inv.getStackInSlot(n);
			if (invStack == null) {
				if (inv.getInventoryStackLimit() >= stack.stackSize)
				{
					inv.setInventorySlotContents(n, stack);
					return true;
				}
				inv.setInventorySlotContents(n, stack.splitStack(inv.getInventoryStackLimit()));
			}
		}

		return false;
	}
	public static boolean CanAddToInventory(IInventory inv, ItemStack stack)
	{
		return AddToInventory(inv, stack,0,inv.getSizeInventory(),false);
	}
	public static boolean CanAddToInventory(XYZCoords coords, ItemStack stack)
	{
		TileEntity tile = coords.ToTile();
		if(tile instanceof IInventory)
		{
			return CanAddToInventory((IInventory)tile,stack);
		}
		return false;
	}
}
