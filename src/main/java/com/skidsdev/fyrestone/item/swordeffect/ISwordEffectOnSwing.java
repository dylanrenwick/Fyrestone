package com.skidsdev.fyrestone.item.swordeffect;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public interface ISwordEffectOnSwing extends ISwordEffect
{
	public abstract void ApplyEffect(EntityPlayer player);
}
