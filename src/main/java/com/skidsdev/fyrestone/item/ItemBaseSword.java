package com.skidsdev.fyrestone.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import com.skidsdev.fyrestone.FyrestoneCreativeTabs;
import com.skidsdev.fyrestone.item.swordeffect.ISwordEffect;
import com.skidsdev.fyrestone.item.swordeffect.ISwordEffectOnHit;
import com.skidsdev.fyrestone.item.swordeffect.ISwordEffectOnSwing;
import com.skidsdev.fyrestone.item.swordeffect.ISwordEffectOnTickInHand;
import com.skidsdev.fyrestone.item.swordeffect.ISwordEffectOnUse;
import com.skidsdev.fyrestone.item.swordeffect.SwordEffectFire;
import com.skidsdev.fyrestone.item.swordeffect.SwordEffectFireball;
import com.skidsdev.fyrestone.item.swordeffect.SwordEffectPotion;
import com.skidsdev.fyrestone.item.swordeffect.SwordEffectWaterstone;
import com.skidsdev.fyrestone.utils.Helper;
import com.skidsdev.fyrestone.utils.ItemNBTHelper;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
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
		this.setCreativeTab(FyrestoneCreativeTabs.tabFyrestone);
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
	public int getMaxDamage(ItemStack stack)
	{
		EnumSwordType type = EnumSwordType.values()[ItemNBTHelper.getInt(stack, "sword_type", 0)];
		
		return type.getDurability();
	}
	
	@Override
	public boolean onEntitySwing(EntityLivingBase entity, ItemStack stack)
	{
		if (entity instanceof EntityPlayer)
		{
			applyEffectOnSwing(stack, (EntityPlayer)entity);
		}
		
		return false;
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		boolean flag = false;
		
		if (entity instanceof EntityLivingBase)
		{
			EnumSwordType type = EnumSwordType.values()[ItemNBTHelper.getInt(stack, "sword_type", 0)];
			float attackDamage = type.getAttackDamage();
			
			boolean isCrit = (player.fallDistance > 0.2F);
			if (isCrit) attackDamage *= 1.2;
			
			flag = entity.attackEntityFrom(DamageSource.causePlayerDamage(player), attackDamage);
			
			applyEffectOnHit(stack, (EntityLivingBase)entity, player);
			
			if (flag) stack.damageItem(1, player);
			
			if (stack.stackSize < 1) player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
		}
		
		return flag;
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
	
	private void applyEffectOnHit(ItemStack stack, EntityLivingBase entity, EntityPlayer player)
	{
		EnumSwordType type = EnumSwordType.values()[ItemNBTHelper.getInt(stack, "sword_type", 0)];
		List<ISwordEffect> effects = type.getEffects();
		
		for(ISwordEffectOnHit effect : getEffectsOnHit(effects))
		{
			effect.ApplyEffect(stack, entity, player);
		}
	}
	
	private void applyEffectOnTickInHand(ItemStack stack, EntityLivingBase entity)
	{
		EnumSwordType type = EnumSwordType.values()[ItemNBTHelper.getInt(stack, "sword_type", 0)];
		List<ISwordEffect> effects = type.getEffects();
		
		for(ISwordEffectOnTickInHand effect : getEffectsOnTickInHand(effects))
		{
			effect.ApplyEffect(stack, entity);
		}
	}
	
	private void applyEffectOnUse(ItemStack stack, EntityLivingBase entity)
	{
		EnumSwordType type = EnumSwordType.values()[ItemNBTHelper.getInt(stack, "sword_type", 0)];
		List<ISwordEffect> effects = type.getEffects();
		
		for(ISwordEffectOnUse effect : getEffectsOnUse(effects))
		{
			effect.ApplyEffect(stack, entity);
		}
	}
	
	private void applyEffectOnSwing(ItemStack stack, EntityPlayer player)
	{
		EnumSwordType type = EnumSwordType.values()[ItemNBTHelper.getInt(stack, "sword_type", 0)];
		List<ISwordEffect> effects = type.getEffects();
		
		for(ISwordEffectOnSwing effect : getEffectsOnSwing(effects))
		{
			effect.ApplyEffect(stack, player);
		}
	}

	private List<ISwordEffectOnHit> getEffectsOnHit(List<ISwordEffect> effects)
	{
		return getEffectsOfType(effects, ISwordEffectOnHit.class);
	}
	
	private List<ISwordEffectOnTickInHand> getEffectsOnTickInHand(List<ISwordEffect> effects)
	{
		return getEffectsOfType(effects, ISwordEffectOnTickInHand.class);
	}
	
	private List<ISwordEffectOnUse> getEffectsOnUse(List<ISwordEffect> effects)
	{
		return getEffectsOfType(effects, ISwordEffectOnUse.class);
	}
	
	private List<ISwordEffectOnSwing> getEffectsOnSwing(List<ISwordEffect> effects)
	{
		return getEffectsOfType(effects, ISwordEffectOnSwing.class);
	}
	
	private <T extends ISwordEffect> List<T> getEffectsOfType(List<ISwordEffect> effects, Class<T> type)
	{
		ArrayList<T> list = new ArrayList<T>();
		
		for(ISwordEffect effect : effects)
		{
			if (type.isInstance(effect)) list.add((T)effect);
		}
		
		return list;
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
			"fyrestone", 1.6f, 6.0f, 500,
			Helper.formatTooltip(""),
			new SwordEffectFire(5)
		),
		EARTHSTONE_SWORD
		(
			"earthstone", 1.0f, 7.0f, 1000,
			Helper.formatTooltip(""),
			new SwordEffectPotion(Potion.getPotionById(2), 80, 2)
		),
		PLAGUEBLADE
		(
			"plagueblade", 1.6f, 7.0f, 1000,
			Helper.formatTooltip(""),
			new SwordEffectPotion(Potion.getPotionById(19), 50, 1)
		),
		FLAMEVENOM
		(
			"flamevenom", 1.6f, 6.0f, 500,
			Helper.formatTooltip(""),
			new SwordEffectFire(3), 
			new SwordEffectPotion(Potion.getPotionById(19), 40, 1)
		),
		WATERSTONE_SWORD
		(
			"waterstone", 1.6f, 6.0f, 250,
			Helper.formatTooltip(""),
			new SwordEffectWaterstone(12)
		),
		BRIMSTONE_SWORD
		(
			"brimstone", 1.6f, 10.0f, 1500,
			Helper.formatTooltip(""),
			new SwordEffectFireball(),
			new SwordEffectFire(1)
		),
		AIRSTONE_SWORD
		(
			"airstone", 1.6f, 150.0f, 10,
			Helper.formatTooltip("")
		);
		
		private List<ISwordEffect> effects;
		private List<String> tooltip;
		private float attackSpeed;
		private float attackDamage;
		private int durability;
		private String name;
		
		private EnumSwordType(String name, float atkSpeed, float atkDamage, int durability, List<String> tooltip, ISwordEffect... effects)
		{
			this.name = name;
			this.attackSpeed = atkSpeed;
			this.attackDamage = atkDamage;
			this.durability = durability;
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
		
		public float getAttackSpeed()
		{
			return attackSpeed;
		}
		
		public float getAttackDamage()
		{
			return attackDamage;
		}
		
		public int getDurability()
		{
			return durability;
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
