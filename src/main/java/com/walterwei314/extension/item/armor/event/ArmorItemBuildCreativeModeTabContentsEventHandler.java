package com.walterwei314.extension.item.armor.event;

import com.walterwei314.extension.item.armor.ModArmorItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@EventBusSubscriber
public class ArmorItemBuildCreativeModeTabContentsEventHandler {
    @SubscribeEvent
    public static void addCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            ModArmorItems.DIRT_ARMOR_SET.forEach(event::accept);
        }
    }
}
