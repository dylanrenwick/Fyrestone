package com.skidsdev.fyrestone.item.swordeffect;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class SwordEffectFire implements ISwordEffectOnHit
{
	private int duration;
	
	public SwordEffectFire(int duration)
	{
		this.duration = duration;
	}
	
	@Override
	public void ApplyEffect(ItemStack stack, EntityLivingBase entity, EntityPlayer player)
	{
		entity.setFire(duration);
	}
}
