package YATS.util;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;

public class XYZCoords
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

	public void Next(ForgeDirection direction)
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
	}
}
