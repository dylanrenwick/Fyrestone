package com.skidsdev.fyrestone.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class ItemEarthstoneSword extends ItemSword
{
	public ItemEarthstoneSword(String regName, ToolMaterial material)
	{
		super(material);
		this.setRegistryName(regName);
		this.setUnlocalizedName(this.getRegistryName().toString());
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		if (entity instanceof EntityLivingBase)
		{
			EntityLivingBase livingEntity = (EntityLivingBase)entity;
			livingEntity.addPotionEffect(new PotionEffect(Potion.getPotionById(2), 80, 2));
		}
		
		return false;
	}
}
