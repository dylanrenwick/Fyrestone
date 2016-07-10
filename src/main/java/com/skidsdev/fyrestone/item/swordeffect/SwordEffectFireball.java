package com.skidsdev.fyrestone.item.swordeffect;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.item.ItemStack;

public class SwordEffectFireball implements ISwordEffectOnSwing
{
	@Override
	public void ApplyEffect(ItemStack stack, EntityPlayer player)
	{
		if(!player.worldObj.isRemote)
		{
			EntitySmallFireball entitysmallfireball = new EntitySmallFireball(player.worldObj, player.posX, player.posY, player.posZ, player.getLookVec().xCoord, player.getLookVec().yCoord, player.getLookVec().zCoord);
	        entitysmallfireball.posY = player.posY + (double)(player.height / 2.0F) + 0.5D;
	        entitysmallfireball.shootingEntity = player;
	        player.worldObj.spawnEntityInWorld(entitysmallfireball);
	        
	        stack.damageItem(1, player);
			
			if (stack.stackSize < 1) player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
		}
	}
}
