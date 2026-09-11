package com.walterwei314.extension;

import com.walterwei314.extension.attribute.ModAttributes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber
public class Test {
    @SubscribeEvent
    public static void test(EntityTickEvent.Pre event){
        Entity entity = event.getEntity();

        if (entity instanceof Player player && !entity.level().isClientSide() && entity.level().getGameTime() % 20 == 0){
            AttributeInstance instance = player.getAttribute(ModAttributes.HEAL_MULTIPLIER);

            if (instance != null){
                System.out.println("heal_multiplier: " + instance.getValue());
            }
        }
    }
}
