package com.skidsdev.fyrestone.block;

import net.minecraft.util.IStringSerializable;

public enum EnumFrameType implements IStringSerializable
{
	WOOD			(2, 1, -1, "wood"),
	STONE			(3, 1, 0, "stone"),
	IRON			(4, 1, 0, "iron"),
	NETHERRACK		(1, 0.125f, -7, "netherrack"),
	NETHER_QUARTZ	(1, 8, 24, "nether_quartz"),
	GOLD			(1, 1, 0, "gold"),
	DIAMOND			(5, 0.9f, 1, "diamond"),
	EMERALD			(6, 0.8f, 2, "emerald");
	
	private final int UpgradeSlots;
	private final float SpeedMod;
	private final int EfficiencyMod;
	private final String name;
	
	private EnumFrameType(int slots, float spdMod, int effMod, String name)
	{
		this.UpgradeSlots = slots;
		this.SpeedMod = spdMod;
		this.EfficiencyMod = effMod;
		this.name= name;
	}
	
	public int getSlots()
	{
		return this.UpgradeSlots;
	}
	
	public float getSpeedMod()
	{
		return this.SpeedMod;
	}
	
	public int getEfficiencyMod()
	{
		return this.EfficiencyMod;
	}

	@Override
	public String getName() 
	{
		return this.name;
	}
}
