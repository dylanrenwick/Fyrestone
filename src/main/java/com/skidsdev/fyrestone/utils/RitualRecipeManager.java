package com.skidsdev.fyrestone.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import net.minecraft.item.ItemStack;
import scala.actors.threadpool.Arrays;

public class RitualRecipeManager
{
	private static final HashSet<RitualRecipe> recipes = new HashSet<RitualRecipe>();
	
	public static void RegisterRecipe(RitualRecipe recipe)
	{
		if (!recipes.contains(recipe)) recipes.add(recipe);
	}
	
	public static RitualRecipe GetRecipeFromInputs(ItemStack... inputs)
	{		
		for (RitualRecipe recipe : recipes)
		{
			if (compareInputSets(Arrays.asList(inputs), recipe.getInputs()))
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
				if (ItemStack.areItemsEqual(stack, secStack))
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
