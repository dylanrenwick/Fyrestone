package com.skidsdev.fyrestone.client.gui.book;

import java.util.List;

public interface IBookPage
{
	public List<IBookEntry> getEntries();
	
	public String getTitle();
}
