package com.walterwei314.extension.mobeffect;

import com.walterwei314.extension.mobeffect.ability.ApplyEffectTick;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiPredicate;

public class BaseMobEffect extends MobEffect {
    public BaseMobEffect(MobEffectCategory category, int color, ParticleOptions particle) {
        super(category, color, particle);
    }

    public BaseMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void addAttributeModifiers(@NotNull AttributeMap attributeMap, int amplifier) {
        super.addAttributeModifiers(attributeMap, amplifier);
    }

    @Override
    public void removeAttributeModifiers(@NotNull AttributeMap attributeMap) {
        super.removeAttributeModifiers(attributeMap);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        BiPredicate<Integer, Integer> canApplyEffect = ApplyEffectTick.CAN_EFFECTS.get(this);

        if (canApplyEffect == null) {
            return super.shouldApplyEffectTickThisTick(duration, amplifier);
        }

        return canApplyEffect.test(duration, amplifier);
    }

    @Override
    public boolean applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        BiPredicate<LivingEntity, Integer> tickEffect = ApplyEffectTick.TICK_EFFECTS.get(this);

        if (tickEffect == null) {
            return super.applyEffectTick(livingEntity, amplifier);
        }

        return tickEffect.test(livingEntity, amplifier);
    }
}
