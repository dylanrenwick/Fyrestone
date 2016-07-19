package com.skidsdev.fyrestone.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public final class ItemRegister 
{	
	public static Item itemShard;
	public static Item itemFyrestoneIngot;
	public static Item itemEarthstoneIngot;
	public static Item itemMysticalOrb;
	public static Item itemPlagueEssence;
	public static Item itemPlagueCore;
	public static Item itemBlazingCore;
	public static Item itemFyrestoneCatalyst;
	public static Item itemGuideBook;
	public static Item itemSword;
	
	public static ToolMaterial FYRESTONE = EnumHelper.addToolMaterial("FYRESTONE", 2, 500, 10.0F, 2.0F, 18);
	
	public static List<Item> registeredItems;
	
	public static final void createItems()
	{
		registeredItems = new ArrayList<Item>();
		
		GameRegistry.register(itemShard = new ItemBaseShard());
		
		itemFyrestoneIngot    = registerItem(new BaseItem("itemFyrestoneIngot"), "ingotFyrestone");
		itemEarthstoneIngot   = registerItem(new BaseItem("itemEarthstoneIngot"), "ingotEarthstone");
		itemMysticalOrb       = registerItem(new BaseItem("itemMysticalOrb"));
		itemPlagueEssence     = registerItem(new BaseItem("itemPlagueEssence"));
		itemPlagueCore        = registerItem(new BaseItem("itemPlagueCore"));
		itemBlazingCore       = registerItem(new BaseItem("itemBlazingCore"));
		itemFyrestoneCatalyst = registerItem(new BaseItem("itemFyrestoneCatalyst"));
		itemGuideBook         = registerItem(new ItemGuideBook());
		itemSword             = registerItem(new ItemBaseSword(FYRESTONE, "itemSword"));
	}
	
	private static Item registerItem(Item item, String oreDict)
	{
		OreDictionary.registerOre(oreDict, registerItem(item));
		
		return item;
	}
	private static Item registerItem(Item item)
	{
		GameRegistry.register(item);
		registeredItems.add(item);
		
		return item;
	}
}