package com.skidsdev.fyrestone.block;

import com.skidsdev.fyrestone.block.BlockRitualCircle.EnumRitualType;
import com.skidsdev.fyrestone.tile.TileEntityRitualCircle;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRitualCircle extends BlockBase
{
	public static final PropertyEnum RITUAL_TYPE = PropertyEnum.create("ritual_type", EnumRitualType.class);
	public static final PropertyEnum CIRCLE_PART = PropertyEnum.create("circle_part", EnumCirclePart.class);
	
	public BlockRitualCircle(String regName) {
		super(regName, Material.ROCK, 0.0f, 0.0f);
		this.setCreativeTab(null);
	}
	
	@Override
	public TileEntity createTileEntity(World worldIn, IBlockState state)
	{
		if (getIsMasterBlock(state)) return new TileEntityRitualCircle();
		return null;
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack stack)
	{
		//if (getIsMasterBlock(state))
		//{
			createRitualCircle(worldIn, pos, state);
		//}
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		if (getIsMasterBlock(state)) return state.withProperty(CIRCLE_PART, EnumCirclePart.CENTER);
		
		//Pos X = East, Pos Z = South
		BlockPos masterPos = getMasterBlock(worldIn, pos);
		if (masterPos == null) return state;
		
		int xdif = 0;
		int zdif = 0;
		
		if (masterPos.getX() < pos.getX()) xdif = 1;
		else if (masterPos.getX() > pos.getX()) xdif = -1;
		if (masterPos.getZ() < pos.getZ()) zdif = 1;
		else if (masterPos.getZ() > pos.getZ()) zdif = -1;
		
		return state.withProperty(CIRCLE_PART, EnumCirclePart.getCirclePartFromDir(xdif, zdif));
	}
	
	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state)
	{
		if (getIsMasterBlock(state))
		{
			for (int x = -1; x < 2; x++)
			{
				for (int z = -1; z < 2; z++)
				{
					worldIn.setBlockState(pos.add(x, 0, z), Blocks.AIR.getDefaultState());
				}
			}
		}
		else
		{
			if (state.getValue(CIRCLE_PART) == EnumCirclePart.CENTER) return;
			BlockPos masterPos = getMasterBlock(worldIn, pos);
			IBlockState masterState = null;
			if (masterPos != null) masterState = worldIn.getBlockState(masterPos);
			if (masterState != null) masterState.getBlock().onBlockDestroyedByPlayer(worldIn, masterPos, masterState);
		}
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) { return getIsMasterBlock(state); }
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, RITUAL_TYPE, CIRCLE_PART);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		return new AxisAlignedBB(0, 0, 0, 1, 0, 1);
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() { return BlockRenderLayer.CUTOUT; }
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		EnumRitualType ritualType = (EnumRitualType)state.getValue(RITUAL_TYPE);
		
		int meta = ritualType.ordinal();
		if (getIsMasterBlock(state)) meta += 8;
		
		return meta;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		boolean masterBlock = false;
		if (meta > EnumRitualType.values().length - 1)
		{
			meta -= 8;
			masterBlock = true;
		}
		return this.getDefaultState().withProperty(RITUAL_TYPE, EnumRitualType.fromMetadata(meta));
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) { return false; }

	@Override
	public boolean isFullCube(IBlockState state) { return false; }
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World worldIn, BlockPos pos) { return null; }
	
	private boolean getIsMasterBlock(IBlockState state)
	{
		return state.getValue(CIRCLE_PART) == EnumCirclePart.CENTER;
	}
	
	private BlockPos getMasterBlock(World worldIn, BlockPos pos)
	{
		for (int x = -1; x < 2; x++)
		{
			for (int z = -1; z < 2; z++)
			{
				if (x == 0 && z == 0) continue;
				if(worldIn.getBlockState(pos.add(x, 0, z)).getBlock() == this && getIsMasterBlock(worldIn.getBlockState(pos.add(x, 0, z))))
				{
					return pos.add(x, 0, z);
				}
			}
		}
		
		return null;
	}
	private BlockPos getMasterBlock(IBlockAccess worldIn, BlockPos pos)
	{
		for (int x = -1; x < 2; x++)
		{
			for (int z = -1; z < 2; z++)
			{
				if (x == 0 && z == 0) continue;
				if(worldIn.getBlockState(pos.add(x, 0, z)).getBlock() == this && getIsMasterBlock(worldIn.getBlockState(pos.add(x, 0, z))))
				{
					return pos.add(x, 0, z);
				}
			}
		}
		
		return null;
	}
	
	private void createRitualCircle(World worldIn, BlockPos pos, IBlockState state)
	{
		worldIn.setBlockState(pos, state.withProperty(BlockRitualCircle.CIRCLE_PART, EnumCirclePart.CENTER));
		
		for (int x = -1; x < 2; x++)
		{
			for (int z = -1; z < 2; z++)
			{
				if (x == 0 && z == 0) continue;
				worldIn.setBlockState(pos.add(x, 0, z), 
						this.getDefaultState()
						.withProperty(BlockRitualCircle.RITUAL_TYPE, (EnumRitualType)state.getValue(BlockRitualCircle.RITUAL_TYPE)));
			}
		}
	}
	
	public enum EnumRitualType implements IStringSerializable
	{
		METALLURGY("metallurgy"),
		ALCHEMY("alchemy"),
		IMBUING("imbuing");
		
		@Override
		public String toString()
		{
			return this.name;
		}
		
		public String getName()
		{
			return this.name;
		}
		
		private final String name;
		
		private EnumRitualType(String i_name)
		{
			this.name = i_name;
		}
		
		public static EnumRitualType fromMetadata(int meta)
		{
			if (meta < 0 || meta >= values().length)	{ meta = 0;	}
			
			return values()[meta];
		}
	}
	
	public enum EnumCirclePart implements IStringSerializable
	{
		NORTH("north"),
		NORTHWEST("north_west"),
		WEST("west"),
		SOUTHWEST("south_west"),
		SOUTH("south"),
		SOUTHEAST("south_east"),
		EAST("east"),
		NORTHEAST("north_east"),
		CENTER("center");
		
		private String name;
		
		private EnumCirclePart(String i_name)
		{
			name = i_name;
		}
		
		public String getName() { return name; }
		
		@Override
		public String toString() { return this.name; }
		
		public static EnumCirclePart getCirclePartFromDir(int xdif, int zdif)
		{
			if (xdif > 0)
			{
				if (zdif > 0)
				{
					return SOUTHEAST;
				}
				else if (zdif < 0)
				{
					return NORTHEAST;
				}
				else
				{
					return EAST;
				}
			}
			else if (xdif < 0)
			{
				if (zdif > 0)
				{
					return SOUTHWEST;
				}
				else if (zdif < 0)
				{
					return NORTHWEST;
				}
				else
				{
					return WEST;
				}
			}
			else
			{
				if (zdif > 0)
				{
					return SOUTH;
				}
				else if (zdif < 0)
				{
					return NORTH;
				}
				else
				{
					return CENTER;
				}
			}
		}
	}
}
