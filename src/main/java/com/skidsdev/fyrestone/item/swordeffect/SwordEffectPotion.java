package com.skidsdev.fyrestone.item.swordeffect;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class SwordEffectPotion implements ISwordEffect
{
	private PotionEffect effect;
	
	public SwordEffectPotion(Potion potion, int duration)
	{
		this(potion, duration, 1);
	}
	public SwordEffectPotion(Potion potion, int duration, int potLevel)
	{
		this.effect = new PotionEffect(potion, duration, potLevel);
	}	
	public SwordEffectPotion(PotionEffect effect)
	{
		this.effect = effect;
	}
	
	@Override
	public void ApplyEffect(EntityLivingBase entity)
	{
		entity.addPotionEffect(new PotionEffect(effect.getPotion(), effect.getDuration(), effect.getAmplifier()));
	}
}
