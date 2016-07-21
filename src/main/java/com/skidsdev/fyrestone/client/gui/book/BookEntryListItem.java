package com.skidsdev.fyrestone.client.gui.book;

import java.awt.Color;
import java.util.List;

import com.skidsdev.fyrestone.client.gui.GuiGuideBook;

import net.minecraft.item.ItemStack;

public class BookEntryListItem implements IBookEntry
{
	private ItemStack stack;
	private String title;
	
	public BookEntryListItem(ItemStack stack, String title)
	{
		this.stack = stack;
		this.title = title;
	}
	
	@Override
	public void drawEntry(GuiGuideBook gui, int x, int y, int mouseX, int mouseY)
	{
		gui.renderItemStackAt(stack, x, y);
		
		int xSize = gui.getStringWidth(title);
		
		Color color = Color.BLACK;
		if (gui.isInRect(x, y, xSize, 16, mouseX, mouseY)) color = Color.DARK_GRAY;
		gui.renderText(title, x + 16, y, color.getRGB());
	}

	@Override
	public List<String> getMouseOverTooltip()
	{
		return null;
	}

	@Override
	public boolean isMouseOver(GuiGuideBook gui, int mouseX, int mouseY)
	{
		return false;
	}

	@Override
	public void mouseClicked(GuiGuideBook gui)
	{
		
	}
}
