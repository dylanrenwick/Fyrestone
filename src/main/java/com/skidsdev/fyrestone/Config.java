package com.skidsdev.fyrestone;

import java.io.File;

import com.skidsdev.fyrestone.block.BlockFyrestoneOre;
import com.skidsdev.fyrestone.block.BlockRegister;
import com.skidsdev.fyrestone.item.ItemRegister;
import com.skidsdev.fyrestone.utils.ItemNBTHelper;

import net.minecraft.init.Blocks;
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
		
		GameRegistry.addRecipe(new ItemStack(ItemRegister.itemFyrestoneSword), new Object[] {"#", "#", "=", '#', ItemRegister.itemFyrestoneIngot, '=', Items.STICK});
		
		
		ItemStack framedFurnaceWood = new ItemStack(BlockRegister.blockFramedFurnace);
		ItemNBTHelper.setInt(framedFurnaceWood, "frameType", 0);
		GameRegistry.addRecipe(framedFurnaceWood, new Object[] {"===", "=@=", "=#=", '#', ItemRegister.itemFyrestoneIngot, '=', Blocks.LOG, '@', Blocks.FURNACE});

		ItemStack framedFurnaceStone = new ItemStack(BlockRegister.blockFramedFurnace);
		ItemNBTHelper.setInt(framedFurnaceStone, "frameType", 1);
		GameRegistry.addRecipe(framedFurnaceStone, new Object[] {"===", "=@=", "=#=", '#', ItemRegister.itemFyrestoneIngot, '=', Blocks.STONE, '@', Blocks.FURNACE});

		ItemStack framedFurnaceIron = new ItemStack(BlockRegister.blockFramedFurnace);
		ItemNBTHelper.setInt(framedFurnaceIron, "frameType", 2);
		GameRegistry.addRecipe(framedFurnaceIron, new Object[] {"===", "=@=", "=#=", '#', ItemRegister.itemFyrestoneIngot, '=', Items.IRON_INGOT, '@', Blocks.FURNACE});

		ItemStack framedFurnaceNetherrack = new ItemStack(BlockRegister.blockFramedFurnace);
		ItemNBTHelper.setInt(framedFurnaceNetherrack, "frameType", 3);
		GameRegistry.addRecipe(framedFurnaceNetherrack, new Object[] {"===", "=@=", "=#=", '#', ItemRegister.itemFyrestoneIngot, '=', Blocks.NETHERRACK, '@', Blocks.FURNACE});

		ItemStack framedFurnaceQuartz = new ItemStack(BlockRegister.blockFramedFurnace);
		ItemNBTHelper.setInt(framedFurnaceQuartz, "frameType", 4);
		GameRegistry.addRecipe(framedFurnaceQuartz, new Object[] {"===", "=@=", "=#=", '#', ItemRegister.itemFyrestoneIngot, '=', Blocks.QUARTZ_BLOCK, '@', Blocks.FURNACE});

		ItemStack framedFurnaceGold = new ItemStack(BlockRegister.blockFramedFurnace);
		ItemNBTHelper.setInt(framedFurnaceGold, "frameType", 5);
		GameRegistry.addRecipe(framedFurnaceGold, new Object[] {"===", "=@=", "#=#", '#', ItemRegister.itemFyrestoneIngot, '=', Items.GOLD_INGOT, '@', Blocks.FURNACE});

		ItemStack framedFurnaceDiamond = new ItemStack(BlockRegister.blockFramedFurnace);
		ItemNBTHelper.setInt(framedFurnaceDiamond, "frameType", 6);
		GameRegistry.addRecipe(framedFurnaceDiamond, new Object[] {"===", "=@=", "=#=", '#', ItemRegister.itemFyrestoneIngot, '=', Items.DIAMOND, '@', Blocks.FURNACE});

		ItemStack framedFurnaceEmerald = new ItemStack(BlockRegister.blockFramedFurnace);
		ItemNBTHelper.setInt(framedFurnaceEmerald, "frameType", 7);
		GameRegistry.addRecipe(framedFurnaceEmerald, new Object[] {"===", "=@=", "=#=", '#', ItemRegister.itemFyrestoneIngot, '=', Items.EMERALD, '@', Blocks.FURNACE});
		
		GameRegistry.addSmelting(ItemRegister.itemFyrestoneChunk, new ItemStack(ItemRegister.itemFyrestoneIngot), 0.7F);
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
