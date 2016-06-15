package com.skidsdev.fyrestone;

import com.skidsdev.fyrestone.client.render.blocks.BlockRenderRegister;
import com.skidsdev.fyrestone.client.render.items.ItemRenderRegister;
import com.skidsdev.fyrestone.item.ItemRegister;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent e)
	{
		super.preInit(e);
		
		ItemRenderRegister.registerItemRenderer();
		BlockRenderRegister.registerBlockRenderer();
	}
	
	@Override
	public void init(FMLInitializationEvent e)
	{
		super.init(e);
		
		//ItemRenderRegister.registerItemRenderer();
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent e)
	{
		super.postInit(e);
	}
}
