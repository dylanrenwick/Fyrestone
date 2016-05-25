package com.skidsdev.fyrestone.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ItemFyrestoneSword extends ItemSword
{
	public ItemFyrestoneSword(String regName, ToolMaterial material)
	{
		super(material);
		this.setRegistryName(regName);
		this.setUnlocalizedName(this.getRegistryName().toString());
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		entity.setFire(5);
		
		return false;
	}
}
