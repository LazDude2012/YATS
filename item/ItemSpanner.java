package YATS.item;

import YATS.api.I6WayWrenchable;
import YATS.common.YATS;
import YATS.tile.TileTube;
import YATS.util.Colours;
import YATS.util.LazUtils;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class ItemSpanner extends Item
{

	public ItemSpanner(int id)
	{
		super(id);
		setUnlocalizedName("YATSItemSpanner");
		setCreativeTab(YATS.tabYATS);
	}

	@Override
	public void registerIcons(IconRegister register)
	{
		this.itemIcon = register.registerIcon("YATS:itemSpanner");
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
	                         float hitX,float hitY, float hitZ)
	{
		if(player.isSneaking())return false;
		TileEntity tile = world.getBlockTileEntity(x,y,z);
		if(tile instanceof TileTube)
		{
			TileTube tube = (TileTube) tile;
			int colour = tube.GetColour().ordinal();
			if(colour == 16) tube.SetColour(Colours.values()[0]);
			else tube.SetColour(Colours.values()[colour+1]);
			return true;
		}
		else if (tile instanceof I6WayWrenchable)
		{
			((I6WayWrenchable)tile).RotateTo(ForgeDirection.getOrientation(side));
			world.markBlockForUpdate(tile.xCoord,tile.yCoord,tile.zCoord);
			return true;
		}
		else
		{
			TellPlayerTheyreAnIdiot(player);
		}
		return false;
	}

	private void TellPlayerTheyreAnIdiot(EntityPlayer player)
	{
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
			player.addChatMessage("You can't use your spanner on that. Idiot.");
	}
}
