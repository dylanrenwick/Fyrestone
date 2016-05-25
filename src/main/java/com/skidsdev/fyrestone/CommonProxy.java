package com.skidsdev.fyrestone;

import com.skidsdev.fyrestone.world.FyrestoneWorldGenerator;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy 
{
	private Config modConfig;
	
	public void preInit(FMLPreInitializationEvent e)
	{		
		this.modConfig = new Config(e.getSuggestedConfigurationFile());
		
		this.modConfig.setupItems();
		this.modConfig.setupBlocks();
		this.modConfig.setupCrafting();
	}
	
	public void init(FMLInitializationEvent e)
	{
		GameRegistry.registerWorldGenerator(new FyrestoneWorldGenerator(), 0);
	}
	
	public void postInit(FMLPostInitializationEvent e)
	{
		
	}
}
