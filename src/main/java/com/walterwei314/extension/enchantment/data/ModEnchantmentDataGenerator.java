package com.walterwei314.extension.enchantment.data;

import com.walterwei314.extension.Extensionneoforge1211;
import com.walterwei314.extension.enchantment.registry.EnchantmentRegistrySets;
import com.walterwei314.extension.enchantment.tag.ModEnchantmentTagProvider;
import net.minecraft.data.DataProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;

@EventBusSubscriber(modid = Extensionneoforge1211.MODID)
public final class ModEnchantmentDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        event.getGenerator().addProvider(event.includeServer(),
                (DataProvider.Factory<DatapackBuiltinEntriesProvider>) output -> new DatapackBuiltinEntriesProvider(
                        output,
                        event.getLookupProvider(),
                        EnchantmentRegistrySets.BUILDER,
                        Set.of(Extensionneoforge1211.MODID)
                )
        );

        event.createProvider(ModEnchantmentTagProvider::new);
    }

    private ModEnchantmentDataGenerator() {
    }
}