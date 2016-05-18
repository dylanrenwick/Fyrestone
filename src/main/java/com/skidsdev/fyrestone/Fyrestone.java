package com.skidsdev.fyrestone;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import com.skidsdev.fyrestone.utils.VersionInfo;

@Mod(
		modid = VersionInfo.ModName, 
		version = VersionInfo.Version
)
public class Fyrestone
{
    private Config modConfig;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
		this.modConfig = new Config(event.getSuggestedConfigurationFile());
    }
}
