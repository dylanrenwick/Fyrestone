package com.skidsdev.fyrestone.client.render.blocks;

import com.skidsdev.fyrestone.block.BlockRegister;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

public final class BlockRenderRegister 
{
	public static void registerBlockRenderer()
	{
		reg(BlockRegister.blockFyrestoneOre);
		reg(BlockRegister.blockFyrestoneBlock);
		reg(BlockRegister.blockFramedFurnace);
	}
	
	public static void reg(Block block)
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName().toString(), "inventory"));
	}
}
