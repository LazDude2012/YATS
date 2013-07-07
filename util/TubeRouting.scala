package YATS.util

import java.util.PriorityQueue
import net.minecraft.world.World
import net.minecraftforge.common.ForgeDirection
import YATS.api.{ITubeConnectable, ICapsule}
import YATS.util.LazUtils.XYZCoords
import net.minecraft.inventory.IInventory
import YATS.capsule.ItemCapsule
import net.minecraft.item.ItemStack

class TubeRouting (var world : World)
{
  var processqueue = new PriorityQueue[TubeRoute]()
  def ScanBlock (block : XYZCoords, initialdir : ForgeDirection, heading : ForgeDirection,
                 distance : Int, capsule : ICapsule)
  {
    if(!(world.blockHasTileEntity(block.x,block.y,block.z))) null
    else
    {
      val tile = world.getBlockTileEntity(block.x,block.y, block.z)
      if (tile isInstanceOf ITubeConnectable)
        processqueue.add(new TubeRoute(block, heading, initialdir, distance + (tile asInstanceOf ITubeConnectable).GetAdditionalPriority))
      else if (tile isInstanceOf IInventory && capsule isInstanceOf ItemCapsule && LazUtils.InventoryCore.CanAddToInventory(block, (capsule.GetContents()) asInstanceOf ItemStack))
      {
        val route = new TubeRoute(block,heading,initialdir,distance)
        route.completed = true
        processqueue add route
      }
    }
  }
  def FindRoute (block : XYZCoords, initial : ForgeDirection, sides : java.util.List[ForgeDirection],
                 capsule : ICapsule) : ForgeDirection =
  {
    for(side <- sides)
    {
      val newblock = block.Next(side)
      ScanBlock(newblock,initial,initial,(if(side == initial) 0 else 1),capsule)
    }

    while(processqueue.size() > 0)
    {
      val route = processqueue.poll()
      if(route.completed) return route.initialdir

      for(side <- ForgeDirection.VALID_DIRECTIONS)
      {
        val newCoords = route.destination.next(side)
        ScanBlock(newCoords,initial,side,route.priority+1,capsule)
      }
    }
    ForgeDirection.UNKNOWN
  }
}

