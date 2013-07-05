package YATS.render;

import YATS.api.ICapsule;
import YATS.common.YATS;
import YATS.tile.TileTube;
import YATS.util.Colours;
import YATS.util.LazUtils;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;

import static net.minecraftforge.common.ForgeDirection.*;
import static org.lwjgl.opengl.GL11.*;

public class TubeRenderer extends TileEntitySpecialRenderer implements IItemRenderer
{
    // TPW_RULES is the boss, he showed me what's up with OpenGL.
    private void RenderTube(double x, double y, double z, double scale, boolean north,
                            boolean south, boolean west, boolean east, boolean up, boolean down, boolean isItem)
    {
        Minecraft.getMinecraft().renderEngine.bindTexture("/mods/YATS/textures/blocks/yats-tubecore.png");
        glPushMatrix();
        glTranslated(x, y, z);
        glScaled(scale, scale, scale);
        glDisable(GL_CULL_FACE);
        if (isItem) glDisable(GL_LIGHTING);

        //region Endless OpenGl Commands
        if (north) {
            //region NORTH ARM
            //NORTH ARM FACE 1: WEST
            glBegin(GL_QUADS);
            glTexCoord2f(0.0f, 0.25f);
            glVertex3f(0.25f, 0.75f, 0.0f);
            glTexCoord2f(0.0f, 0.75f);
            glVertex3f(0.25f, 0.25f, 0.0f);
            glTexCoord2f(0.25f, 0.75f);
            glVertex3f(0.25f, 0.25f, 0.25f);
            glTexCoord2f(0.25f, 0.25f);
            glVertex3f(0.25f, 0.75f, 0.25f);
            glEnd();

            //NORTH ARM FACE 2: EAST
            glBegin(GL_QUADS);
            glTexCoord2f(0.0f, 0.25f);
            glVertex3f(0.75f, 0.75f, 0.0f);
            glTexCoord2f(0.0f, 0.75f);
            glVertex3f(0.75f, 0.25f, 0.0f);
            glTexCoord2f(0.25f, 0.75f);
            glVertex3f(0.75f, 0.25f, 0.25f);
            glTexCoord2f(0.25f, 0.25f);
            glVertex3f(0.75f, 0.75f, 0.25f);
            glEnd();

            //NORTH ARM FACE 3: TOP
            glBegin(GL_QUADS);
            glTexCoord2f(0.25f, 0.0f);
            glVertex3f(0.25f, 0.75f, 0.0f);
            glTexCoord2f(0.25f, 0.25f);
            glVertex3f(0.25f, 0.75f, 0.25f);
            glTexCoord2f(0.75f, 0.25f);
            glVertex3f(0.75f, 0.75f, 0.25f);
            glTexCoord2f(0.75f, 0.0f);
            glVertex3f(0.75f, 0.75f, 0.0f);
            glEnd();

            //NORTH ARM FACE 4: BOTTOM
            glBegin(GL_QUADS);
            glTexCoord2f(0.25f, 0.0f);
            glVertex3f(0.25f, 0.25f, 0.0f);
            glTexCoord2f(0.25f, 0.25f);
            glVertex3f(0.25f, 0.25f, 0.25f);
            glTexCoord2f(0.75f, 0.25f);
            glVertex3f(0.75f, 0.25f, 0.25f);
            glTexCoord2f(0.75f, 0.0f);
            glVertex3f(0.75f, 0.25f, 0.0f);
            glEnd();
            //endregion NORTH ARM
        } else {
            //region CORE FACE 1 : NORTH
            glBegin(GL_QUADS);
            glTexCoord2f(0.25f, 0.25f);
            glVertex3f(0.25f, 0.75f, 0.25f);
            glTexCoord2f(0.25f, 0.75f);
            glVertex3f(0.25f, 0.25f, 0.25f);
            glTexCoord2f(0.75f, 0.75f);
            glVertex3f(0.75f, 0.25f, 0.25f);
            glTexCoord2f(0.75f, 0.25f);
            glVertex3f(0.75f, 0.75f, 0.25f);
            glEnd();
            //endregion FACE 1
        }
        if (south) {
            //region SOUTH ARM

            //SOUTH ARM FACE 1: WEST
            glBegin(GL_QUADS);
            glTexCoord2f(1.0f, 0.25f);
            glVertex3f(0.25f, 0.75f, 1.0f);
            glTexCoord2f(1.0f, 0.75f);
            glVertex3f(0.25f, 0.25f, 1.0f);
            glTexCoord2f(0.75f, 0.75f);
            glVertex3f(0.25f, 0.25f, 0.75f);
            glTexCoord2f(0.75f, 0.25f);
            glVertex3f(0.25f, 0.75f, 0.75f);
            glEnd();

            //SOUTH ARM FACE 2: EAST
            glBegin(GL_QUADS);
            glTexCoord2f(1.0f, 0.25f);
            glVertex3f(0.75f, 0.75f, 1.0f);
            glTexCoord2f(1.0f, 0.75f);
            glVertex3f(0.75f, 0.25f, 1.0f);
            glTexCoord2f(0.75f, 0.75f);
            glVertex3f(0.75f, 0.25f, 0.75f);
            glTexCoord2f(0.75f, 0.25f);
            glVertex3f(0.75f, 0.75f, 0.75f);
            glEnd();

            //SOUTH ARM FACE 3: TOP
            glBegin(GL_QUADS);
            glTexCoord2f(0.25f, 1.0f);
            glVertex3f(0.25f, 0.75f, 1.0f);
            glTexCoord2f(0.25f, 0.75f);
            glVertex3f(0.25f, 0.75f, 0.75f);
            glTexCoord2f(0.75f, 0.75f);
            glVertex3f(0.75f, 0.75f, 0.75f);
            glTexCoord2f(0.75f, 1.0f);
            glVertex3f(0.75f, 0.75f, 1.0f);
            glEnd();

            //SOUTH ARM FACE 4: BOTTOM
            glBegin(GL_QUADS);
            glTexCoord2f(0.25f, 1.0f);
            glVertex3f(0.25f, 0.25f, 1.0f);
            glTexCoord2f(0.25f, 0.75f);
            glVertex3f(0.25f, 0.25f, 0.75f);
            glTexCoord2f(0.75f, 0.75f);
            glVertex3f(0.75f, 0.25f, 0.75f);
            glTexCoord2f(0.75f, 1.0f);
            glVertex3f(0.75f, 0.25f, 1.0f);
            glEnd();

            //endregion SOUTH ARM
        } else {
            //region CORE FACE 2 : SOUTH
            glBegin(GL_QUADS);
            glTexCoord2f(0.25f, 0.25f);
            glVertex3f(0.25f, 0.75f, 0.75f);
            glTexCoord2f(0.25f, 0.75f);
            glVertex3f(0.25f, 0.25f, 0.75f);
            glTexCoord2f(0.75f, 0.75f);
            glVertex3f(0.75f, 0.25f, 0.75f);
            glTexCoord2f(0.75f, 0.25f);
            glVertex3f(0.75f, 0.75f, 0.75f);
            glEnd();
            //endregion FACE 2
        }
        if (west) {
            //region WEST ARM

            //WEST ARM FACE 1: SOUTH
            glBegin(GL_QUADS);
            glTexCoord2f(0.0f, 0.25f);
            glVertex3f(0.0f, 0.75f, 0.75f);
            glTexCoord2f(0.0f, 0.75f);
            glVertex3f(0.0f, 0.25f, 0.75f);
            glTexCoord2f(0.25f, 0.75f);
            glVertex3f(0.25f, 0.25f, 0.75f);
            glTexCoord2f(0.25f, 0.25f);
            glVertex3f(0.25f, 0.75f, 0.75f);
            glEnd();

            //WEST ARM FACE 2: NORTH
            glBegin(GL_QUADS);
            glTexCoord2f(0.0f, 0.25f);
            glVertex3f(0.0f, 0.75f, 0.25f);
            glTexCoord2f(0.0f, 0.75f);
            glVertex3f(0.0f, 0.25f, 0.25f);
            glTexCoord2f(0.25f, 0.75f);
            glVertex3f(0.25f, 0.25f, 0.25f);
            glTexCoord2f(0.25f, 0.25f);
            glVertex3f(0.25f, 0.75f, 0.25f);
            glEnd();

            //WEST ARM FACE 3: TOP
            glBegin(GL_QUADS);
            glTexCoord2f(0.25f, 0.0f);
            glVertex3f(0.0f, 0.75f, 0.25f);
            glTexCoord2f(0.25f, 0.25f);
            glVertex3f(0.25f, 0.75f, 0.25f);
            glTexCoord2f(0.75f, 0.25f);
            glVertex3f(0.25f, 0.75f, 0.75f);
            glTexCoord2f(0.75f, 0.0f);
            glVertex3f(0.0f, 0.75f, 0.75f);
            glEnd();

            //WEST ARM FACE 4: BOTTOM
            glBegin(GL_QUADS);
            glTexCoord2f(0.25f, 0.0f);
            glVertex3f(0.0f, 0.25f, 0.25f);
            glTexCoord2f(0.25f, 0.25f);
            glVertex3f(0.25f, 0.25f, 0.25f);
            glTexCoord2f(0.75f, 0.25f);
            glVertex3f(0.25f, 0.25f, 0.75f);
            glTexCoord2f(0.75f, 0.0f);
            glVertex3f(0.0f, 0.25f, 0.75f);
            glEnd();

            //endregion
        } else {
            //region CORE FACE 3 : WEST
            glBegin(GL_QUADS);
            glTexCoord2f(0.25f, 0.25f);
            glVertex3f(0.25f, 0.75f, 0.25f);
            glTexCoord2f(0.25f, 0.75f);
            glVertex3f(0.25f, 0.25f, 0.25f);
            glTexCoord2f(0.75f, 0.75f);
            glVertex3f(0.25f, 0.25f, 0.75f);
            glTexCoord2f(0.75f, 0.25f);
            glVertex3f(0.25f, 0.75f, 0.75f);
            glEnd();
            //endregion FACE 3
        }
        if (east) {
            //region EAST ARM

            //EAST ARM FACE 1: SOUTH
            glBegin(GL_QUADS);
            glTexCoord2f(1.0f, 0.25f);
            glVertex3f(1.0f, 0.75f, 0.75f);
            glTexCoord2f(1.0f, 0.75f);
            glVertex3f(1.0f, 0.25f, 0.75f);
            glTexCoord2f(0.75f, 0.75f);
            glVertex3f(0.75f, 0.25f, 0.75f);
            glTexCoord2f(0.75f, 0.25f);
            glVertex3f(0.75f, 0.75f, 0.75f);
            glEnd();

            //EAST ARM FACE 2: NORTH
            glBegin(GL_QUADS);
            glTexCoord2f(1.0f, 0.25f);
            glVertex3f(1.0f, 0.75f, 0.25f);
            glTexCoord2f(1.0f, 0.75f);
            glVertex3f(1.0f, 0.25f, 0.25f);
            glTexCoord2f(0.75f, 0.75f);
            glVertex3f(0.75f, 0.25f, 0.25f);
            glTexCoord2f(0.75f, 0.25f);
            glVertex3f(0.75f, 0.75f, 0.25f);
            glEnd();

            //EAST ARM FACE 3: TOP
            glBegin(GL_QUADS);
            glTexCoord2f(1.0f, 0.25f);
            glVertex3f(1.0f, 0.75f, 0.25f);
            glTexCoord2f(1.0f, 0.75f);
            glVertex3f(1.0f, 0.75f, 0.75f);
            glTexCoord2f(0.75f, 0.75f);
            glVertex3f(0.75f, 0.75f, 0.75f);
            glTexCoord2f(0.75f, 0.25f);
            glVertex3f(0.75f, 0.75f, 0.25f);
            glEnd();

            //EAST ARM FACE 4: BOTTOM
            glBegin(GL_QUADS);
            glTexCoord2f(1.0f, 0.25f);
            glVertex3f(1.0f, 0.25f, 0.25f);
            glTexCoord2f(1.0f, 0.75f);
            glVertex3f(1.0f, 0.25f, 0.75f);
            glTexCoord2f(0.75f, 0.75f);
            glVertex3f(0.75f, 0.25f, 0.75f);
            glTexCoord2f(0.75f, 0.25f);
            glVertex3f(0.75f, 0.25f, 0.25f);
            glEnd();

            //endregion
        } else {
            //region CORE FACE 4: EAST
            glBegin(GL_QUADS);
            glTexCoord2f(0.25f, 0.25f);
            glVertex3f(0.75f, 0.75f, 0.25f);
            glTexCoord2f(0.25f, 0.75f);
            glVertex3f(0.75f, 0.25f, 0.25f);
            glTexCoord2f(0.75f, 0.75f);
            glVertex3f(0.75f, 0.25f, 0.75f);
            glTexCoord2f(0.75f, 0.25f);
            glVertex3f(0.75f, 0.75f, 0.75f);
            glEnd();
            //endregion
        }
        if (up) {
            //region TOP ARM

            //TOP ARM FACE 1: NORTH
            glBegin(GL_QUADS);
            glTexCoord2f(0.25f, 0.0f);
            glVertex3f(0.25f, 1.0f, 0.25f);
            glTexCoord2f(0.25f, 0.25f);
            glVertex3f(0.25f, 0.75f, 0.25f);
            glTexCoord2f(0.75f, 0.25f);
            glVertex3f(0.75f, 0.75f, 0.25f);
            glTexCoord2f(0.75f, 0.0f);
            glVertex3f(0.75f, 1.0f, 0.25f);
            glEnd();

            //TOP ARM FACE 2: SOUTH
            glBegin(GL_QUADS);
            glTexCoord2f(0.25f, 0.0f);
            glVertex3f(0.25f, 1.0f, 0.75f);
            glTexCoord2f(0.25f, 0.25f);
            glVertex3f(0.25f, 0.75f, 0.75f);
            glTexCoord2f(0.75f, 0.25f);
            glVertex3f(0.75f, 0.75f, 0.75f);
            glTexCoord2f(0.75f, 0.0f);
            glVertex3f(0.75f, 1.0f, 0.75f);
            glEnd();

            //TOP ARM FACE 3: WEST
            glBegin(GL_QUADS);
            glTexCoord2f(0.25f, 0.0f);
            glVertex3f(0.25f, 1.0f, 0.25f);
            glTexCoord2f(0.25f, 0.25f);
            glVertex3f(0.25f, 0.75f, 0.25f);
            glTexCoord2f(0.75f, 0.25f);
            glVertex3f(0.25f, 0.75f, 0.75f);
            glTexCoord2f(0.75f, 0.0f);
            glVertex3f(0.25f, 1.0f, 0.75f);
            glEnd();

            //TOP ARM FACE 4: EAST
            glBegin(GL_QUADS);
            glTexCoord2f(0.25f, 0.0f);
            glVertex3f(0.75f, 1.0f, 0.25f);
            glTexCoord2f(0.25f, 0.25f);
            glVertex3f(0.75f, 0.75f, 0.25f);
            glTexCoord2f(0.75f, 0.25f);
            glVertex3f(0.75f, 0.75f, 0.75f);
            glTexCoord2f(0.75f, 0.0f);
            glVertex3f(0.75f, 1.0f, 0.75f);
            glEnd();

            //endregion
        } else {
            //region CORE FACE 5: TOP
            glBegin(GL_QUADS);
            glTexCoord2f(0.25f, 0.25f);
            glVertex3f(0.25f, 0.75f, 0.25f);
            glTexCoord2f(0.25f, 0.75f);
            glVertex3f(0.25f, 0.75f, 0.75f);
            glTexCoord2f(0.75f, 0.75f);
            glVertex3f(0.75f, 0.75f, 0.75f);
            glTexCoord2f(0.75f, 0.25f);
            glVertex3f(0.75f, 0.75f, 0.25f);
            glEnd();
            //endregion FACE 5
        }
        if (down) {
            //region BOTTOM ARM

            //BOTTOM ARM FACE 1: NORTH
            glBegin(GL_QUADS);
            glTexCoord2f(0.25f, 1.0f);
            glVertex3f(0.25f, 0.0f, 0.25f);
            glTexCoord2f(0.25f, 0.75f);
            glVertex3f(0.25f, 0.25f, 0.25f);
            glTexCoord2f(0.75f, 0.75f);
            glVertex3f(0.75f, 0.25f, 0.25f);
            glTexCoord2f(0.75f, 1.0f);
            glVertex3f(0.75f, 0.0f, 0.25f);
            glEnd();

            //BOTTOM ARM FACE 2: SOUTH
            glBegin(GL_QUADS);
            glTexCoord2f(0.25f, 1.0f);
            glVertex3f(0.25f, 0.0f, 0.75f);
            glTexCoord2f(0.25f, 0.75f);
            glVertex3f(0.25f, 0.25f, 0.75f);
            glTexCoord2f(0.75f, 0.75f);
            glVertex3f(0.75f, 0.25f, 0.75f);
            glTexCoord2f(0.75f, 1.0f);
            glVertex3f(0.75f, 0.0f, 0.75f);
            glEnd();

            //BOTTOM ARM FACE 3: WEST
            glBegin(GL_QUADS);
            glTexCoord2f(0.25f, 1.0f);
            glVertex3f(0.25f, 0.0f, 0.25f);
            glTexCoord2f(0.25f, 0.75f);
            glVertex3f(0.25f, 0.25f, 0.25f);
            glTexCoord2f(0.75f, 0.75f);
            glVertex3f(0.25f, 0.25f, 0.75f);
            glTexCoord2f(0.75f, 1.0f);
            glVertex3f(0.25f, 0.0f, 0.75f);
            glEnd();

            //BOTTOM ARM FACE 4: EAST
            glBegin(GL_QUADS);
            glTexCoord2f(0.25f, 1.0f);
            glVertex3f(0.75f, 0.0f, 0.25f);
            glTexCoord2f(0.25f, 0.75f);
            glVertex3f(0.75f, 0.25f, 0.25f);
            glTexCoord2f(0.75f, 0.75f);
            glVertex3f(0.75f, 0.25f, 0.75f);
            glTexCoord2f(0.75f, 1.0f);
            glVertex3f(0.75f, 0.0f, 0.75f);
            glEnd();

            //endregion
        } else {
            // region CORE FACE 6: BOTTOM
            glBegin(GL_QUADS);
            glTexCoord2f(0.25f, 0.25f);
            glVertex3f(0.25f, 0.25f, 0.25f);
            glTexCoord2f(0.25f, 0.75f);
            glVertex3f(0.25f, 0.25f, 0.75f);
            glTexCoord2f(0.75f, 0.75f);
            glVertex3f(0.75f, 0.25f, 0.75f);
            glTexCoord2f(0.75f, 0.25f);
            glVertex3f(0.75f, 0.25f, 0.25f);
            glEnd();
            //endregion
        }
        //endregion

        glEnable(GL_CULL_FACE);
        if (isItem) glEnable(GL_LIGHTING);
        glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f)
    {
        glPushMatrix();
        glDisable(GL_LIGHTING);
        TileTube tube = (TileTube) tileentity;
        RenderTube(x, y, z, 1,
                tube.IsConnectedOnSide(NORTH),
                tube.IsConnectedOnSide(SOUTH),
                tube.IsConnectedOnSide(WEST),
                tube.IsConnectedOnSide(EAST),
                tube.IsConnectedOnSide(UP),
                tube.IsConnectedOnSide(DOWN), false);
        if (tube.GetColour() != Colours.NONE) RenderPaint(tube.worldObj, tube, x, y, z);
        if (tube.contents.size() != 0 && YATS.IS_DEBUG)
            LazUtils.logNormal("Curiosity! Tube contents size: %s", tube.contents.size());
        for (ICapsule capsule : tube.contents) {
            if (YATS.IS_DEBUG)
                FMLLog.info("Sensuality! Rendering capsule at %s, %s, %s!", x, y, z);
            capsule.GetRenderer().RenderCapsule(tube, capsule, x, y, z);
        }
        glEnable(GL_LIGHTING);
        glPopMatrix();
    }

