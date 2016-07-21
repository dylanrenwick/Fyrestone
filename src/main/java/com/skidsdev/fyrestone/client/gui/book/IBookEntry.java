package com.skidsdev.fyrestone.client.gui.book;

import java.util.List;

import com.skidsdev.fyrestone.client.gui.GuiGuideBook;

public interface IBookEntry
{
	public void drawEntry(GuiGuideBook gui, int x, int y, int mouseX, int mouseY);
	
	public void mouseClicked(GuiGuideBook gui);
	
	public boolean isMouseOver(GuiGuideBook gui, int mouseX, int mouseY);
	
	public List<String> getMouseOverTooltip();
}
