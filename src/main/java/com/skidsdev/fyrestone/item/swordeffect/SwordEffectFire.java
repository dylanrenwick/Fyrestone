package com.skidsdev.fyrestone.item.swordeffect;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class SwordEffectFire implements ISwordEffectOnHit
{
	private int duration;
	
	public SwordEffectFire(int duration)
	{
		this.duration = duration;
	}
	
	@Override
	public void ApplyEffect(EntityLivingBase entity, EntityPlayer player)
	{
		entity.setFire(duration);
	}
}
