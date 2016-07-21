package com.skidsdev.fyrestone.utils;

import java.util.ArrayList;
import java.util.List;

import com.skidsdev.fyrestone.client.gui.book.BookEntryIndex;
import com.skidsdev.fyrestone.client.gui.book.BookPageIndex;
import com.skidsdev.fyrestone.client.gui.book.IBookPage;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class BookNBTHelper
{
	public static final String PAGE_TAG = "Page";
	
	public static final String METALLURGY_TAG = "MetallurgyLevel";
	public static final String ALCHEMY_TAG    = "AlchemyLevel";
	public static final String IMBUING_TAG    = "ImbuingLevel";
	
	public static final String NETHER_TIER  = "NetherTier";
	public static final String ENDER_TIER   = "EnderTier";
	public static final String BALANCE_TIER = "BalanceTier";
	
	private static final ResourceLocation ICON_SHEET = new ResourceLocation(VersionInfo.ModId + ":textures/gui/bookIcons.png");
	
	public static void initializeNBT(ItemStack stack)
	{
		NBTTagCompound tag = new NBTTagCompound();
		
		tag.setInteger(PAGE_TAG, 0);
		
		tag.setInteger(METALLURGY_TAG, 1);
		tag.setInteger(ALCHEMY_TAG, 0);
		tag.setInteger(IMBUING_TAG, 0);
		
		tag.setBoolean(NETHER_TIER, false);
		tag.setBoolean(ENDER_TIER, false);
		tag.setBoolean(BALANCE_TIER, false);
		
		ItemNBTHelper.injectNBT(stack, tag);
	}
	
	public static List<IBookPage> getPagesFromBook(ItemStack stack)
	{
		List<IBookPage> pages = new ArrayList<IBookPage>();
		
		int currentPage = ItemNBTHelper.getInt(stack, PAGE_TAG, 0);
		
		int metallurgy = ItemNBTHelper.getInt(stack, METALLURGY_TAG, 0);
		int alchemy    = ItemNBTHelper.getInt(stack, ALCHEMY_TAG, 0);
		int imbuing    = ItemNBTHelper.getInt(stack, IMBUING_TAG, 0);
		
		boolean nether  = ItemNBTHelper.getBoolean(stack, NETHER_TIER, false);
		boolean ender   = ItemNBTHelper.getBoolean(stack, ENDER_TIER, false);
		boolean balance = ItemNBTHelper.getBoolean(stack, BALANCE_TIER, false);
		
		pages.add(constructIndex(metallurgy, alchemy, imbuing, nether, ender, balance));
		
		return pages;
	}
	
	public static int getBookPage(ItemStack stack)
	{
		return ItemNBTHelper.getInt(stack, PAGE_TAG, 0);
	}
	
	public static void savePage(ItemStack stack, int page)
	{
		ItemNBTHelper.setInt(stack, PAGE_TAG, page);
	}
	
	private static IBookPage constructIndex(int metallurgy, int alchemy, int imbuing, boolean nether, boolean ender, boolean balance)
	{
		BookPageIndex indexPage = new BookPageIndex();
		
		indexPage.addEntry(new BookEntryIndex(ICON_SHEET, 0, 0, "Getting Started", 1));
		indexPage.addEntry(new BookEntryIndex(ICON_SHEET, 16, 0, "Metallurgy", 2));
		indexPage.addEntry(new BookEntryIndex(ICON_SHEET, 32, 0, "Alchemy", 2));
		indexPage.addEntry(new BookEntryIndex(ICON_SHEET, 48, 0, "Imbuing", 2));
		
		return indexPage;
	}
}
