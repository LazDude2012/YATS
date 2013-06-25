package YATS.util;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.entity.EntityLiving;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;

import java.util.logging.Level;

public class LazUtils
{
	/**
	 * This is a helper method to return the approximate ForgeDirection an entity is facing.
	 * @param entity The EntityLiving to get a ForgeDirection from.
	 * @param inverted Whether to get the direction the entity is facing (useful for in-world GUIs) or the opposite direction. (useful for block placement)
	 * @return The ForgeDirection the entity is facing (or the opposite if needed).
	 */
	public static ForgeDirection GetFDFromEntity(EntityLiving entity, Boolean inverted)
	{
		if(inverted)
		{
			if(entity.rotationPitch > 60) return ForgeDirection.UP;
			if(entity.rotationPitch < -60) return ForgeDirection.DOWN;
			int facing = MathHelper.floor_double((double) ((entity.rotationYaw * 4F) / 360F) + 0.5D) & 3;
			switch(facing)
			{
				case 0:
					return ForgeDirection.NORTH;
				case 1:
					return ForgeDirection.EAST;
				case 2:
					return ForgeDirection.SOUTH;
				case 3:
					return ForgeDirection.WEST;
			}
		}
		else
		{
			if(entity.rotationPitch > 60) return ForgeDirection.DOWN;
			if(entity.rotationPitch < -60) return ForgeDirection.UP;
			int facing = MathHelper.floor_double((double) ((entity.rotationYaw * 4F) / 360F) + 0.5D) & 3;
			switch(facing)
			{
				case 0:
					return ForgeDirection.SOUTH;
				case 1:
					return ForgeDirection.WEST;
				case 2:
					return ForgeDirection.NORTH;
				case 3:
					return ForgeDirection.EAST;
			}
		}
		throw new RuntimeException();
	}

	public static class XYZCoords
	{
		public int x,y,z;
		public IBlockAccess world;

		public XYZCoords(IBlockAccess world, int x, int y, int z)
		{
			this.world = world;
			this.x = x; this.y = y; this.z = z;
		}

		public static XYZCoords FromTile(TileEntity tile)
		{
			return new XYZCoords(tile.worldObj,tile.xCoord, tile.yCoord, tile.zCoord);
		}
		public XYZCoords Copy()
		{
			return new XYZCoords(this.world,this.x,this.y,this.z);
		}

		public TileEntity ToTile()
		{
			return world.getBlockTileEntity(x,y,z);
		}

		@Override
		public boolean equals(Object o)
		{
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			XYZCoords xyzCoords = (XYZCoords) o;

			if (x != xyzCoords.x) return false;
			if (y != xyzCoords.y) return false;
			if (z != xyzCoords.z) return false;
			if (!world.equals(xyzCoords.world)) return false;

			return true;
		}

		@Override
		public int hashCode()
		{
			int result = x;
			result = 31 * result + y;
			result = 31 * result + z;
			result = 31 * result + world.hashCode();
			return result;
		}

		public XYZCoords Next(ForgeDirection direction)
		{
			switch(direction)
			{
				case NORTH:
					z--;
					break;
				case SOUTH:
					z++;
					break;
				case EAST:
					x++;
					break;
				case WEST:
					x--;
					break;
				case UP:
					y++;
					break;
				case DOWN:
					y--;
					break;
			}
			return this;
		}
	}

	public static void logNormal(String string, Object... format)
	{
		FMLLog.log("YATS", Level.INFO,string,format);
	}

	public static void LogException(String string, Throwable ex, Object... format)
	{
		FMLLog.log("YATS",Level.SEVERE,string + " || "+ ex.toString(),format);
		ex.printStackTrace();
	}

	public static class InventoryCore
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
		public static boolean ExtractFromInventory(IInventory inv, ItemStack stack, boolean commit)
		{
			for(int i = 0;i <= inv.getSizeInventory();i++)
			{
				ItemStack invstack = inv.getStackInSlot(i);
				if(invstack.getItem() == stack.getItem())
				{
					if(invstack.stackSize >= stack.stackSize)
					{
						if(commit)
						inv.decrStackSize(i,stack.stackSize);
						return true;
					}
				}
			}
			return false;
		}
		public static ItemStack ExtractOneFromInventory(IInventory inv, boolean commit)
		{
			for(int i = 0; i <= inv.getSizeInventory();i++)
			{
				ItemStack stack = inv.getStackInSlot(i);
				if(stack != null)
				{
					ItemStack retstack = new ItemStack(stack.getItem(),1);
					if(commit) retstack = inv.decrStackSize(i,1);
					return retstack;
				}
			}
			return null;
		}
	}
}
