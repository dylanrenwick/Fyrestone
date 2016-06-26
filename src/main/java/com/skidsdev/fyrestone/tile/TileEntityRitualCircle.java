package com.skidsdev.fyrestone.tile;

import java.util.List;

import com.skidsdev.fyrestone.block.BlockRegister;
import com.skidsdev.fyrestone.block.BlockRitualCircle;
import com.skidsdev.fyrestone.block.BlockRitualCircle.EnumRitualType;
import com.skidsdev.fyrestone.utils.RitualRecipe;
import com.skidsdev.fyrestone.utils.RitualRecipeManager;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityRitualCircle extends TileEntity implements ITickable
{

	@Override
	public void update() {
		if (this.getWorld().getWorldTime() % 20 != 0 || this.getWorld().isRemote) return;
		
		List<EntityItem> entities = this.getWorld().getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1));
		if (entities.size() > 0)
		{
			ItemStack[] stacks = new ItemStack[entities.size()];
			for (int i = 0; i < entities.size(); i++)
			{
				stacks[i] = entities.get(i).getEntityItem();
			}
			
			RitualRecipe recipe = RitualRecipeManager.GetRecipeFromInputs((EnumRitualType)this.getWorld().getBlockState(pos).getValue(BlockRitualCircle.RITUAL_TYPE), stacks);
			
			if (recipe != null)
			{
				for(EntityItem entity : entities) { entity.setDead(); }
				EntityItem entityItem = new EntityItem(this.getWorld(), pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, recipe.getOutput());
				this.getWorld().spawnEntityInWorld(entityItem);
			}
			else
			{
				if (stacks.length == 1)
				{
					if (stacks[0].getItem() == Items.DIAMOND 
							&& (EnumRitualType)this.getWorld().getBlockState(pos).getValue(BlockRitualCircle.RITUAL_TYPE) == EnumRitualType.METALLURGY)
					{
						if (doMetallurgyMultiblock())
						{
							
						}
					}
					else if (stacks[0].getItem() == Items.EMERALD
							&& (EnumRitualType)this.getWorld().getBlockState(pos).getValue(BlockRitualCircle.RITUAL_TYPE) == EnumRitualType.ALCHEMY)
					{
						if (doAlchemyMultiblock())
						{
							
						}
					}
				}
			}
		}
	}
	
	private boolean doMetallurgyMultiblock()
	{
		for(int y = 0; y < 2; y++)
		{
			for(int x = -1; x < 2; x+=2)
			{
				for(int z = -1; z < 2; z+=2)
				{
					Block block = this.getWorld().getBlockState(pos.add(x, y, z)).getBlock();
					if (block != Blocks.IRON_BLOCK) return false;
				}
			}
		}
		
		Block block = this.getWorld().getBlockState(pos.add(0, 2, 0)).getBlock();
		if (block != BlockRegister.blockFyrestoneBlock) return false;
		
		return true;
	}
	
	private boolean doAlchemyMultiblock()
	{
		return false;
	}
}
