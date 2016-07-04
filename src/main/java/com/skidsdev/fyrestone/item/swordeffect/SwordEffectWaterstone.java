package com.skidsdev.fyrestone.item.swordeffect;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

public class SwordEffectWaterstone implements ISwordEffectOnHit
{
	private int damageBonus;
	
	public SwordEffectWaterstone(int dmgBonus)
	{
		damageBonus = dmgBonus;
	}

	@Override
	public void ApplyEffect(EntityLivingBase entity, EntityPlayer player) {
		if(entity instanceof EntityEnderman || entity instanceof EntityBlaze)
		{
			entity.attackEntityFrom(DamageSource.causePlayerDamage(player), damageBonus);
		}
		
		if(entity.isBurning()) entity.extinguish();
	}
}
