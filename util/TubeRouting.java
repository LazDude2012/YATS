package YATS.util;

import YATS.api.ICapsule;
import YATS.api.ITubeConnectable;
import YATS.tile.TileTube;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import java.util.List;
import java.util.PriorityQueue;

public class TubeRouting
{
    PriorityQueue<TubeRoute> queue = new PriorityQueue<TubeRoute>();
    World world;

    public TubeRouting(World world)
    {
        this.world = world;
    }

    public void ScanBlock(LazUtils.XYZCoords coords, ForgeDirection side, ForgeDirection direction, int priority, ICapsule capsule)
    {
        TileEntity te = world.getBlockTileEntity(coords.x, coords.y, coords.z);

        if (te != null && te instanceof ITubeConnectable) {
            ITubeConnectable tube = (ITubeConnectable) te;
            if (tube.CanRoute()) {
                if (tube.CanAccept(capsule) && !(tube instanceof TileTube) && tube.AcceptsItemsOnSide(direction.getOpposite())) {
                    TubeRoute route = new TubeRoute(coords, side, direction, priority + tube.GetAdditionalPriority());
                    route.isComplete = true;
                    queue.add(route);
                    return;
                }
                queue.add(new TubeRoute(coords, side, direction, priority + tube.GetAdditionalPriority()));
                return;
            }
        } else if (te != null && te instanceof IInventory) {
            IInventory inv = (IInventory) te;
            if (capsule.GetContents() instanceof ItemStack && LazUtils.InventoryCore.CanAddToInventory(inv, (ItemStack) capsule.GetContents())) {
                TubeRoute route = new TubeRoute(coords, side.getOpposite(), direction, priority - 1);
                route.isComplete = true;
                queue.add(route);
                return;
            }
        }
    }

    public ForgeDirection FindRoute(LazUtils.XYZCoords coords, ForgeDirection initial, List<ForgeDirection> sides, ICapsule capsule)
    {
        for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
            if (sides.contains(side)) {
                LazUtils.XYZCoords newcoords = coords.Copy();
                newcoords.Next(side);
                ScanBlock(newcoords, side, side, (side == initial ? 0 : 1), capsule);
            }
        }

        while (!queue.isEmpty()) {
            TubeRoute route = queue.poll();
            if (route.isComplete) {
                return route.direction;
            }

            for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
                if (side != route.destside && ((ITubeConnectable) route.destblock.ToTile()).IsConnectedOnSide(side)) {
                    LazUtils.XYZCoords newcoords = route.destblock.Copy();
                    newcoords.Next(side);
                    ScanBlock(newcoords, route.direction, side, route.priority + 1, capsule);
                }
            }
        }
        return ForgeDirection.UNKNOWN;
    }

    class TubeRoute implements Comparable<TubeRoute>
    {
        public LazUtils.XYZCoords destblock;
        public ForgeDirection destside;
        public ForgeDirection direction;
        public int priority;
        public boolean isComplete;

        public TubeRoute(LazUtils.XYZCoords coords, ForgeDirection side, ForgeDirection dir, int weight)
        {
            this.destblock = coords;
            this.destside = side;
            this.direction = dir;
            this.priority = weight;
        }

        public int compareTo(TubeRoute route)
        {
            return this.priority - route.priority;
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TubeRoute tubeRoute = (TubeRoute) o;

            if (isComplete != tubeRoute.isComplete) return false;
            if (priority != tubeRoute.priority) return false;
            if (!destblock.equals(tubeRoute.destblock)) return false;
            if (destside != tubeRoute.destside) return false;
            if (direction != tubeRoute.direction) return false;

            return true;
        }

        @Override
        public int hashCode()
        {
            int result = destblock.hashCode();
            result = 31 * result + destside.ordinal();
            result = 31 * result + direction.ordinal();
            result = 31 * result + priority;
            result = 31 * result + (isComplete ? 1 : 0);
            return result;
        }
    }
}



