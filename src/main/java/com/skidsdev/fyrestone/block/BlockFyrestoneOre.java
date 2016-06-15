package com.skidsdev.fyrestone.block;

import java.util.Random;

import com.skidsdev.fyrestone.item.ItemRegister;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class BlockFyrestoneOre extends BlockBase
{
	public BlockFyrestoneOre()
	{
		super("blockFyrestoneOre", Material.ROCK, 2.0f, 10.0f);
		this.setLightLevel(0.4f);
		this.setHarvestLevel("pickaxe", 2);
	}
	
	@Override
	public Item getItemDropped(IBlockState blockstate, Random random, int fortune)
	{
		return ItemRegister.itemFyrestoneChunk;
	}
	
	@Override
	public int quantityDropped(IBlockState blockstate, int fortune, Random random)
	{
		return 1 + random.nextInt(3 - 1 + fortune + 1);
	}
}
