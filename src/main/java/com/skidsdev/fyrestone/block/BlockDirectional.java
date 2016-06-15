package com.skidsdev.fyrestone.block;

import com.skidsdev.fyrestone.utils.Helper;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockDirectional extends BlockBase
{	
	public BlockDirectional(String regName, Material material, float hardness, float resistance) {
		super(regName, material, hardness, resistance);
	}

	@Override
	public BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BlockHorizontal.FACING);
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(BlockHorizontal.FACING).getHorizontalIndex();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.getHorizontal(meta));
	}
	
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return getStateFromMeta(meta).withProperty(BlockHorizontal.FACING, placer.getHorizontalFacing().getOpposite());
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		TileEntity tile = worldIn.getTileEntity(pos);
		
		if (tile != null)
		{
			IItemHandler inv = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

			Helper.dropInventory(inv, worldIn, pos);
		
			worldIn.notifyNeighborsOfStateChange(pos, state.getBlock());
			super.breakBlock(worldIn, pos, state);
		}
	}
	
	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer player)
	{
		if(worldIn.isRemote)
		{
			return;
		}
		
		ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
	}
	
	protected void setFacingMeta(World worldIn, BlockPos pos, EntityPlayer player)
	{
		worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(BlockHorizontal.FACING, player.getHorizontalFacing().getOpposite()));
	}
}
