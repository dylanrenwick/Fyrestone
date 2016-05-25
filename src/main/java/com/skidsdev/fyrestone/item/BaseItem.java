package com.skidsdev.fyrestone.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class BaseItem extends Item
{
	
	public BaseItem(String regName)
	{
		super();
		
		this.setRegistryName(regName);
		this.setUnlocalizedName(this.getRegistryName().toString());
		this.setCreativeTab(CreativeTabs.tabMaterials);
	}
}
