package com.skidsdev.fyrestone.item;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class ItemRegister 
{	
	public static Item itemShard;
	public static Item itemFyrestoneIngot;
	public static Item itemEarthstoneIngot;
	public static Item itemMysticalOrb;
	public static Item itemFyrestoneSword;
	public static Item itemFyrestoneCatalyst;
	
	public static ToolMaterial FYRESTONE = EnumHelper.addToolMaterial("FYRESTONE", 2, 500, 10.0F, 2.0F, 18);
	public static ToolMaterial EARTHSTONE = EnumHelper.addToolMaterial("EARTHSTONE", 3, 2500, 14.0F, 1.4F, 22);
	
	public static final void createItems()
	{
		GameRegistry.register(itemShard = new ItemBaseShard());
		GameRegistry.register(itemFyrestoneIngot = new BaseItem("itemFyrestoneIngot"));
		GameRegistry.register(itemEarthstoneIngot = new BaseItem("itemEarthstoneIngot"));
		GameRegistry.register(itemMysticalOrb = new BaseItem("itemMysticalOrb"));
		GameRegistry.register(itemFyrestoneCatalyst = new BaseItem("itemFyrestoneCatalyst"));
		GameRegistry.register(itemFyrestoneSword = new ItemFyrestoneSword("itemFyrestoneSword", FYRESTONE));
	}
}