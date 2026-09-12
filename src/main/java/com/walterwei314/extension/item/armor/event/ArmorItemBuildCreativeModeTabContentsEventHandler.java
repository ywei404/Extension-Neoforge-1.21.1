package com.walterwei314.extension.item.armor.event;

import com.walterwei314.extension.item.armor.BaseArmor;
import com.walterwei314.extension.item.armor.ModArmorItems;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.function.Consumer;

@EventBusSubscriber
public class ArmorItemBuildCreativeModeTabContentsEventHandler {
    @SubscribeEvent
    public static void addCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            BuiltInRegistries.ITEM.forEach(item -> {
                if (item instanceof BaseArmor){
                    event.accept(item);
                }
            });
        }
    }
}
