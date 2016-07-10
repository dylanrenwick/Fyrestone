package com.skidsdev.fyrestone;

import java.io.File;

import com.skidsdev.fyrestone.block.BlockFyrestoneOre;
import com.skidsdev.fyrestone.block.BlockRegister;
import com.skidsdev.fyrestone.item.ItemBaseShard;
import com.skidsdev.fyrestone.item.ItemBaseShard.EnumShardType;
import com.skidsdev.fyrestone.item.ItemBaseSword;
import com.skidsdev.fyrestone.item.ItemBaseSword.EnumSwordType;
import com.skidsdev.fyrestone.item.ItemRegister;
import com.skidsdev.fyrestone.utils.RitualRecipe;
import com.skidsdev.fyrestone.utils.RitualRecipeManager;
import com.skidsdev.fyrestone.utils.VersionInfo;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;

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
		RecipeSorter.register(VersionInfo.ModId + ":ritualRecipe", RitualRecipeManager.class, RecipeSorter.Category.SHAPELESS, "");
		
		GameRegistry.addRecipe(new ItemStack(BlockRegister.blockFyrestoneBlock), new Object[] {"###", "###", "###", '#', ItemRegister.itemFyrestoneIngot});
		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegister.itemFyrestoneIngot, 9), BlockRegister.blockFyrestoneBlock);
		
		//Metallurgy Rituals
		RitualRecipeManager.RegisterRecipe(new RitualRecipe(new ItemStack(ItemRegister.itemFyrestoneIngot), 0, ItemBaseShard.getShardStack(1, EnumShardType.FYRESTONE), new ItemStack(Items.IRON_INGOT)));
		RitualRecipeManager.RegisterRecipe(new RitualRecipe(ItemBaseSword.getSwordStack(EnumSwordType.FYRESTONE_SWORD), 0, new ItemStack(ItemRegister.itemFyrestoneIngot, 2), new ItemStack(Items.IRON_SWORD)));
		RitualRecipeManager.RegisterRecipe(new RitualRecipe(ItemBaseShard.getShardStack(1, EnumShardType.WATERSTONE), 0, new ItemStack(ItemRegister.itemFyrestoneCatalyst), ItemBaseShard.getShardStack(1, EnumShardType.FYRESTONE), new ItemStack(Items.WATER_BUCKET), new ItemStack(Items.DYE, 1, EnumDyeColor.BLUE.getDyeDamage())));
		RitualRecipeManager.RegisterRecipe(new RitualRecipe(new ItemStack(ItemRegister.itemEarthstoneIngot), 0, ItemBaseShard.getShardStack(1, EnumShardType.EARTHSTONE), new ItemStack(Items.IRON_INGOT)));
		RitualRecipeManager.RegisterRecipe(new RitualRecipe(ItemBaseSword.getSwordStack(EnumSwordType.EARTHSTONE_SWORD), 0, new ItemStack(ItemRegister.itemEarthstoneIngot, 2), new ItemStack(ItemRegister.itemMysticalOrb), new ItemStack(Items.STICK)));
		RitualRecipeManager.RegisterRecipe(new RitualRecipe(ItemBaseSword.getSwordStack(EnumSwordType.WATERSTONE_SWORD), 0, new ItemStack(Items.IRON_SWORD), new ItemStack(Items.WATER_BUCKET), ItemBaseShard.getShardStack(2, EnumShardType.WATERSTONE)));
		RitualRecipeManager.RegisterRecipe(new RitualRecipe(ItemBaseSword.getSwordStack(EnumSwordType.AIRSTONE_SWORD), 0, new ItemStack(Items.NETHER_STAR), ItemBaseShard.getShardStack(2, EnumShardType.AIRSTONE), new ItemStack(Items.STICK)));
		
		//Alchemy Rituals
		RitualRecipeManager.RegisterRecipe(new RitualRecipe(ItemBaseShard.getShardStack(1, EnumShardType.WATERSTONE), 1, ItemBaseShard.getShardStack(1, EnumShardType.FYRESTONE), new ItemStack(Items.DYE, 1, EnumDyeColor.BLUE.getDyeDamage())));
		RitualRecipeManager.RegisterRecipe(new RitualRecipe(ItemBaseShard.getShardStack(1, EnumShardType.EARTHSTONE), 1, ItemBaseShard.getShardStack(1, EnumShardType.FYRESTONE), new ItemStack(Blocks.DIRT), new ItemStack(ItemRegister.itemFyrestoneCatalyst)));
		RitualRecipeManager.RegisterRecipe(new RitualRecipe(new ItemStack(ItemRegister.itemPlagueEssence), 1, ItemBaseShard.getShardStack(1, EnumShardType.WATERSTONE), new ItemStack(Items.ROTTEN_FLESH, 2), new ItemStack(Items.GLASS_BOTTLE)));
		RitualRecipeManager.RegisterRecipe(new RitualRecipe(ItemBaseShard.getShardStack(1, EnumShardType.ORDERSTONE), 1, ItemBaseShard.getShardStack(1, EnumShardType.FYRESTONE), ItemBaseShard.getShardStack(1, EnumShardType.WATERSTONE), new ItemStack(ItemRegister.itemFyrestoneCatalyst), new ItemStack(Items.NETHER_STAR)));
		RitualRecipeManager.RegisterRecipe(new RitualRecipe(new ItemStack(Items.LEATHER), 1, new ItemStack(Items.ROTTEN_FLESH, 2)));
		
		//Imbuing Rituals
		RitualRecipeManager.RegisterRecipe(new RitualRecipe(ItemBaseShard.getShardStack(1, EnumShardType.EARTHSTONE), 2, ItemBaseShard.getShardStack(1, EnumShardType.FYRESTONE), new ItemStack(Blocks.DIRT)));
		RitualRecipeManager.RegisterRecipe(new RitualRecipe(new ItemStack(ItemRegister.itemPlagueCore), 2, new ItemStack(ItemRegister.itemMysticalOrb), new ItemStack(ItemRegister.itemPlagueEssence, 2)));
		RitualRecipeManager.RegisterRecipe(new RitualRecipe(ItemBaseSword.getSwordStack(EnumSwordType.PLAGUEBLADE), 2, new ItemStack(ItemRegister.itemPlagueCore), ItemBaseSword.getSwordStack(EnumSwordType.EARTHSTONE_SWORD)));
		RitualRecipeManager.RegisterRecipe(new RitualRecipe(ItemBaseSword.getSwordStack(EnumSwordType.FLAMEVENOM), 2, new ItemStack(ItemRegister.itemPlagueCore), ItemBaseSword.getSwordStack(EnumSwordType.FYRESTONE_SWORD)));
		RitualRecipeManager.RegisterRecipe(new RitualRecipe(ItemBaseShard.getShardStack(1, EnumShardType.BRIMSTONE), 2, ItemBaseShard.getShardStack(1, EnumShardType.FYRESTONE), new ItemStack(Items.BLAZE_POWDER, 2), ItemBaseShard.getShardStack(1, EnumShardType.CHAOSSTONE)));
		RitualRecipeManager.RegisterRecipe(new RitualRecipe(new ItemStack(ItemRegister.itemBlazingCore), 2, new ItemStack(ItemRegister.itemMysticalOrb), ItemBaseShard.getShardStack(2, EnumShardType.BRIMSTONE)));
		RitualRecipeManager.RegisterRecipe(new RitualRecipe(ItemBaseSword.getSwordStack(EnumSwordType.BRIMSTONE_SWORD), 2, new ItemStack(ItemRegister.itemBlazingCore), ItemBaseSword.getSwordStack(EnumSwordType.FYRESTONE_SWORD)));
	
		//Balance Rituals
		RitualRecipeManager.RegisterRecipe(new RitualRecipe(ItemBaseShard.getShardStack(1, EnumShardType.AIRSTONE), 3, ItemBaseShard.getShardStack(1, EnumShardType.EARTHSTONE)));
		RitualRecipeManager.RegisterRecipe(new RitualRecipe(ItemBaseShard.getShardStack(1, EnumShardType.EARTHSTONE), 3, ItemBaseShard.getShardStack(1, EnumShardType.AIRSTONE)));
		RitualRecipeManager.RegisterRecipe(new RitualRecipe(ItemBaseShard.getShardStack(1, EnumShardType.WATERSTONE), 3, ItemBaseShard.getShardStack(1, EnumShardType.FYRESTONE)));
		RitualRecipeManager.RegisterRecipe(new RitualRecipe(ItemBaseShard.getShardStack(1, EnumShardType.FYRESTONE), 3, ItemBaseShard.getShardStack(1, EnumShardType.WATERSTONE)));
		RitualRecipeManager.RegisterRecipe(new RitualRecipe(ItemBaseShard.getShardStack(1, EnumShardType.CHAOSSTONE), 3, ItemBaseShard.getShardStack(1, EnumShardType.ORDERSTONE)));
		RitualRecipeManager.RegisterRecipe(new RitualRecipe(ItemBaseShard.getShardStack(1, EnumShardType.ORDERSTONE), 3, ItemBaseShard.getShardStack(1, EnumShardType.CHAOSSTONE)));
		RitualRecipeManager.RegisterRecipe(new RitualRecipe(ItemBaseShard.getShardStack(1, EnumShardType.ORDERSTONE), 3, new ItemStack(ItemRegister.itemFyrestoneCatalyst)));
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