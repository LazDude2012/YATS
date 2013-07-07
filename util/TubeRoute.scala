package YATS.util

import net.minecraftforge.common.ForgeDirection
import YATS.util.LazUtils.XYZCoords

class TubeRoute (val destination : XYZCoords, val heading : ForgeDirection, val initialdir : ForgeDirection,
                 val distance : Int) extends Comparable[TubeRoute]
{
  var completed : Boolean = false;

  def compareTo(route : TubeRoute) : Int = this.distance - route.distance

  def hashCode() : Int =
  {
    destination.hashCode() *
    31 + heading.ordinal() *
    31 + initialdir.ordinal() *
    31 + distance * (if(completed) 1 else 0)
  }
  def == (other : Any) : Boolean =
  {
    this.hashCode() == other.hashCode()
  }
}