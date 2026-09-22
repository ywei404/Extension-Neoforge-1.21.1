package com.walterwei314.extension.mobeffect.util;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.util.TriPredicate;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

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

    public static final TriPredicate<MobEffectInstance, Integer, Integer> ALWAYS_ADD =
            (mobEffectInstance, duration, amplifier) -> true;
    public static final TriPredicate<MobEffectInstance, Integer, Integer> ADD_BEFORE_EXPIRATION =
            (mobEffectInstance, duration, amplifier) ->
                    mobEffectInstance.getDuration() <= 1 || mobEffectInstance.getAmplifier() < amplifier;

    public static<T extends Collection<? extends Holder<MobEffect>>> void removeEffects(LivingEntity entity, T effectsToRemove) {
        if (entity == null
                || entity.level().isClientSide()
                || effectsToRemove == null
                || effectsToRemove.isEmpty()
        ) {
            return;
        }

        effectsToRemove.forEach(entity::removeEffect);
    }

    public static void removeBadEffects(LivingEntity entity) {
        if (entity == null || entity.level().isClientSide()) {
            return;
        }

        List<Holder<MobEffect>> activeBadEffects = entity.getActiveEffects().stream()
                .filter(mobEffectInstance -> mobEffectInstance.getEffect().value().getCategory() == MobEffectCategory.HARMFUL)
                .map(mobEffectInstance -> mobEffectInstance.getEffect().getDelegate()).toList();

        removeEffects(entity, activeBadEffects);
    }

    public static void addEffect(LivingEntity entity, Holder<MobEffect> effect, int duration, int amplifier, TriPredicate<MobEffectInstance, Integer, Integer> condition) {
        if (entity == null
                || entity.level().isClientSide()
                || effect == null
                || condition == null
                || duration <= 0
                || amplifier < 0
        ) {
            return;
        }

        MobEffectInstance previousEffectInstance = entity.getEffect(effect);

        if (previousEffectInstance != null && !condition.test(previousEffectInstance, duration, amplifier)) {
            return;
        }

        entity.addEffect(new MobEffectInstance(effect, duration, amplifier));
    }

    public static void addEffectEveryTicks(LivingEntity entity, Holder<MobEffect> effect, int duration, int amplifier, int interval, long time, TriPredicate<MobEffectInstance, Integer, Integer> condition) {
        if (effect == null || entity.level().isClientSide() || interval <= 0 || time < 0) {
            return;
        }

        if (time % interval == 0) {
            addEffect(entity, effect, duration, amplifier, condition);
        }
    }

    public static void addEffectEveryTicks(LivingEntity entity, Holder<MobEffect> effect, int duration, int amplifier, int interval, TriPredicate<MobEffectInstance, Integer, Integer> condition) {
        if (effect == null || entity.level().isClientSide()) {
            return;
        }

        addEffectEveryTicks(entity, effect, duration, amplifier, interval, entity.level().getGameTime(), condition);
    }
}
