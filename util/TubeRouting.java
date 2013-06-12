package YATS.util;

import YATS.api.ITubeConnectible;
import YATS.common.Capsule;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class TubeRouting
{
	PriorityQueue<TubeRoute> queue = new PriorityQueue<TubeRoute>();
	HashSet explored = new HashSet();
	World world;
	ForgeDirection initial;
	public TubeRoute result;

	public TubeRouting(World world){ this.world = world; }

	public void ScanBlock(XYZCoords coords, ForgeDirection side, ForgeDirection direction, int priority, Capsule capsule)
	{
		if(world.blockHasTileEntity(coords.x,coords.y,coords.z))
		{
			if(!explored.contains(coords))
			{
				if (world.getBlockTileEntity(coords.x,coords.y,coords.z) instanceof ITubeConnectible)
				{
					ITubeConnectible tube = (ITubeConnectible)world.getBlockTileEntity(coords.x,coords.y,coords.z);
					if(tube.CanRoute())
					{
						explored.add(coords);
						if(tube.CanAccept(capsule))
						{
							TubeRoute route = new TubeRoute(coords, side, direction, priority+tube.GetAdditionalPriority());
							route.isComplete=true;
							queue.add(route);
							return;
						}
						queue.add(new TubeRoute(coords, side, direction, priority+tube.GetAdditionalPriority()));
					}
				}
				else if (world.getBlockTileEntity(coords.x,coords.y,coords.z) instanceof IInventory)
				{
					IInventory inv = (IInventory)world.getBlockTileEntity(coords.x,coords.y,coords.z);
					if(InventoryCore.CanAddToInventory(coords, capsule.contents))
					{
						TubeRoute route = new TubeRoute(coords,side,direction,priority);
						route.isComplete=true;
						queue.add(route);
						return;
					}
				}
			}
		}
	}
	public ForgeDirection FindRoute(XYZCoords coords, ForgeDirection initial, List<ForgeDirection> sides, Capsule capsule)
	{
		for(ForgeDirection side : ForgeDirection.VALID_DIRECTIONS)
		{
			if(sides.contains(side))
			{
				XYZCoords newcoords = coords.Copy();
				newcoords.Next(side);
				ScanBlock(newcoords,side,side,(side == initial ? 0 : 1),capsule);
			}
		}

		while(!queue.isEmpty())
		{
			TubeRoute route = queue.poll();
			if(route.isComplete)
			{
				this.result = route;
				return route.direction;
			}

			for(ForgeDirection side : ForgeDirection.VALID_DIRECTIONS)
			{
				if(side != route.destside && ((ITubeConnectible)route.destblock.ToTile()).IsConnectedOnSide(side))
				{
					XYZCoords newcoords = route.destblock.Copy();
					newcoords.Next(side);
					ScanBlock(newcoords, route.direction, side, route.priority + 1, capsule);
				}
			}
		}
		return ForgeDirection.UNKNOWN;
	}

	class TubeRoute implements Comparable<TubeRoute>
	{
		public XYZCoords destblock;
		public ForgeDirection destside;
		public ForgeDirection direction;
		public int priority;
		public boolean isComplete;

		public TubeRoute(XYZCoords coords, ForgeDirection side, ForgeDirection dir, int weight)
		{
			this.destblock=coords;
			this.destside=side;
			this.direction = dir;
			this.priority = weight;
		}
		public int compareTo(TubeRoute route)
		{
			return this.priority - route.priority;
		}
	}
}



