package com.skidsdev.fyrestone.item.swordeffect;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public interface ISwordEffectOnHit extends ISwordEffect
{
	public abstract void ApplyEffect(EntityLivingBase entity, EntityPlayer player);
}
