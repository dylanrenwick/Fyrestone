package com.skidsdev.fyrestone.item;

import java.util.ArrayList;
import java.util.List;

import com.skidsdev.fyrestone.block.BlockRegister;
import com.skidsdev.fyrestone.block.BlockRitualCircle;
import com.skidsdev.fyrestone.block.BlockRitualCircle.EnumRitualType;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBaseShard extends BaseItem
{
	public ItemBaseShard() {
		super("itemShard");
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		EnumShardType type = EnumShardType.values()[stack.getMetadata()];
		
		switch(type)
		{
			case FYRESTONE:
				tooltip.add("This shard is glowing softly with heat");
				tooltip.add("");
				tooltip.add("Fyrestone shards contain more magical");
				tooltip.add("potential than other shards, and are");
				tooltip.add("the only shards our world appears to be");
				tooltip.add("able to create naturally.");
				break;
			case WATERSTONE:
				tooltip.add("This shard is cool to the touch");
				tooltip.add("");
				tooltip.add("Waterstone is the polar opposite of");
				tooltip.add("Fyrestone, and as such seems to suck");
				tooltip.add("in all heat around it, creating a");
				tooltip.add("pocket of cold air.");
				break;
			case ORDERSTONE:
				tooltip.add("This shard is inert");
				tooltip.add("");
				tooltip.add("Orderstone is a perfect balance of");
				tooltip.add("opposing forces. A material in pure");
				tooltip.add("harmony. It's not very useful.");
				break;
			case CHAOSSTONE:
				tooltip.add("This shard is in constant self-conflict");
				tooltip.add("");
				tooltip.add("Chaosstone is the polar opposite of");
				tooltip.add("Orderstone, a material locked in a");
				tooltip.add("constant war with itself. It looks like");
				tooltip.add("it might tear apart at any moment.");
				break;
			case EARTHSTONE:
				tooltip.add("This shard is surprisingly heavy");
				tooltip.add("");
				tooltip.add("Earthstone is a solid, dense material.");
				tooltip.add("It is also somewhat porous, and absorbs");
				tooltip.add("other substances well.");
				break;
			case AIRSTONE:
				tooltip.add("This shard is surprisingly light");
				tooltip.add("");
				tooltip.add("Airstone is incredibly light, yet incredibly");
				tooltip.add("fragile. It floats slowly to the ground, but");
				tooltip.add("can shatter from a light breeze.");
				break;
			case DEBUG:
				tooltip.add("This shard is awesome");
				tooltip.add("");
				tooltip.add("You shouldn't have this, why do you have this?");
				break;
			default:
				break;
		}
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (worldIn.isRemote) return EnumActionResult.SUCCESS;
		if (getStonePlatform(worldIn, pos) && hitY == 1.0f)
		{
			IBlockState ritualCircle = getRitualCircleBlock(stack);
			
			if (ritualCircle != null)
			{
				worldIn.setBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()), ritualCircle, 3);
				BlockRegister.blockRitualCircle.onBlockPlacedBy(worldIn, pos.add(0, 1, 0), ritualCircle, player, stack);
				stack.stackSize -= 1;
			}
		}
		
		if (EnumShardType.values()[stack.getMetadata()] == EnumShardType.DEBUG)
		{
			debug(worldIn.getBlockState(pos).getActualState(worldIn, pos));
		}
		
		return EnumActionResult.SUCCESS;
	}

	@Override
	public int getMetadata(int damage)
	{
		return damage;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		EnumShardType type = EnumShardType.values()[stack.getMetadata()];
		return super.getUnlocalizedName() + "_" + type.getName();
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List subItems)
	{
		for (EnumShardType type : EnumShardType.values())
		{
			ItemStack subItemStack = new ItemStack(itemIn, 1, type.ordinal());
			subItems.add(subItemStack);
		}
	}
	
	private boolean getStonePlatform(World worldIn, BlockPos pos)
	{
		for (int i = -1; i < 2; i++)
		{
			for (int j = -1; j < 2; j++)
			{
				BlockPos newPos = new BlockPos(pos.getX() + i, pos.getY(), pos.getZ() + j);
				Block block = worldIn.getBlockState(newPos).getBlock();
				
				if (block != Blocks.STONE) return false;
				
				for (int k = 1; k < 3; k++)
				{
					newPos = newPos.add(0, 1, 0);
					block = worldIn.getBlockState(newPos).getBlock();
					
					if (block != Blocks.AIR) return false;
				}
			}
		}
		
		return true;
	}
	
	private void debug(IBlockState state)
	{
		String msg = state.getBlock().getUnlocalizedName() + " - Properties [ ";
		for (IProperty prop : state.getPropertyNames())
		{
			msg += prop.getName() + ": " + state.getValue(prop).toString() + ", ";
		}
		msg = msg.substring(0, msg.length() - 2);
		msg += " ]";
		System.out.println(msg);
		
		int meta = state.getBlock().getMetaFromState(state);
		System.out.println("Meta: " + meta);
	}
	
	private IBlockState getRitualCircleBlock(ItemStack stack)
	{
		EnumShardType type = EnumShardType.values()[stack.getMetadata()];
		IBlockState defaultState = BlockRegister.blockRitualCircle.getDefaultState();
		
		switch(type)
		{
			case FYRESTONE:
				return defaultState.withProperty(BlockRitualCircle.RITUAL_TYPE, EnumRitualType.METALLURGY);
			case WATERSTONE:
				return defaultState.withProperty(BlockRitualCircle.RITUAL_TYPE, EnumRitualType.ALCHEMY);
			case EARTHSTONE:
				return defaultState.withProperty(BlockRitualCircle.RITUAL_TYPE, EnumRitualType.IMBUING);
			default:
				return null;			
		}
	}
	
	public static ItemStack getShardStack(int amt, EnumShardType type)
	{
		return new ItemStack(ItemRegister.itemShard, amt, type.ordinal());
	}
	
	public enum EnumShardType implements IStringSerializable
	{
		FYRESTONE("fyrestone"),
		WATERSTONE("waterstone"),
		ORDERSTONE("orderstone"),
		CHAOSSTONE("chaosstone"),
		EARTHSTONE("earthstone"),
		AIRSTONE("airstone"),
		DEBUG("debug");
		
		private String name;
		
		private EnumShardType(String i_name)
		{
			name = i_name;
		}

		@Override
		public String getName() {
			return name;
		}
	}
}
