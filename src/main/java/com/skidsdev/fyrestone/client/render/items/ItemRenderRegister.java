package com.skidsdev.fyrestone.client.render.items;

import com.skidsdev.fyrestone.item.ItemBaseShard;
import com.skidsdev.fyrestone.item.ItemBaseShard.EnumShardType;
import com.skidsdev.fyrestone.item.ItemRegister;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ItemRenderRegister
{
	public static void registerItemRenderer()
	{
		reg(ItemRegister.itemFyrestoneIngot);
		reg(ItemRegister.itemEarthstoneIngot);
		reg(ItemRegister.itemMysticalOrb);
		reg(ItemRegister.itemPlagueEssence);
		reg(ItemRegister.itemPlagueCore);
		reg(ItemRegister.itemFyrestoneSword);
		reg(ItemRegister.itemEarthstoneSword);
		reg(ItemRegister.itemPlagueblade);
		reg(ItemRegister.itemFyrestoneCatalyst);
		
		for (EnumShardType type : EnumShardType.values())
		{
			String itemModelName = ItemRegister.itemShard.getRegistryName().toString() + "_" + type.getName();
			reg(ItemRegister.itemShard, itemModelName, type.ordinal());
		}
	}
	
	private static void reg(Item item) { reg(item, 0); }
	private static void reg(Item item, int meta) { reg(item, item.getRegistryName().toString(), meta); }
	private static void reg(Item item, String regName, int meta)
	{
		ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(regName, "inventory");
		ModelLoader.setCustomModelResourceLocation(item, meta, itemModelResourceLocation);
	}
}
