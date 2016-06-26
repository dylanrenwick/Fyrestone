package com.skidsdev.fyrestone.block;

import java.util.List;

import com.skidsdev.fyrestone.tile.TileEntityRitualCircle;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRitualCircle extends BlockBase
{
	public static final PropertyEnum RITUAL_TYPE = PropertyEnum.create("ritual_type", EnumRitualType.class);
	
	public BlockRitualCircle(String regName) {
		super(regName, Material.ROCK, 0.0f, 0.0f);
		this.setCreativeTab(null);
	}
	
	@Override
	public TileEntity createTileEntity(World worldIn, IBlockState state)
	{
		return new TileEntityRitualCircle();
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) { return true; }
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, RITUAL_TYPE);
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
		
		return ritualType.ordinal();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(RITUAL_TYPE, EnumRitualType.fromMetadata(meta));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
	{
		for (EnumRitualType ritualType : EnumRitualType.values())
		{
			list.add(new ItemStack(itemIn, 1, ritualType.ordinal()));
		}
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) { return false; }

	@Override
	public boolean isFullCube(IBlockState state) { return false; }
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World worldIn, BlockPos pos) { return null; }
	
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
