package com.walterwei314.extension.attribute.event;

import com.walterwei314.extension.attribute.ModAttributes;
import com.walterwei314.extension.attribute.util.AttributeUtils;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;

@EventBusSubscriber
public class AttributeLivingHealEventHandler {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void healMultiplier(LivingHealEvent event) {
        AttributeUtils.applyIfHasAttributeOnServer(event, ModAttributes.HEAL_MULTIPLIER.getDelegate(),
                attributeInstance -> {
                    double heal_multiplier = attributeInstance.getValue();
                    event.setAmount((float) (event.getAmount() * heal_multiplier));
                }
        );
    }
}
