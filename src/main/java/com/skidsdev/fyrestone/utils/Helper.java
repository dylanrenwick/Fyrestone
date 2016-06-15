package com.skidsdev.fyrestone.utils;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

public final class Helper
{
	public static void spawnEntityItem(World worldIn, ItemStack stack, double x, double z, double y)
	{
		float xRand = worldIn.rand.nextFloat() * 0.8F + 0.1F;
		float yRand = worldIn.rand.nextFloat() * 0.8F + 0.1F;
		float zRand = worldIn.rand.nextFloat() * 0.8F + 0.1F;
		
		EntityItem entityItem = new EntityItem(worldIn, x + xRand, y + yRand, z + zRand, stack.copy());
		
		entityItem.motionX = worldIn.rand.nextGaussian() * 0.05;
		entityItem.motionZ = worldIn.rand.nextGaussian() * 0.05;
		entityItem.motionY = worldIn.rand.nextGaussian() * 0.05 + 0.2;
		
		worldIn.spawnEntityInWorld(entityItem);
	}
	
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

			spawnEntityItem(worldIn, stack, pos.getX(), pos.getY(), pos.getZ());
		}
	}
}
