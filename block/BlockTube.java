package YATS.block;

import YATS.api.ITubeConnectable;
import YATS.common.YATS;
import YATS.tile.TileTube;
import YATS.util.Colours;
import YATS.util.LazUtils;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import java.util.List;

public class BlockTube extends Block implements ITileEntityProvider
{
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
		return -1;
	}
	
	   @Override
    public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z)
    {
        TileEntity te = iba.getBlockTileEntity(x, y, z);
        if (te != null && te instanceof ITubeConnectable) {
            SetBlockBounds(GetAABBFromState((ITubeConnectable) te));
        } else {
            super.setBlockBoundsBasedOnState(iba, x, y, z);
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World wrd, int x, int y, int z)
    {
        TileEntity te = wrd.getBlockTileEntity(x, y, z);
        if (te != null && te instanceof ITubeConnectable) {
            return TranslateAABB(GetAABBFromState((ITubeConnectable) te), x, y, z);
        } else {
            return super.getCollisionBoundingBoxFromPool(wrd, x, y, z);
        }
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World wrd, int x, int y, int z)
    {
        return getCollisionBoundingBoxFromPool(wrd, x, y, z);
    }

    @Override
    public void addCollisionBoxesToList(World wrd, int x, int y, int z, AxisAlignedBB aabb, List lst, Entity ent)
    {
        setBlockBoundsBasedOnState(wrd, x, y, z);
        super.addCollisionBoxesToList(wrd, x, y, z, aabb, lst, ent);
    }

    private AxisAlignedBB GetAABBFromState(ITubeConnectable te)
    {
        AxisAlignedBB aabb = GetAABB(4, 4, 4, 12, 12, 12);
        for (int i = 0; i < 6; i++) {
            ForgeDirection side = ForgeDirection.values()[i];
            if (te.IsConnectedOnSide(side)) {
                switch (side) {
                    case DOWN:
                        aabb.setBounds(aabb.minX, 0, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ);
                        break;
                    case UP:
                        aabb.setBounds(aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, 1, aabb.maxZ);
                        break;
                    case NORTH:
                        aabb.setBounds(aabb.minX, aabb.minY, 0, aabb.maxX, aabb.maxY, aabb.maxZ);
                        break;
                    case SOUTH:
                        aabb.setBounds(aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, 1);
                        break;
                    case WEST:
                        aabb.setBounds(0, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ);
                        break;
                    case EAST:
                        aabb.setBounds(aabb.minX, aabb.minY, aabb.minZ, 1, aabb.maxY, aabb.maxZ);
                        break;
                }
            }
        }
        return aabb;
    }

    private AxisAlignedBB TranslateAABB(AxisAlignedBB aabb, int x, int y, int z)
    {
        aabb.minX += x;
        aabb.minY += y;
        aabb.minZ += z;
        aabb.maxX += x;
        aabb.maxY += y;
        aabb.maxZ += z;
        return aabb;
    }

    private AxisAlignedBB GetAABB(int nX, int nY, int nZ, int mX, int mY, int mZ)
    {
        final double p = 1D / 16D;
        return AxisAlignedBB.getAABBPool().getAABB(nX * p, nY * p, nZ * p, mX * p, mY * p, mZ * p);
    }

    private void SetBlockBounds(AxisAlignedBB aabb)
    {
        this.maxX = aabb.maxX;
        this.maxY = aabb.maxY;
        this.maxZ = aabb.maxZ;
        this.minX = aabb.minX;
        this.minY = aabb.minY;
        this.minZ = aabb.minZ;
    }
    
    @Override
    public void registerIcons(IconRegister ir)
    {
        blockIcon = ir.registerIcon("YATS:yats-tubecore");
    }

    @Override
    public Icon getIcon(int par1, int par2)
    {
        return blockIcon;
    }
}
