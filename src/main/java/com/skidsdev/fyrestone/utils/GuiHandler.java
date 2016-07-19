package com.skidsdev.fyrestone.utils;

import com.skidsdev.fyrestone.Fyrestone;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class GuiHandler implements IGuiHandler
{
	private static GuiHandler instance;
	
	public static GuiHandler getInstance()
	{
		if (instance == null) instance = new GuiHandler();
		return instance;
	}
	
	private GuiHandler()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(Fyrestone.instance, this);
	}
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World worldIn, int x, int y, int z)
	{
		BlockPos pos = new BlockPos(x, y, z);
		IOpenableGUI openableGUI = this.getOpenableGUI(id, player, worldIn, pos);
		if (openableGUI != null) return openableGUI.getServerGuiElement(id, player, worldIn, pos);
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World worldIn, int x, int y, int z)
	{
		BlockPos pos = new BlockPos(x, y, z);
		IOpenableGUI openableGUI = this.getOpenableGUI(id, player, worldIn, pos);
		if (openableGUI != null) return openableGUI.getClientGuiElement(id, player, worldIn, pos);
		
		return null;
	}
	
	private IOpenableGUI getOpenableGUI(int id, EntityPlayer player, World worldIn, BlockPos pos)
	{
		IOpenableGUI openableGUI = null;
		
		// GUI is Guide Book
		if (id == 0)
		{
			Item item = player.getHeldItemMainhand().getItem();
			if (item instanceof IOpenableGUI)
			{
				return (IOpenableGUI)item;
			}
		}
		
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if(tileEntity instanceof IOpenableGUI)
		{
			openableGUI = (IOpenableGUI) tileEntity;
		}
		
		return openableGUI;
	}
}
