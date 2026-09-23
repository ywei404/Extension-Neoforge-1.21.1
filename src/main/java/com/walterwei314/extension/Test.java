package com.walterwei314.extension;

import com.walterwei314.extension.event.BaseLivingEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber
public class Test {
    @SubscribeEvent
    public static void test(EntityTickEvent.Pre event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player player && !player.level().isClientSide() && player.level().getGameTime() % 20 == 0) {
//            AttributeInstance instance = player.getAttribute(ModAttributes.HEAL_MULTIPLIER);
//
//            if (instance != null) {
//                System.out.println("heal_multiplier: " + instance.getValue());
//            }
        }
    }

    @SubscribeEvent
    public static void test2(BaseLivingEvent.LivingAbsorptionChangeEvent event) {
        LivingEntity entity = event.getEntity();

        if (entity instanceof Player player && !player.level().isClientSide()){
            System.out.println(event.getAmount());
        }
    }
}
