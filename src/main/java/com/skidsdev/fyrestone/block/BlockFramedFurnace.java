package com.skidsdev.fyrestone.block;

import com.skidsdev.fyrestone.Fyrestone;
import com.skidsdev.fyrestone.gui.GuiHandlerFramedFurnace;
import com.skidsdev.fyrestone.tileentity.TileEntityFramedFurnace;
import com.skidsdev.fyrestone.utils.ItemNBTHelper;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFramedFurnace extends BlockDirectional
{	
	public static final PropertyEnum FRAME_TYPE = PropertyEnum.create("frame_type", EnumFrameType.class);
	public EnumFrameType frameType;
    public boolean isActive;
	
	public BlockFramedFurnace()
	{
		super("blockFramedFurnace", Material.ROCK, 2.5f, 30.0f);
		
		frameType = EnumFrameType.WOOD;
		isActive = false;
	}

	@Override
	public BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BlockHorizontal.FACING, FRAME_TYPE);
	}

	@Override
	public TileEntity createTileEntity(World worldIn, IBlockState state)
    {
        return new TileEntityFramedFurnace();
    }
	
	@Override
	public boolean hasTileEntity(IBlockState state) { return true; }
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		
		if(tileEntity instanceof TileEntityFramedFurnace)
		{
			return state.withProperty(FRAME_TYPE, ((TileEntityFramedFurnace)tileEntity).FRAME_TYPE);
		}
		
		return state;
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		//frameType = EnumFrameType.values()[ItemNBTHelper.getInt(stack, "frame_type", 0)];
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack stack, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!worldIn.isRemote)
		{
			player.openGui(Fyrestone.instance, GuiHandlerFramedFurnace.getGuiID(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		
		return true;
	}
}
