package com.skidsdev.fyrestone.tile;

import java.util.List;

import com.skidsdev.fyrestone.block.BlockRitualCircle;
import com.skidsdev.fyrestone.block.BlockRitualCircle.EnumRitualType;
import com.skidsdev.fyrestone.utils.Helper;
import com.skidsdev.fyrestone.utils.RitualRecipe;
import com.skidsdev.fyrestone.utils.RitualRecipeManager;

import net.minecraft.entity.item.EntityItem;
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
						doMetallurgyMultiblock();
					}
					else if (stacks[0].getItem() == Items.EMERALD
							&& (EnumRitualType)this.getWorld().getBlockState(pos).getValue(BlockRitualCircle.RITUAL_TYPE) == EnumRitualType.ALCHEMY)
					{
						doAlchemyMultiblock();
					}
				}
			}
		}
	}
	
	private void doMetallurgyMultiblock()
	{
		
	}
	
	private void doAlchemyMultiblock()
	{
		
	}
}
