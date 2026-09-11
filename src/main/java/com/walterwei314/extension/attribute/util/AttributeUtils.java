package com.walterwei314.extension.attribute.util;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

import java.util.function.Consumer;

public final class AttributeUtils {
    private AttributeUtils() {}

    public static <E extends LivingEvent> void applyIfHasAttributeOnServer(
            E event,
            Holder<Attribute> attributeHolder,
            Consumer<AttributeInstance> attributeConsumer
    ) {
        if (event == null || attributeHolder == null || attributeConsumer == null) {
            return;
        }

        LivingEntity entity = event.getEntity();

        if (entity.level().isClientSide()) {
            return;
        }

        AttributeInstance attributeInstance = entity.getAttribute(attributeHolder);

        if (attributeInstance == null) {
            return;
        }

        attributeConsumer.accept(attributeInstance);
    }
}
