package com.skidsdev.fyrestone.item;

import java.util.List;

import com.skidsdev.fyrestone.Fyrestone;
import com.skidsdev.fyrestone.client.gui.GuiGuideBook;
import com.skidsdev.fyrestone.container.ContainerGuideBook;
import com.skidsdev.fyrestone.utils.IOpenableGUI;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemGuideBook extends BaseItem implements IOpenableGUI
{
	public ItemGuideBook()
	{
		super("itemGuideBook");
	}
	
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World worldIn, EntityPlayer player, EnumHand hand)
	{
		if (!player.isSneaking())
		{
			player.openGui(Fyrestone.instance, 0, worldIn, (int)player.posX, (int)player.posY, (int)player.posZ);
		}
		
		return ActionResult.newResult(EnumActionResult.PASS, stack);
	}
	
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
	{
		tooltip.add("This book is a little scorched around");
		tooltip.add("the edges, but most of the knowledge");
		tooltip.add("it contains seems to be intact.");
	}

	@Override
	public Gui getClientGuiElement(int id, EntityPlayer player, World worldIn, BlockPos pos)
	{
		return new GuiGuideBook(player, player.getHeldItemMainhand());
	}

	@Override
	public Container getServerGuiElement(int id, EntityPlayer player, World worldIn, BlockPos pos)
	{
		return new ContainerGuideBook();
	}
}
