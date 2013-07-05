package YATS.block;

import YATS.api.ITubeConnectable;
import YATS.tile.TileTube;
import YATS.util.Colours;
import YATS.util.LazUtils;
import YATS.common.YATS;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockTube extends Block implements ITileEntityProvider
{
	public int renderID;
    protected Icon itemicon;
	public BlockTube(int id)
	{
		super(id, Material.rock);
		setCreativeTab(YATS.tabYATS);
		setUnlocalizedName("YATSBlockTube");
	}

    @Override
    public String getItemIconName()
    {
        return "YATS:blockTube";
    }

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entity, ItemStack itemStack)
	{
		CheckTubeConnections(world,x,y,z);
	}

	@Override
	public boolean renderAsNormalBlock() { return false; }

	@Override
	public boolean isOpaqueCube() { return false; }

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int neighborID)
	{
		CheckTubeConnections(world,x,y,z);
	}

    @Override
    public void registerIcons(IconRegister register)
    {
        itemicon = register.registerIcon("YATS:blockTube");
    }

	public static void CheckTubeConnections(World world, int x, int y, int z)
	{
		TileTube originator = (TileTube)world.getBlockTileEntity(x,y,z);
		for(ForgeDirection side : ForgeDirection.VALID_DIRECTIONS)
		{
			TileEntity tile = LazUtils.XYZCoords.FromTile(originator).Next(side).ToTile();
			if(tile instanceof ITubeConnectable)
			{
				ITubeConnectable tube = (ITubeConnectable) tile;
				if(tube.IsConnectableOnSide(side.getOpposite()) && (tube.GetColour() == originator.GetColour() || tube.GetColour() == Colours.NONE
				|| originator.GetColour() == Colours.NONE))
				{
					tube.SetConnectionOnSide(side.getOpposite(),true);
					originator.SetConnectionOnSide(side,true);
				}
				else
				{
					tube.SetConnectionOnSide(side.getOpposite(),false);
					originator.SetConnectionOnSide(side,false);
				}
			}
			else if(tile instanceof IInventory)
			{
				originator.SetConnectionOnSide(side,true);
			}
			else originator.SetConnectionOnSide(side,false);
		}
	}

	@Override
	public int getLightOpacity(World world, int x, int y, int z) { return 0; }

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileTube();
	}

	@Override
	public int getRenderType()
	{
		return renderID;
	}
}
