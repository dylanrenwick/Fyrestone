package com.skidsdev.fyrestone;

import com.skidsdev.fyrestone.handler.PlayerTickHandler;
import com.skidsdev.fyrestone.tile.TileEntityRitualCircle;
import com.skidsdev.fyrestone.utils.LootHandler;
import com.skidsdev.fyrestone.world.FyrestoneWorldGenerator;

import net.minecraftforge.common.MinecraftForge;
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
		
		this.modConfig.setupBlocks();
		this.modConfig.setupItems();
		this.modConfig.setupCrafting();
		
		MinecraftForge.EVENT_BUS.register(new PlayerTickHandler());
		
		GameRegistry.registerTileEntity(TileEntityRitualCircle.class, "ritualcircle");
	}
	
	public void init(FMLInitializationEvent e)
	{
		GameRegistry.registerWorldGenerator(new FyrestoneWorldGenerator(), 0);
	}
	
	public void postInit(FMLPostInitializationEvent e)
	{
		LootHandler.register();
	}
}
