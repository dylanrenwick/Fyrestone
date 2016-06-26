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
	public static final PropertyBool MASTER_BLOCK = PropertyBool.create("master_block");
	
	public BlockRitualCircle(String regName) {
		super(regName, Material.ROCK, 0.0f, 0.0f);
		this.setCreativeTab(null);
	}
	
	@Override
	public TileEntity createTileEntity(World worldIn, IBlockState state)
	{
		if (state.getValue(MASTER_BLOCK)) return new TileEntityRitualCircle();
		return null;
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack stack)
	{
		if (state.getValue(MASTER_BLOCK))
		{
			createRitualCircle(worldIn, pos, state);
		}
	}
	
	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state)
	{
		if (state.getValue(MASTER_BLOCK))
		{
			System.out.println("Master block destroyed");
			for (int x = -1; x < 2; x++)
			{
				for (int z = -1; z < 2; z++)
				{
					worldIn.setBlockState(pos.add(x, 0, z), Blocks.AIR.getDefaultState());
					System.out.println("Master block destroyed non-master");
				}
			}
		}
		else
		{
			BlockPos masterPos = getMasterBlock(worldIn, pos);
			IBlockState masterState = worldIn.getBlockState(masterPos);
			if (masterState != null) masterState.getBlock().onBlockDestroyedByPlayer(worldIn, masterPos, masterState);
			System.out.println("non-master block destroyed");
		}
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) { return true; }
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, RITUAL_TYPE, MASTER_BLOCK);
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
		if (state.getValue(MASTER_BLOCK)) meta *= 2;
		
		return meta;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		boolean masterBlock = false;
		if (meta > EnumRitualType.values().length - 1)
		{
			meta /= 2;
			masterBlock = true;
		}
		return this.getDefaultState().withProperty(RITUAL_TYPE, EnumRitualType.fromMetadata(meta)).withProperty(MASTER_BLOCK, masterBlock);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) { return false; }

	@Override
	public boolean isFullCube(IBlockState state) { return false; }
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World worldIn, BlockPos pos) { return null; }
	
	private BlockPos getMasterBlock(World worldIn, BlockPos pos)
	{
		for (int x = -1; x < 2; x++)
		{
			for (int z = -1; z < 2; z++)
			{
				if (x == 0 && z == 0) continue;
				if(worldIn.getBlockState(pos.add(x, 0, z)).getBlock() == this && worldIn.getBlockState(pos.add(x, 0, z)).getValue(MASTER_BLOCK))
				{
					return pos.add(x, 0, z);
				}
			}
		}
		
		return null;
	}
	
	private void createRitualCircle(World worldIn, BlockPos pos, IBlockState state)
	{
		for (int x = -1; x < 2; x++)
		{
			for (int z = -1; z < 2; z++)
			{
				if (x == 0 && z == 0) continue;
				worldIn.setBlockState(pos.add(x, 0, z), 
						this.getDefaultState()
						.withProperty(BlockRitualCircle.RITUAL_TYPE, (EnumRitualType)state.getValue(BlockRitualCircle.RITUAL_TYPE))
						.withProperty(BlockRitualCircle.MASTER_BLOCK, false));
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
}
