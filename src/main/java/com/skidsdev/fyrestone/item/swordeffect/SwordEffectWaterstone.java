package com.skidsdev.fyrestone.item.swordeffect;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.util.DamageSource;

public class SwordEffectWaterstone implements ISwordEffect
{
	public SwordEffectWaterstone() { }

	@Override
	public void ApplyEffect(EntityLivingBase entity) {
		if(entity instanceof EntityEnderman || entity instanceof EntityBlaze)
		{
			entity.attackEntityFrom(new DamageSource(entity.getAttackingEntity().getName()), 8);
		}
	}

}
