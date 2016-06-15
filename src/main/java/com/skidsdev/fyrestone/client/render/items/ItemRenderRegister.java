package com.skidsdev.fyrestone.client.render.items;

import com.skidsdev.fyrestone.item.ItemRegister;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ItemRenderRegister
{
	public static void registerItemRenderer()
	{
		reg(ItemRegister.itemFyrestoneChunk);
		reg(ItemRegister.itemFyrestoneIngot);
		reg(ItemRegister.itemFyrestoneSword);
	}
	
	public static void reg(Item item)
	{
		ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(item.getRegistryName().toString(), "inventory");
		ModelLoader.setCustomModelResourceLocation(item, 0, itemModelResourceLocation);
	}
}
