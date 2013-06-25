/**
 * ICapsuleRenderer.java provides an API for YATS capsule renderers.
 *
 * Original author: LazDude2012
 *
 * Last revision: Unknown
 *
 * Documentation: Timmietimtim
 *
 * Documentation Updated: 6/24/2013
 */
package YATS.api;

import YATS.tile.TileTube;

/**
 * ICapsuleRenderer is an API for capsule renderers, in case there was any doubt. Different {@link ICapsule}
 * implementations render differently, ergo the need for a distinct renderer API.
 */
public interface ICapsuleRenderer
{
    /**
     * This method, when overridden, provides the protocol for rendering capsules.
     * @param tube A TileEntity (a TileTube) that locates the capsule.
     * @param capsule A capsule, implementing ICapsule, that is to be rendered.
     * @param x A double indicating the x coordinate of the capsule.
     * @param y A double indicating the y coordinate of the capsule.
     * @param z A double indicating the z coordinate of the capsule.
     */
	public void RenderCapsule(TileTube tube, ICapsule capsule, double x, double y, double z);
}
