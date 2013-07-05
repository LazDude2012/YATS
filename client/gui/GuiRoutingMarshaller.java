package YATS.client.gui;

import YATS.common.gui.ContainerRoutingMarshaller;
import YATS.tile.TileRoutingMarshaller;
import YATS.util.Colours;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import org.lwjgl.opengl.GL11;

public class GuiRoutingMarshaller extends GuiContainer
{
    protected TileRoutingMarshaller tile;
    private GuiButton[] rowButtons = new GuiButton[5];
    private int[] colours = new int[5];

    public GuiRoutingMarshaller(InventoryPlayer inv, TileRoutingMarshaller te)
    {
        super(new ContainerRoutingMarshaller(inv,te));
        this.tile=te;
        this.xSize=256;
        this.ySize=245;
    }

    public void initGui()
    {
        super.initGui();
        for(int i = 0; i < 5; i++)
        {
            colours[i] = tile.rowColours[i].ordinal();
            rowButtons[i] = new GuiButton(i,(width - xSize)/2 + 161,(height - ySize)/2 + (i*23+31),82,20,"Tag: "+tile.rowColours[i].name().toLowerCase());
            rowButtons[i].enabled=true;
            buttonList.add(rowButtons[i]);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.renderEngine.bindTexture("/mods/YATS/textures/gui/guiRoutingMarshaller.png");
        // This is the x value of the picture, it will be used later
        int x = (width - xSize) / 2;
        // This is the y value of the picture, it will be used later
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        int id = button.id;
        colours[id] = (colours[id] == 16 ? 0 : colours[id] + 1);
        tile.rowColours[id] = Colours.values()[colours[id]];
        rowButtons[id].displayString = "Tag: "+tile.rowColours[id].name().toLowerCase();
    }
}
