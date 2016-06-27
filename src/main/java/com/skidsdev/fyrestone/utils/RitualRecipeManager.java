package com.skidsdev.fyrestone.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.skidsdev.fyrestone.block.BlockRitualCircle;

import net.minecraft.item.ItemStack;

public class RitualRecipeManager
{
	private static final HashSet<RitualRecipe> recipes = new HashSet<RitualRecipe>();
	
	public static void RegisterRecipe(RitualRecipe recipe)
	{
		if (!recipes.contains(recipe)) recipes.add(recipe);
	}
	
	public static RitualRecipe GetRecipeFromInputs(BlockRitualCircle.EnumRitualType ritualType, ItemStack... inputs)
	{		
		for (RitualRecipe recipe : recipes)
		{
			if (recipe.getRitualType() == ritualType && compareInputSets(Arrays.asList(inputs), recipe.getInputs()))
			{
				return recipe;
			}
		}
		
		return null;
	}
	
	private static boolean compareInputSets(List<ItemStack> firstSet, List<ItemStack> secondSet)
	{
		if(firstSet.size() != secondSet.size()) return false;
		
		for (ItemStack stack : firstSet)
		{
			boolean equal = false;
			for (ItemStack secStack : secondSet)
			{
				if (ItemStack.areItemStacksEqual(stack, secStack))
				{
					equal = true;
					break;
				}
			}
			
			if (!equal) return false;
		}
		
		return true;
	}
}
