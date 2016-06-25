package com.skidsdev.fyrestone.item;

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
		if (worldIn.getBlockState(pos).getBlock() == Blocks.STONE)
		{
			System.out.println("Hit coords: X: " + hitX + " Y: " + hitY + " Z: " + hitZ);
		}
		
		return EnumActionResult.SUCCESS;
	}
}
