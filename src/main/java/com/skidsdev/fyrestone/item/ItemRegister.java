package com.skidsdev.fyrestone.item;

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
	public static Item itemSword;
	public static Item itemFyrestoneCatalyst;
	
	public static ToolMaterial FYRESTONE = EnumHelper.addToolMaterial("FYRESTONE", 2, 500, 10.0F, 2.0F, 18);
	
	public static final void createItems()
	{
		GameRegistry.register(itemShard = new ItemBaseShard());
		GameRegistry.register(itemFyrestoneIngot = new BaseItem("itemFyrestoneIngot"));
		GameRegistry.register(itemEarthstoneIngot = new BaseItem("itemEarthstoneIngot"));
		GameRegistry.register(itemMysticalOrb = new BaseItem("itemMysticalOrb"));
		GameRegistry.register(itemPlagueEssence = new BaseItem("itemPlagueEssence"));
		GameRegistry.register(itemPlagueCore = new BaseItem("itemPlagueCore"));
		GameRegistry.register(itemFyrestoneCatalyst = new BaseItem("itemFyrestoneCatalyst"));
		GameRegistry.register(itemSword = new ItemBaseSword(FYRESTONE, "itemSword"));
		
		OreDictionary.registerOre("ingotFyrestone", itemFyrestoneIngot);
		OreDictionary.registerOre("ingotEarthstone", itemEarthstoneIngot);
		OreDictionary.registerOre("itemMysticalOrb", itemMysticalOrb);
		OreDictionary.registerOre("itemFyrestoneCatalyst", itemFyrestoneCatalyst);
	}
}