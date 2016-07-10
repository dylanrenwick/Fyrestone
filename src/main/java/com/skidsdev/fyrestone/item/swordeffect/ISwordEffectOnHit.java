package com.skidsdev.fyrestone.item.swordeffect;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface ISwordEffectOnHit extends ISwordEffect
{
	public abstract void ApplyEffect(ItemStack stack, EntityLivingBase entity, EntityPlayer player);
}
