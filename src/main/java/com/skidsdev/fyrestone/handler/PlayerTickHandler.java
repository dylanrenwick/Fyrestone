package com.skidsdev.fyrestone.handler;

import java.util.List;

import com.skidsdev.fyrestone.item.ItemBaseSword;
import com.skidsdev.fyrestone.item.ItemBaseSword.EnumSwordType;
import com.skidsdev.fyrestone.utils.ItemNBTHelper;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerTickHandler
{
	@SubscribeEvent
	public void tooltip(ItemTooltipEvent e)
	{
		if (e.getItemStack().getItem() instanceof ItemBaseSword)
		{
			addSwordInfo(e);
		}
	}
	
	private void addSwordInfo(ItemTooltipEvent e)
	{
		ItemStack stack = e.getItemStack();
		EnumSwordType type = EnumSwordType.values()[ItemNBTHelper.getInt(stack, "sword_type", 0)];
		List<String> tooltip = e.getToolTip();
		
		for(int i = 0; i < tooltip.size(); i++)
		{
			String tooltipLine = tooltip.get(i);
			
			if (tooltipLine.endsWith(" Attack Speed"))
			{
				String newTooltipLine = " " + type.getAttackSpeed() + " Attack Speed";
				tooltip.remove(i);
				tooltip.add(i, newTooltipLine);
			}
			else if (tooltipLine.endsWith(" Attack Damage"))
			{
				String newTooltipLine = " " + (int)type.getAttackDamage() + " Attack Damage";
				tooltip.remove(i);
				tooltip.add(i, newTooltipLine);
			}
		}
	}
}
