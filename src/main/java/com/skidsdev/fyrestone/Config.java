package com.skidsdev.fyrestone;

import java.io.File;

import com.skidsdev.fyrestone.block.BlockFyrestoneOre;
import com.skidsdev.fyrestone.block.BlockRegister;
import com.skidsdev.fyrestone.block.BlockRitualCircle;
import com.skidsdev.fyrestone.item.ItemBaseShard;
import com.skidsdev.fyrestone.item.ItemBaseShard.EnumShardType;
import com.skidsdev.fyrestone.item.ItemRegister;
import com.skidsdev.fyrestone.utils.RitualRecipe;
import com.skidsdev.fyrestone.utils.RitualRecipeManager;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
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
		BlockRegister.createBlocks();
	}
	
	public void setupItems()
	{
		ItemRegister.createItems();
	}
	
	public void setupCrafting()
	{
		GameRegistry.addRecipe(new ItemStack(BlockRegister.blockFyrestoneBlock), new Object[] {"###", "###", "###", '#', ItemRegister.itemFyrestoneIngot});
		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegister.itemFyrestoneIngot, 9), BlockRegister.blockFyrestoneBlock);
		
		//Metallurgy Rituals
		RitualRecipeManager.RegisterRecipe(new RitualRecipe(new ItemStack(ItemRegister.itemFyrestoneIngot), 0, ItemBaseShard.getShardStack(1, EnumShardType.FYRESTONE), new ItemStack(Items.IRON_INGOT)));
		RitualRecipeManager.RegisterRecipe(new RitualRecipe(new ItemStack(ItemRegister.itemFyrestoneSword), 0, new ItemStack(ItemRegister.itemFyrestoneIngot, 2), new ItemStack(Items.IRON_SWORD)));
		RitualRecipeManager.RegisterRecipe(new RitualRecipe(ItemBaseShard.getShardStack(1, EnumShardType.WATERSTONE), 0, new ItemStack(ItemRegister.itemFyrestoneCatalyst), ItemBaseShard.getShardStack(1, EnumShardType.FYRESTONE), new ItemStack(Items.WATER_BUCKET), new ItemStack(Items.DYE, 1, EnumDyeColor.BLUE.getDyeDamage())));
		
		//Alchemy Rituals
		RitualRecipeManager.RegisterRecipe(new RitualRecipe(ItemBaseShard.getShardStack(1, EnumShardType.WATERSTONE), 1, ItemBaseShard.getShardStack(1, EnumShardType.FYRESTONE), new ItemStack(Items.DYE, 1, EnumDyeColor.BLUE.getDyeDamage())));
		RitualRecipeManager.RegisterRecipe(new RitualRecipe(ItemBaseShard.getShardStack(1, EnumShardType.EARTHSTONE), 1, ItemBaseShard.getShardStack(1, EnumShardType.FYRESTONE), new ItemStack(Blocks.DIRT), new ItemStack(ItemRegister.itemFyrestoneCatalyst)));
		
		//Imbuing Rituals
		RitualRecipeManager.RegisterRecipe(new RitualRecipe(ItemBaseShard.getShardStack(1, EnumShardType.EARTHSTONE), 2, ItemBaseShard.getShardStack(1, EnumShardType.FYRESTONE), new ItemStack(Blocks.DIRT)));
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