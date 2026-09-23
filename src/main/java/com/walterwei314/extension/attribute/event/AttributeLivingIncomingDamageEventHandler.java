package com.walterwei314.extension.attribute.event;

import com.walterwei314.extension.attribute.ModAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber
public final class AttributeLivingIncomingDamageEventHandler {
    private AttributeLivingIncomingDamageEventHandler() {}

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void a(LivingIncomingDamageEvent event){
        LivingEntity entity = event.getEntity();

        if (entity.level().isClientSide()){
            return;
        }

        AttributeInstance instance = entity.getAttribute(ModAttributes.INVULNERABILITY_TICKS.getDelegate());

        if (instance == null){
            return;
        }

        event.getContainer().setPostAttackInvulnerabilityTicks((int) instance.getValue());
    }
}
