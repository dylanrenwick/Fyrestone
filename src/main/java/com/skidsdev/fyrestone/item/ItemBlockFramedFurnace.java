package com.skidsdev.fyrestone.item;

import java.util.List;

import com.skidsdev.fyrestone.block.EnumFrameType;
import com.skidsdev.fyrestone.utils.ItemNBTHelper;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockFramedFurnace extends ItemBlock {

	public ItemBlockFramedFurnace(Block block) {
		super(block);
		this.setHasSubtypes(true);
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List subItems)
	{
		for (int i = 0; i < EnumFrameType.values().length; i++)
		{
			ItemStack itemStack = new ItemStack(itemIn, 1);
			ItemNBTHelper.setInt(itemStack, "frameType", i);
			
			subItems.add(itemStack);
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		EnumFrameType frameType = EnumFrameType.values()[ItemNBTHelper.getInt(stack, "frameType", 0)];
		
		return super.getUnlocalizedName() + "." + frameType.getName();
	}

}
