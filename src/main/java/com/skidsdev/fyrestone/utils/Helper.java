package com.skidsdev.fyrestone.utils;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

public final class Helper
{	
	public static void dropInventory(IItemHandler inv, World worldIn, BlockPos pos)
	{
		if (inv == null)
			return;

		for (int i = 1; i < inv.getSlots(); i++)
		{
			ItemStack stack = inv.getStackInSlot(i);

			if (stack == null)
			{
				continue;
			}
			
			EntityItem entityItem = new EntityItem(worldIn, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, stack);
			worldIn.spawnEntityInWorld(entityItem);
		}
	}
}
