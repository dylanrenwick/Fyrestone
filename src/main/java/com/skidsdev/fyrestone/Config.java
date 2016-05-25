package com.skidsdev.fyrestone;

import java.io.File;

import com.skidsdev.fyrestone.block.BlockFyrestoneOre;
import com.skidsdev.fyrestone.block.ModBlocks;
import com.skidsdev.fyrestone.item.ModItems;

import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Config
{
	public static final String CATEGORY_GENERAL = "general";
	public static final String CATEGORY_CLIENT = "client";
	public static final String CATEGORY_DEBUG = "debug";
	
	public static boolean logOreSpawns;
	
	public static BlockFyrestoneOre fyrestoneOre;
	public static ItemBlock itemFyrestoneOre;
	
	public static Configuration configuration;
	
	public Config(File configFile)
	{
		configuration = new Configuration(configFile);
		configuration.load();
		processConfigFile();
		
		configuration.save();
	}
	
	public void setupBlocks()
	{
		ModBlocks.createBlocks();
	}
	
	public void setupItems()
	{
		ModItems.createItems();
	}
	
	public void setupCrafting()
	{
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockFyrestoneBlock), new Object[] {"###", "###", "###", '#', ModItems.itemFyrestoneIngot});
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.itemFyrestoneIngot, 9), ModBlocks.blockFyrestoneBlock);
		
		GameRegistry.addRecipe(new ItemStack(ModItems.itemFyrestoneSword), new Object[] {"#", "#", "=", '#', ModItems.itemFyrestoneIngot, '=', Items.stick});
		
		GameRegistry.addSmelting(ModItems.itemFyrestoneChunk, new ItemStack(ModItems.itemFyrestoneIngot), 0.7F);
	}
	
	private void processConfigFile()
	{
		doGeneralConfigs();
		doDebugConfigs();
	}
	
	private void doDebugConfigs()
	{
		Property p;
		p = configuration.get(CATEGORY_GENERAL, "logOreSpawns", false);
		p.setComment("Enable to see exact locations of Fyrestone ore spawns.");
		logOreSpawns = p.getBoolean();
	}
	
	private void doGeneralConfigs()
	{
		
	}
}