    private void RenderPaint(World world, TileTube tube, double x, double y, double z)
    {
        Colours colour = tube.GetColour();
        if (colour == Colours.NONE) return;
        byte[] rgb = Colours.toRGB(colour);
        Minecraft.getMinecraft().renderEngine.bindTexture("/mods/YATS/textures/blocks/yats-paintring.png");
        glPushMatrix();
        glTranslated(x, y, z);
        glDisable(GL_CULL_FACE);
        glColor3ub(rgb[0], rgb[1], rgb[2]);

        //region PAINT RING: OUTSIDE FACES

        //COORDS: LOW 0.21875 HIGH 0.78125
        //bottom, top, bottom, top
        glBegin(GL_QUAD_STRIP);
        glTexCoord2f(0f, 1f);
        glVertex3d(0.21875, 0.21875, 0.21875);
        glTexCoord2f(0f, 0f);
        glVertex3d(0.21875, 0.78125, 0.21875);
        glTexCoord2f(1f, 1f);
        glVertex3d(0.78125, 0.21875, 0.21875);
        glTexCoord2f(1f, 0f);
        glVertex3d(0.78125, 0.78125, 0.21875);
        glTexCoord2f(0f, 1f);
        glVertex3d(0.78125, 0.21875, 0.78125);
        glTexCoord2f(0f, 0f);
        glVertex3d(0.78125, 0.78125, 0.78125);
        glTexCoord2f(1f, 1f);
        glVertex3d(0.21875, 0.21875, 0.78125);
        glTexCoord2f(1, 0);
        glVertex3d(0.21875, 0.78125, 0.78125);
        glTexCoord2f(0f, 1f);
        glVertex3d(0.21875, 0.21875, 0.21875);
        glTexCoord2f(0f, 0f);
        glVertex3d(0.21875, 0.78125, 0.21875);
        glEnd();
        glBegin(GL_QUADS);
        glTexCoord2f(0f, 0f);
        glVertex3d(0.21875, 0.78125, 0.21875);
        glTexCoord2f(0f, 1f);
        glVertex3d(0.21875, 0.78125, 0.78125);
        glTexCoord2f(1f, 1f);
        glVertex3d(0.78125, 0.78125, 0.78125);
        glTexCoord2f(1f, 0f);
        glVertex3d(0.78125, 0.78125, 0.21875);
        glTexCoord2f(0f, 0f);
        glVertex3d(0.21875, 0.21875, 0.21875);
        glTexCoord2f(0f, 1f);
        glVertex3d(0.21875, 0.21875, 0.78125);
        glTexCoord2f(1f, 1f);
        glVertex3d(0.78125, 0.21875, 0.78125);
        glTexCoord2f(1f, 0f);
        glVertex3d(0.78125, 0.21875, 0.21875);
        glEnd();
        glPopMatrix();
        glEnable(GL_CULL_FACE);
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        switch (type) {
            case ENTITY:
                RenderItem(-0.75, -0.25, -0.75, 1.5);
                break;
            case EQUIPPED:
                RenderItem(-0.1, 0, -0.1, 1.25);
                break;
            case EQUIPPED_FIRST_PERSON:
                RenderItem(-0.25, -0.33, -0.2, 1.5);
                break;
            case INVENTORY:
                RenderItem(-0.25, -0.3075, -0.2, 1.23);
                break;
        }
    }

    private void RenderItem(double x, double y, double z, double scale)
    {
        RenderTube(x, y, z, scale, false, false, false, false, true, true, true);
    }
}
