package com.skidsdev.fyrestone.item;

import java.util.List;

import com.skidsdev.fyrestone.block.BlockRegister;
import com.skidsdev.fyrestone.block.BlockRitualCircle;
import com.skidsdev.fyrestone.block.BlockRitualCircle.EnumRitualType;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBaseShard extends BaseItem
{
	public ItemBaseShard() {
		super("itemShard");
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (worldIn.isRemote) return EnumActionResult.SUCCESS;
		if (getStonePlatform(worldIn, pos) && hitY == 1.0f)
		{
			IBlockState ritualCircle = getRitualCircleBlock(stack);
			
			if (ritualCircle != null)
			{
				worldIn.setBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()), ritualCircle, 3);
				BlockRegister.blockRitualCircle.onBlockPlacedBy(worldIn, pos.add(0, 1, 0), ritualCircle, player, stack);
				stack.stackSize -= 1;
			}
		}
		
		return EnumActionResult.SUCCESS;
	}

	@Override
	public int getMetadata(int damage)
	{
		return damage;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		EnumShardType type = EnumShardType.values()[stack.getMetadata()];
		return super.getUnlocalizedName() + "_" + type.getName();
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List subItems)
	{
		for (EnumShardType type : EnumShardType.values())
		{
			ItemStack subItemStack = new ItemStack(itemIn, 1, type.ordinal());
			subItems.add(subItemStack);
		}
	}
	
	private boolean getStonePlatform(World worldIn, BlockPos pos)
	{
		for (int i = -1; i < 2; i++)
		{
			for (int j = -1; j < 2; j++)
			{
				BlockPos newPos = new BlockPos(pos.getX() + i, pos.getY(), pos.getZ() + j);
				Block block = worldIn.getBlockState(newPos).getBlock();
				
				if (block != Blocks.STONE) return false;
				
				for (int k = 1; k < 3; k++)
				{
					newPos = newPos.add(0, 1, 0);
					block = worldIn.getBlockState(newPos).getBlock();
					
					if (block != Blocks.AIR) return false;
				}
			}
		}
		
		return true;
	}
	
	private IBlockState getRitualCircleBlock(ItemStack stack)
	{
		EnumShardType type = EnumShardType.values()[stack.getMetadata()];
		IBlockState defaultState = BlockRegister.blockRitualCircle.getDefaultState();
		
		switch(type)
		{
			case FYRESTONE:
				return defaultState.withProperty(BlockRitualCircle.RITUAL_TYPE, EnumRitualType.METALLURGY);
			case WATERSTONE:
				return defaultState.withProperty(BlockRitualCircle.RITUAL_TYPE, EnumRitualType.ALCHEMY);
			case EARTHSTONE:
				return defaultState.withProperty(BlockRitualCircle.RITUAL_TYPE, EnumRitualType.IMBUING);
			default:
				return null;			
		}
	}
	
	public enum EnumShardType implements IStringSerializable
	{
		FYRESTONE("fyrestone"),
		WATERSTONE("waterstone"),
		ORDERSTONE("orderstone"),
		CHAOSSTONE("chaosstone"),
		EARTHSTONE("earthstone"),
		AIRSTONE("airstone");
		
		private String name;
		
		private EnumShardType(String i_name)
		{
			name = i_name;
		}

		@Override
		public String getName() {
			return name;
		}
	}
}
