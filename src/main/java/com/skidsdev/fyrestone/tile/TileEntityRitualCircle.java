package com.skidsdev.fyrestone.tile;

import java.util.List;

import com.skidsdev.fyrestone.utils.Helper;
import com.skidsdev.fyrestone.utils.RitualRecipe;
import com.skidsdev.fyrestone.utils.RitualRecipeManager;

import net.minecraft.entity.item.EntityItem;
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
			
			RitualRecipe recipe = RitualRecipeManager.GetRecipeFromInputs(stacks);
			
			if (recipe != null)
			{
				for(EntityItem entity : entities) { entity.setDead(); }
				Helper.spawnEntityItem(this.getWorld(), recipe.getOutput(), pos.getX(), pos.getY(), pos.getZ());
				System.out.println("Recipe match!");
			}
			else System.out.println("No Recipe match");
		}
		else System.out.println("No entities found");
	}
}
