package YATS.client.gui;

import YATS.client.ClientPacketHandler;
import YATS.common.gui.ContainerAdvExtractor;
import YATS.tile.TileAdvExtractor;
import YATS.util.Colours;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import org.lwjgl.opengl.GL11;

public class GuiAdvExtractor extends GuiContainer
{
    protected TileAdvExtractor tile;
    private GuiButton colour;
    private int colourInt;

    public GuiAdvExtractor(InventoryPlayer inv, TileAdvExtractor te)
    {
        super(new ContainerAdvExtractor(inv,te));
        tile = te;
        this.xSize = 176;
        this.ySize = 181;
    }
    public void initGui()
    {
        super.initGui();
        colour = new GuiButton(1,(width - xSize)/2+14,(height-ySize)/2+21,147,20,"Filter colour: "+tile.getFilterColour().name());
        colour.enabled = true;
        buttonList.add(colour);
    }
    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.renderEngine.bindTexture("/mods/YATS/textures/gui/guiAdvExtractor.png");
        // This is the x value of the picture, it will be used later
        int x = (width - xSize) / 2;
        // This is the y value of the picture, it will be used later
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }
    public void actionPerformed(GuiButton button)
    {
        colourInt = (colourInt == 16 ? 0 : colourInt + 1);
        tile.setFilterColour(Colours.values()[colourInt]);
        colour.displayString = "Filter colour: "+tile.getFilterColour().name();
        ClientPacketHandler.DispatchAdvExtPkt(tile);
    }
}
