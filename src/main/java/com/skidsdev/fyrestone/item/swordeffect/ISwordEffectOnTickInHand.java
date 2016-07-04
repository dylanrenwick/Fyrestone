package com.skidsdev.fyrestone.item.swordeffect;

import net.minecraft.entity.EntityLivingBase;

public interface ISwordEffectOnTickInHand extends ISwordEffect
{
	public abstract void ApplyEffect(EntityLivingBase entity);
}
