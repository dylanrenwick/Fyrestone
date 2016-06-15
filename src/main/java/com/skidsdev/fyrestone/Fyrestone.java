package com.skidsdev.fyrestone;

import com.skidsdev.fyrestone.utils.VersionInfo;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
		modid = VersionInfo.ModId,
		name = VersionInfo.ModName, 
		version = VersionInfo.Version
)
public class Fyrestone
{    
    @SidedProxy(clientSide="com.skidsdev.fyrestone.ClientProxy", serverSide="com.skidsdev.fyrestone.ServerProxy")
    public static CommonProxy proxy;
    
    @Instance
    public static Fyrestone instance;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent e)
    {
    	proxy.preInit(e);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent e)
    {
    	proxy.init(e);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent e)
    {
    	proxy.postInit(e);
    }
}
