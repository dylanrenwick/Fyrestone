package com.skidsdev.fyrestone.block;

import com.skidsdev.fyrestone.Fyrestone;
import com.skidsdev.fyrestone.GuiHandlerRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class BlockRegister 
{
	public static Block blockFyrestoneOre;
	public static Block blockFyrestoneBlock;
	public static Block blockFramedFurnace;
	public static ItemBlock itemBlockFyrestoneOre;
	public static ItemBlock itemBlockFyrestoneBlock;
	public static ItemBlock itemBlockFramedFurnace;
	
	public static void createBlocks()
	{
		blockFyrestoneOre = new BlockFyrestoneOre();
		itemBlockFyrestoneOre = new ItemBlock(blockFyrestoneOre);
		itemBlockFyrestoneOre.setRegistryName("blockFyrestoneOre");
		GameRegistry.register(blockFyrestoneOre);
		GameRegistry.register(itemBlockFyrestoneOre);
		
		blockFyrestoneBlock = new BlockBase("blockFyrestoneBlock", Material.IRON, 2.5f, 30.0f).setLightLevel(0.8f);
		itemBlockFyrestoneBlock = new ItemBlock(blockFyrestoneBlock);
		itemBlockFyrestoneBlock.setRegistryName("blockFyrestoneBlock");
		GameRegistry.register(blockFyrestoneBlock);
		GameRegistry.register(itemBlockFyrestoneBlock);
	}
}
