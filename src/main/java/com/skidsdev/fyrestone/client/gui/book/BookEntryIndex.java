package com.skidsdev.fyrestone.client.gui.book;

import java.util.ArrayList;
import java.util.List;

import com.skidsdev.fyrestone.client.gui.GuiGuideBook;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class BookEntryIndex implements IBookEntry
{
	private ResourceLocation icon;
	private int u;
	private int v;
	private String title;
	private int destinationPage;
	
	private int xPos;
	private int yPos;
	
	public BookEntryIndex(ResourceLocation icon, int u, int v, String title, int destPage)
	{
		this.icon = icon;
		this.title = title;
		this.destinationPage = destPage;
		
		this.u = u;
		this.v = v;
	}
	
	@Override
	public void drawEntry(GuiGuideBook gui, int x, int y, int mouseX, int mouseY)
	{
		this.xPos = x;
		this.yPos = y;
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(icon);
		GlStateManager.scale(2, 2, 2);
		
		if (!gui.isInRect(x, y, 32, 32, mouseX, mouseY))
		{
			GlStateManager.color(1f, 1f, 1f, 0.5f);
			GlStateManager.enableBlend();
		}
		
		gui.drawTexturedModalRect(x / 2, y / 2, u, v, 16, 16);
		
		GlStateManager.disableBlend();
		GlStateManager.scale(0.5, 0.5, 0.5);
	}

	@Override
	public List<String> getMouseOverTooltip()
	{
		List<String> tooltip = new ArrayList<String>();
		tooltip.add(title);
		return tooltip;
	}

	@Override
	public boolean isMouseOver(GuiGuideBook gui, int mouseX, int mouseY)
	{
		return gui.isInRect(xPos, yPos, 32, 32, mouseX, mouseY);
	}

	@Override
	public void mouseClicked(GuiGuideBook gui)
	{
		gui.changePage(this.destinationPage);
	}
}
