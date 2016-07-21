package com.skidsdev.fyrestone.client.gui.book;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.skidsdev.fyrestone.client.gui.GuiGuideBook;

import net.minecraft.client.renderer.GlStateManager;

public class BookPageIndex implements IBookPage
{
	private static final int entryStart_X = 22;
	private static final int entryStart_Y = 30;
	
	private List<IBookEntry> entries;
	
	public BookPageIndex()
	{
		entries = new ArrayList<IBookEntry>();
	}
	
	@Override
	public List<IBookEntry> getEntries()
	{
		return entries;
	}

	@Override
	public String getTitle()
	{
		return "Fyrestone Manual";
	}
	
	public void addEntry(BookEntryIndex entry)
	{
		entries.add(entry);
	}

	@Override
	public void drawPage(GuiGuideBook gui, int x, int y, int mouseX, int mouseY)
	{
		gui.renderText("Fyrestone", x + 46, y + 16, Color.BLACK.getRGB());
		

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		
		for(int i = 0; i < entries.size(); i++)
		{
			int entryX;
			int entryY;
			
			if (i == 0)
			{
				entryX = x + entryStart_X + 34;
				entryY = y + entryStart_Y;
			}
			else
			{
				entryX = x + entryStart_X + (((i - 1) % 3 == 0) ? 0 : 34 * (i - 1));
				entryY = y + entryStart_Y + (34 * (((i - 1) / 3) + 1));
			}
			
			entries.get(i).drawEntry(gui, entryX, entryY, mouseX, mouseY);
		}
	}

	@Override
	public void mouseClicked(GuiGuideBook gui, int mouseX, int mouseY, int mouseButton)
	{
		
	}

	@Override
	public List<String> getHoveringText(GuiGuideBook gui, int mouseX, int mouseY)
	{
		IBookEntry entry = getMousedOver(gui, mouseX, mouseY);
		return entry != null ? entry.getMouseOverTooltip() : null;
	}
	
	private IBookEntry getMousedOver(GuiGuideBook gui, int mouseX, int mouseY)
	{
		for(IBookEntry entry : entries)
		{
			if (entry.isMouseOver(gui, mouseX, mouseY)) return entry;
		}
		
		return null;
	}
}
