package com.skidsdev.fyrestone;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.skidsdev.fyrestone.item.ItemRegister;
import com.skidsdev.fyrestone.utils.VersionInfo;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class FyrestoneCreativeTabs
{
    public static final CreativeTabs tabFyrestone = new CreativeTabs(VersionInfo.ModId)
    {
        @Override
        public Item getTabIconItem()
        {
            return ItemRegister.itemShard;
        }
    };
}