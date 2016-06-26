package com.skidsdev.fyrestone.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.skidsdev.fyrestone.block.BlockRitualCircle;

import net.minecraft.item.ItemStack;

public class RitualRecipe
{
	private ArrayList<ItemStack> inputs;
	private BlockRitualCircle.EnumRitualType ritualType;
	private ItemStack output;
	
	public RitualRecipe(ItemStack result, int ritualMeta, ItemStack... inputArgs)
	{
		this(result, BlockRitualCircle.EnumRitualType.values()[ritualMeta], inputArgs);
	}
	public RitualRecipe(ItemStack result, BlockRitualCircle.EnumRitualType ritType, ItemStack... inputArgs)
	{
		output = result;
		ritualType = ritType;
		inputs = new ArrayList<ItemStack>(Arrays.asList(inputArgs));
	}
	
	public ItemStack getOutput()
	{
		return output;
	}
	
	public BlockRitualCircle.EnumRitualType getRitualType()
	{
		return ritualType;
	}
	
	public List<ItemStack> getInputs()
	{
		return inputs;
	}
}
