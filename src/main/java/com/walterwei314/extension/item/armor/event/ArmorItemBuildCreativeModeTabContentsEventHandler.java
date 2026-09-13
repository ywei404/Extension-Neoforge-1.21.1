package com.walterwei314.extension.item.armor.event;

import com.walterwei314.extension.Extensionneoforge1211;
import com.walterwei314.extension.item.armor.BaseArmor;
import com.walterwei314.extension.item.armor.ModArmorItems;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.checkerframework.checker.units.qual.A;

import java.util.function.Consumer;

@EventBusSubscriber
public class ArmorItemBuildCreativeModeTabContentsEventHandler {
    @SubscribeEvent
    public static void addCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            BuiltInRegistries.ITEM.forEach(item -> {
                if (!(item instanceof BaseArmor)) {
                    return;
                }

                var key = BuiltInRegistries.ITEM.getKey(item);

                if (!key.getNamespace().equals(Extensionneoforge1211.MODID)) {
                    return;
                }

                event.accept(item);
            });
        }
    }
}
