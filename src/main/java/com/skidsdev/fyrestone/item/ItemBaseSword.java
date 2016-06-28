package com.skidsdev.fyrestone.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import com.skidsdev.fyrestone.item.ItemBaseShard.EnumShardType;
import com.skidsdev.fyrestone.utils.Helper;
import com.skidsdev.fyrestone.utils.ISwordEffect;
import com.skidsdev.fyrestone.utils.ItemNBTHelper;
import com.skidsdev.fyrestone.utils.SwordEffectFire;
import com.skidsdev.fyrestone.utils.SwordEffectPotion;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBaseSword extends ItemSword
{
	public ItemBaseSword(ToolMaterial material, String regName)
	{
		super(material);
		this.setRegistryName(regName);
		this.setUnlocalizedName(this.getRegistryName().toString());
		this.addPropertyOverride(new ResourceLocation("sword"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return ItemNBTHelper.getInt(stack, "sword_type", 0);
            }
        });
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		if (entity instanceof EntityLivingBase)
		{
			applyEffectToEntity(stack, (EntityLivingBase)entity);
		}
		
		return false;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		EnumSwordType type = EnumSwordType.values()[ItemNBTHelper.getInt(stack, "sword_type", 0)];
		return super.getUnlocalizedName() + "_" + type.getName();
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		EnumSwordType type = EnumSwordType.values()[ItemNBTHelper.getInt(stack, "sword_type", 0)];
		List<String> swordTooltip = type.getTooltip();
		for(String line : swordTooltip)
		{
			tooltip.add(line);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List subItems)
	{
		for(EnumSwordType type : EnumSwordType.values())
		{
			ItemStack stack = new ItemStack(itemIn);
			ItemNBTHelper.setInt(stack, "sword_type", type.ordinal());
			subItems.add(stack);
		}
	}
	
	private void applyEffectToEntity(ItemStack stack, EntityLivingBase entity)
	{
		EnumSwordType type = EnumSwordType.values()[ItemNBTHelper.getInt(stack, "sword_type", 0)];
		List<ISwordEffect> effects = type.getEffects();
		
		for(ISwordEffect effect : effects)
		{
			effect.ApplyEffect(entity);
		}
	}
	
	public static ItemStack getSwordStack(EnumSwordType type)
	{
		ItemStack stack = new ItemStack(ItemRegister.itemSword);
		ItemNBTHelper.setInt(stack, "sword_type", type.ordinal());
		return stack;
	}
	
	public enum EnumSwordType implements IStringSerializable
	{
		FYRESTONE_SWORD
		(
				"fyrestone",
				Helper.formatTooltip(""),
				new SwordEffectFire(5)
		),
		EARTHSTONE_SWORD
		(
				"earthstone",
				Helper.formatTooltip(""),
				new SwordEffectPotion(Potion.getPotionById(2), 80, 2)
		),
		PLAGUEBLADE
		(
				"plagueblade",
				Helper.formatTooltip(""),
				new SwordEffectPotion(Potion.getPotionById(19), 50, 1)
		),
		FLAMEVENOM
		(
				"flamevenom",
				Helper.formatTooltip(""),
				new SwordEffectFire(3), 
				new SwordEffectPotion(Potion.getPotionById(19), 40, 1)
		);
		
		private List<ISwordEffect> effects;
		private List<String> tooltip;
		private String name;
		
		private EnumSwordType(String name, List<String> tooltip, ISwordEffect... effects)
		{
			this.name = name;
			this.tooltip = tooltip;
			this.effects = Arrays.asList(effects);
		}
		
		public List<ISwordEffect> getEffects()
		{
			return (List)new ArrayList<ISwordEffect>(effects);
		}
		
		public List<String> getTooltip()
		{
			return (List)new ArrayList<String>(tooltip);
		}
		
		public String getName()
		{
			return name;
		}
		
		public String toString()
		{
			return name;
		}
	}
}
