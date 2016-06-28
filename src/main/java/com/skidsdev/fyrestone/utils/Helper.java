package com.skidsdev.fyrestone.utils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

public final class Helper
{	
	public static List<String> formatTooltip(String tooltip) { return formatTooltip(tooltip, 45); }
	public static List<String> formatTooltip(String tooltip, int length)
	{
		List<String> formattedTooltip = new ArrayList<String>();
		
		while(!tooltip.isEmpty())
		{
			if (tooltip.length() > length)
			{
				int lastSpaceIndex = 0;
				
				for(int i = 0; i < length; i++)
				{
					if (tooltip.charAt(i) == ' ') lastSpaceIndex = i;
				}
				formattedTooltip.add(tooltip.substring(0, lastSpaceIndex));
				tooltip = tooltip.substring(lastSpaceIndex);
			}
			else
			{
				formattedTooltip.add(tooltip);
				tooltip = "";
			}
		}
		
		return formattedTooltip;
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
			
			EntityItem entityItem = new EntityItem(worldIn, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, stack);
			worldIn.spawnEntityInWorld(entityItem);
		}
	}
}
