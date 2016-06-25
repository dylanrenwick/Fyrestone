package com.skidsdev.fyrestone.item;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class ItemRegister 
{	
	public static Item itemFyrestoneShard;
	public static Item itemFyrestoneIngot;
	public static Item itemFyrestoneSword;
	
	public static ToolMaterial FYRESTONE = EnumHelper.addToolMaterial("FYRESTONE", 2, 500, 10.0F, 2.0F, 18);
	
	public static final void createItems()
	{
		GameRegistry.register(itemFyrestoneShard = new ItemFyrestoneShard());
		GameRegistry.register(itemFyrestoneIngot = new BaseItem("itemFyrestoneIngot"));
		GameRegistry.register(itemFyrestoneSword = new ItemFyrestoneSword("itemFyrestoneSword", FYRESTONE));
	}
}