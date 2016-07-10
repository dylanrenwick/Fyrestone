package com.skidsdev.fyrestone.item.swordeffect;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface ISwordEffectOnSwing extends ISwordEffect
{
	public abstract void ApplyEffect(ItemStack stack, EntityPlayer player);
}
