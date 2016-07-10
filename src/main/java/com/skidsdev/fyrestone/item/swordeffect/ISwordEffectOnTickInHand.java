package com.skidsdev.fyrestone.item.swordeffect;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface ISwordEffectOnTickInHand extends ISwordEffect
{
	public abstract void ApplyEffect(ItemStack stack, EntityLivingBase entity);
}
