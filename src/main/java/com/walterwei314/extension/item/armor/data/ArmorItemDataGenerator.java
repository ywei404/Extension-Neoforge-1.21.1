package com.walterwei314.extension.item.armor.data;

import com.walterwei314.extension.Extensionneoforge1211;
import com.walterwei314.extension.item.armor.recipe.ArmorItemRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = Extensionneoforge1211.MODID)
public final class ArmorItemDataGenerator {

    private ArmorItemDataGenerator() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();

        generator.addProvider(
                event.includeServer(),
                new ArmorItemRecipeProvider(
                        generator.getPackOutput(),
                        event.getLookupProvider()
                )
        );
    }
}