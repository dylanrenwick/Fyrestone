package com.skidsdev.fyrestone.tile;

import java.util.List;

import com.skidsdev.fyrestone.block.BlockRegister;
import com.skidsdev.fyrestone.block.BlockRitualCircle;
import com.skidsdev.fyrestone.block.BlockRitualCircle.EnumRitualType;
import com.skidsdev.fyrestone.item.ItemRegister;
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
	public TileEntityRitualCircle()
	{
		super();
	}

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
							for(EntityItem entity : entities) { entity.setDead(); }
							
							for(int z = -2; z < 3; z+=4)
							{
								for(int x = -2; x < 3; x+=4)
								{
									this.getWorld().setBlockState(pos.add(x, 1, z), Blocks.AIR.getDefaultState());
								}
							}
							
							EntityItem entityItem = new EntityItem(this.getWorld(), pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, new ItemStack(ItemRegister.itemFyrestoneCatalyst, 1));
							this.getWorld().spawnEntityInWorld(entityItem);
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
		for(int x = -2; x < 3; x+=4)
		{
			for(int z = -2; z < 3; z+=4)
			{
				for(int y = -1; y < 1; y++)
				{
					Block block = this.getWorld().getBlockState(pos.add(x, y, z)).getBlock();
					if (block != Blocks.IRON_BLOCK) return false;
				}
				Block block = this.getWorld().getBlockState(pos.add(x, 1, z)).getBlock();
				if (block != BlockRegister.blockFyrestoneBlock) return false;
			}
		}
		
		return true;
	}
	
	private boolean doAlchemyMultiblock()
	{
		return false;
	}
}
