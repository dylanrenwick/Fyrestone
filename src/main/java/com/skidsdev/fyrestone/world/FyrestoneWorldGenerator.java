package com.skidsdev.fyrestone.world;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import com.skidsdev.fyrestone.block.BlockFyrestoneOre;
import com.skidsdev.fyrestone.block.ModBlocks;
import com.skidsdev.fyrestone.Config;

public class FyrestoneWorldGenerator implements IWorldGenerator
{
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator iChunkGenerator, IChunkProvider iChunkProvider)
	{
		generateOre(world, random, chunkX * 16, chunkZ * 16);
	}
	
	private void generateOre(World world, Random random, int chunkX, int chunkZ)
	{
		for (int i = 0; i < 2; i++)
		{
			int x = chunkX + random.nextInt(16);
			int y = random.nextInt(48);
			int z = chunkZ + random.nextInt(16);
			
			BlockPos pos = new BlockPos(x, y, z);
			
			new WorldGenMinable(ModBlocks.blockFyrestoneOre.getDefaultState(), 8).generate(world, random, pos);
		}
	}
}
