package YATS.tile

import YATS.api.I6WayWrenchable
import YATS.api.ICapsule
import YATS.api.ITubeConnectable
import YATS.api.YATSRegistry
import YATS.block.BlockTube
import YATS.util._
import YATS.capsule.ItemCapsule
import YATS.common.YATS
import net.minecraft.entity.item.EntityItem
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagList
import net.minecraft.network.INetworkManager
import net.minecraft.network.packet.Packet
import net.minecraft.network.packet.Packet132TileEntityData
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.common.ForgeDirection
import java.util.ArrayList
import scala.language.implicitConversions;

class TileExtractor extends TileEntity with I6WayWrenchable with ITubeConnectable
{
  var isBusy: Boolean = false
  override def getDescriptionPacket: Packet =
  {
    val nbttagcompound: NBTTagCompound = new NBTTagCompound
    this.writeToNBT(nbttagcompound)
    val packet: Packet132TileEntityData = new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 3, nbttagcompound)
    packet.isChunkDataPacket = true
    return packet
  }

  override def onDataPacket(manager: INetworkManager, packet: Packet132TileEntityData)
  {
    readFromNBT(packet.data)
    worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord)
  }

  override def updateEntity
  {
    if (deferUpdate) worldObj.markBlockForUpdate(xCoord, yCoord, zCoord)
    if (worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
    {
      if (!isContinuedSignal)
      {
        isBusy = true
        ExtractItem
        isContinuedSignal = true
      }
    }
    else
    {
      isContinuedSignal = false
      isBusy = false
    }
    if (!isContinuedSignal)
    {
      val capsulesToRemove: ArrayList[ICapsule] = new ArrayList[ICapsule]
      import scala.collection.JavaConversions._
      for (capsule <- pending) contents.add(capsule)
      pending.clear
      for (capsule <- contents)
      {
        if (YATS.IS_DEBUG) LazUtils.logNormal("Discovery! We have a capsule, updating. %s, %s, %s", xCoord : Integer, yCoord : Integer, zCoord : Integer)
        capsule.addProgress(5.asInstanceOf[Float] / 100)
        if (YATS.IS_DEBUG) LazUtils.logNormal("Ambition! Capsule moved forward successfully, capsule progress %s, pressure %s. %s, %s, %s", capsule.getProgress.asInstanceOf[java.lang.Float], 5 : Integer, xCoord : Integer, yCoord : Integer, zCoord : Integer)
        if (capsule.getProgress >= 1)
        {
          val coords: LazUtils.XYZCoords = LazUtils.XYZCoords.FromTile(this)
          coords.Next(capsule.GetHeading)
          val tile: TileEntity = coords.ToTile
          tile match
          {
            case ii: IInventory =>
              if (capsule.GetContents.isInstanceOf[ItemStack] && LazUtils.InventoryCore.CanAddToInventory(ii, capsule.GetContents.asInstanceOf[ItemStack]))
              {
                capsulesToRemove.add(capsule)
                LazUtils.InventoryCore.AddToInventory(ii, capsule.GetContents.asInstanceOf[ItemStack])
              }
            case itc: ITubeConnectable =>
              if (itc.CanAccept(capsule))
              {
                capsulesToRemove.add(capsule)
                itc.AcceptCapsule(capsule)
              }
            case _ =>
          }
        }
      }
      import scala.collection.JavaConversions._
      for (capsule <- capsulesToRemove)
      {
        contents.remove(capsule)
      }
    }
  }

  private def ExtractItem
  {
    val tile: TileEntity = LazUtils.XYZCoords.FromTile(this).Next(currentfacing).ToTile
    if (tile.isInstanceOf[IInventory])
    {
      if (LazUtils.InventoryCore.ExtractOneFromInventory(tile.asInstanceOf[IInventory], false) != null)
      {
        val stack = LazUtils.InventoryCore.ExtractOneFromInventory(tile.asInstanceOf[IInventory], true)
        if (YATS.IS_DEBUG) LazUtils.logNormal("Heartbreak! Extracting item at " + xCoord + "," + yCoord + "," + zCoord + ": (" + stack.toString + ")")
        val tile2 = LazUtils.XYZCoords.FromTile(this).Next(currentfacing.getOpposite).ToTile
        if (tile2.isInstanceOf[ITubeConnectable] && (tile2.asInstanceOf[ITubeConnectable]).IsConnectedOnSide(currentfacing)) (tile2.asInstanceOf[ITubeConnectable]).AcceptCapsule(new ItemCapsule(stack, Colours.NONE, currentfacing.getOpposite))
        else if (tile2.isInstanceOf[IInventory] && LazUtils.InventoryCore.CanAddToInventory(tile2.asInstanceOf[IInventory], stack)) LazUtils.InventoryCore.AddToInventory(tile2.asInstanceOf[IInventory], stack)
        else
        {
          val coords: LazUtils.XYZCoords = LazUtils.XYZCoords.FromTile(this).Next(currentfacing.getOpposite)
          val item: EntityItem = new EntityItem(worldObj, coords.x + 0.5, coords.y + 0.5, coords.z + 0.5, stack)
          item.setVelocity(0, 0, 0)
          worldObj.spawnEntityInWorld(item)
        }
      }
    }
  }

  def RotateTo(direction: ForgeDirection)
  {
    currentfacing = direction
  }

  def GetCurrentFacing: ForgeDirection =
  {
    return currentfacing
  }

  override def writeToNBT(nbt: NBTTagCompound)
  {
    super.writeToNBT(nbt)
    nbt.setInteger("facing", currentfacing.ordinal)
    nbt.setBoolean("busy", isBusy)
    val taglist: NBTTagList = new NBTTagList
    import scala.collection.JavaConversions._
    for (capsule <- contents)
    {
      taglist.appendTag(YATSRegistry.getCapsuleNBT(capsule))
    }
    nbt.setTag("contents", taglist)
    if (YATS.IS_DEBUG) LazUtils.logNormal("Transparency! Contents of tube at %s,%s,%s are: %s", xCoord : Integer, yCoord : Integer, zCoord : Integer, contents.toString)
  }

  override def readFromNBT(nbt: NBTTagCompound)
  {
    super.readFromNBT(nbt)
    currentfacing = ForgeDirection.values()(nbt.getInteger("facing"))
    isBusy = nbt.getBoolean("busy")
    val list: NBTTagList = nbt.getTag("contents").asInstanceOf[NBTTagList]
    contents = new ArrayList[ICapsule]
    {
      var i: Int = 0
      while (i < list.tagCount)
      {
        {
          contents.add(YATSRegistry.handleCapsuleNBT(list.tagAt(i).asInstanceOf[NBTTagCompound]))
        }
        ({
          i += 1;
          i - 1
        })
      }
    }
    if (YATS.IS_DEBUG) LazUtils.logNormal("Literacy! Read tag list %s into tube at %s, %s, %s", list.toString, xCoord : Integer, yCoord : Integer, zCoord : Integer)
    deferUpdate = true
  }

  def AcceptsItemsOnSide(side: ForgeDirection): Boolean =
  {
    return (side eq currentfacing)
  }

  def IsConnectedOnSide(side: ForgeDirection): Boolean =
  {
    return connections(side.ordinal)
  }

  def CanAccept(capsule: ICapsule): Boolean =
  {
    return true
  }

  def CanRoute() : Boolean =
  {
    return !isContinuedSignal
  }

  def GetPressure(): Int =
  {
    return 1
  }

  def SetPressure(pressure: Int)
  {
  }

  def GetColour: Colours =
  {
    return Colours.NONE
  }

  def SetColour(colour: Colours)
  {
  }

  def GetAdditionalPriority: Int =
  {
    return 1
  }

  def AcceptCapsule(capsule: ICapsule)
  {
    capsule.resetProgress
    pending.add(capsule)
  }

  def SetConnectionOnSide(side: ForgeDirection, connected: Boolean)
  {
    connections(side.ordinal) = connected
  }

  def IsConnectableOnSide(side: ForgeDirection): Boolean =
  {
    return (side == currentfacing || side.getOpposite == currentfacing)
  }

  private var currentfacing: ForgeDirection = ForgeDirection.WEST
  private var isContinuedSignal: Boolean = false

  private var contents: ArrayList[ICapsule] = new ArrayList[ICapsule]
  private var pending: ArrayList[ICapsule] = new ArrayList[ICapsule]
  private var connections: Array[Boolean] = new Array[Boolean](6)
  private var deferUpdate: Boolean = false
}