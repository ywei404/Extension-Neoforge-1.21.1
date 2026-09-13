package com.walterwei314.extension.language;

import com.walterwei314.extension.Extensionneoforge1211;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = Extensionneoforge1211.MODID)
public final class LanguageDataGenerator {

    private LanguageDataGenerator() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        event.createProvider(ModEnglishLanguageProvider::new);
    }
}