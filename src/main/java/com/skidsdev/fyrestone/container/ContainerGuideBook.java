package com.skidsdev.fyrestone.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerGuideBook extends Container
{
	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return true;
	}
}
