package com.skidsdev.fyrestone.item;

import com.skidsdev.fyrestone.block.BlockRegister;
import com.skidsdev.fyrestone.block.BlockRitualCircle;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemFyrestoneShard extends BaseItem
{
	public ItemFyrestoneShard() {
		super("itemFyrestoneShard");
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (worldIn.isRemote) return EnumActionResult.SUCCESS;
		if (getStonePlatform(worldIn, pos) && hitY == 1.0f)
		{
			System.out.println("Valid placement");
			stack.stackSize -= 1;
			worldIn.setBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()), BlockRegister.blockRitualCircle.getDefaultState().withProperty(BlockRitualCircle.RITUAL_TYPE, BlockRitualCircle.EnumRitualType.METALLURGY), 3);
		}
		
		return EnumActionResult.SUCCESS;
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
}
