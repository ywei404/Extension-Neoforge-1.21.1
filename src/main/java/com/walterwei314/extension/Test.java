package com.walterwei314.extension;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.*;

@EventBusSubscriber
public class Test {
    @SubscribeEvent
    public static void test(LivingHealEvent event){

    }
}
