package com.skidsdev.fyrestone.client.gui;

import java.io.IOException;
import java.util.List;

import com.skidsdev.fyrestone.client.gui.book.IBookEntry;
import com.skidsdev.fyrestone.client.gui.book.IBookPage;
import com.skidsdev.fyrestone.utils.BookNBTHelper;
import com.skidsdev.fyrestone.utils.ItemNBTHelper;
import com.skidsdev.fyrestone.utils.VersionInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiGuideBook extends GuiScreen
{
	public static ResourceLocation BOOK_PAGE_BG = new ResourceLocation(VersionInfo.ModId + ":textures/gui/bookPage.png");
	
	public static int BG_WIDTH  = 146;
	public static int BG_HEIGHT = 180;
	
	public List<IBookPage> pages;
	
	public int index;
	
	public GuiGuideBook(EntityPlayer player, ItemStack stack)
	{
		this.mc = Minecraft.getMinecraft();
		
		ScaledResolution scaledRes = new ScaledResolution(mc);
		
		this.setGuiSize(scaledRes.getScaledWidth(), scaledRes.getScaledHeight());
		
		if (!ItemNBTHelper.detectNBT(stack)) BookNBTHelper.initializeNBT(stack);
		
		pages = BookNBTHelper.getPagesFromBook(stack);
		
		index = BookNBTHelper.getBookPage(stack);
	}
	
	@Override public boolean doesGuiPauseGame() { return false; }
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton)
	{
		// Gui's page-independant mouseClick handling
		
		IBookPage page = pages.get(index);
		page.mouseClicked(this, mouseX, mouseY, mouseButton);
	}
	
	@Override
	public void keyTyped(char typedChar, int keyCode) throws IOException
	{
		if (typedChar == 'e')
		{
            this.mc.displayGuiScreen((GuiScreen)null);

            if (this.mc.currentScreen == null)
            {
                this.mc.setIngameFocus();
            }
		}
		
		super.keyTyped(typedChar, keyCode);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		mc.getTextureManager().bindTexture(BOOK_PAGE_BG);
		GlStateManager.scale(1, 1, 1);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(getGuiLeft(), getGuiTop(), 0, 0, BG_WIDTH, BG_HEIGHT);
		
		IBookPage page = pages.get(index);
		page.drawPage(this, getGuiLeft(), getGuiTop(), mouseX, mouseY);
		
		List<String> pageHoverText = page.getHoveringText(this, mouseX, mouseY);
		if (pageHoverText != null && !pageHoverText.isEmpty())
		{
			drawHoveringText(pageHoverText, mouseX, mouseY, fontRendererObj);
		}
	}
	
	@Override
	public void onResize(Minecraft mc, int width, int height)
	{
		ScaledResolution scaledRes = new ScaledResolution(mc);
		
		this.setGuiSize(scaledRes.getScaledWidth(), scaledRes.getScaledHeight());
	}
	
	public void renderItemStackAt(ItemStack stack, int x, int y)
	{
		this.itemRender.renderItemIntoGUI(stack, x, y);
		GlStateManager.color(1f, 1f, 1f, 1f);
	}
	
	public void renderBookEntryAt(IBookEntry entry, int x, int y, int mouseX, int mouseY)
	{
		entry.drawEntry(this, x, y, mouseX, mouseY);
	}
	
	public void renderText(String text, int x, int y, int color)
	{
		this.fontRendererObj.drawString(text, x, y, color);
	}
	
	public int getStringWidth(String text)
	{
		return this.fontRendererObj.getStringWidth(text);
	}
	
	public boolean isInRect(int x, int y, int xSize, int ySize, int mouseX, int mouseY)
	{
		return ((mouseX >= x && mouseX <= x+xSize) && (mouseY >= y && mouseY <= y+ySize));
	}
	
	public void changePage(int index)
	{
		if (index < pages.size())
		{
			IBookPage page = pages.get(index);
			if (page != null)
				this.index = index;
		}
	}
	
	public int getGuiLeft()
	{
		return (this.width / 2) - (BG_WIDTH / 2);
	}
	
	public int getGuiTop()
	{
		return (this.height / 2) - (BG_HEIGHT / 2);
	}
}
