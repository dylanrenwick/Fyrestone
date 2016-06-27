package com.skidsdev.fyrestone.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LootHandler
{
	private static LootHandler INSTANCE = new LootHandler();
	
	public static void register()
	{
		MinecraftForge.EVENT_BUS.register(INSTANCE);
	}
	
	@SubscribeEvent
	public void onLootTableLoad(LootTableLoadEvent evt) 
	{
		LootTable table = evt.getTable();
		
		FyrestonePool lp = new FyrestonePool();
		
		if (evt.getName() == LootTableList.CHESTS_SIMPLE_DUNGEON)
		{
			//lp.addItem(createLootEntry(null, 0, 1, 1, 0.25));
		}
	}
	
	private LootPoolEntry createLootEntry(Item item, int meta, int minSize, int maxSize, double chance)
	{
		return new LootPoolEntry(new ItemStack(item, 1, meta), chance, minSize, maxSize);
	}
	
	private static class LootPoolEntry
	{
		ItemStack item;
		double chance;
		int minSize;
		int maxSize;
		
		public LootPoolEntry(ItemStack item, double chance)	{ this(item, chance, 1, 1);	}
		public LootPoolEntry(ItemStack item, double chance, int minSize, int maxSize)
		{
			this.item = item;
			this.chance = chance;
			this.minSize = minSize;
			this.maxSize = maxSize;
		}
		
		public ItemStack createStack(Random random)
		{
			int size = minSize;
			
			if (maxSize > minSize)
			{
				size += random.nextInt(maxSize - minSize + 1);
			}
			
			ItemStack result = item.copy();
			result.stackSize = size;
			return result;
		}
	}
	
	private static class FyrestonePool extends LootPool 
	{
		private final List<LootPoolEntry> items = new ArrayList<LootPoolEntry>();
		
		public FyrestonePool()
		{
			super(new LootEntry[0], new LootCondition[0], new RandomValueRange(0, 0), new RandomValueRange(0, 0), VersionInfo.ModName);
		}
		
		public void addItem(LootPoolEntry entry)
		{
			items.add(entry);
		}
		
		public void generateLoot(Collection<ItemStack> stacks, Random random, LootContext contect)
		{
			for (LootPoolEntry entry : items)
			{
				if (random.nextDouble() < entry.chance)
				{
					stacks.add(entry.createStack(random));
				}
			}
		}
	}
}
