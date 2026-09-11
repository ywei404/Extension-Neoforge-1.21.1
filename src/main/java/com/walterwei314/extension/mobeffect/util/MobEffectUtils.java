package com.walterwei314.extension.mobeffect.util;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

import java.util.function.Consumer;

public final class MobEffectUtils {
    private MobEffectUtils() {
    }

    public static <E extends LivingEvent> void applyIfHasMobEffectOnServer(
            E event,
            Holder<MobEffect> effectHolder,
            Consumer<MobEffectInstance> effectApplier
    ) {
        if (event == null || effectHolder == null || effectApplier == null) {
            return;
        }

        LivingEntity entity = event.getEntity();

        if (entity.level().isClientSide()) {
            return;
        }

        MobEffectInstance mobEffectInstance = entity.getEffect(effectHolder);

        if (mobEffectInstance == null) {
            return;
        }

        // TODO: fix the bug

        effectApplier.accept(mobEffectInstance);
    }

    public static <E extends MobEffectEvent> void applyIfEventMatchesMobEffectOnServer(
            E event,
            Holder<MobEffect> effectHolder,
            Consumer<MobEffectInstance> effectConsumer
    ) {
        if (event == null || effectHolder == null || effectConsumer == null) {
            return;
        }

        LivingEntity entity = event.getEntity();

        if (entity.level().isClientSide()) {
            return;
        }

        MobEffectInstance mobEffectInstance = event.getEffectInstance();

        if (mobEffectInstance == null) {
            return;
        }

        if (!mobEffectInstance.is(effectHolder)) {
            return;
        }

        effectConsumer.accept(mobEffectInstance);
    }
}
