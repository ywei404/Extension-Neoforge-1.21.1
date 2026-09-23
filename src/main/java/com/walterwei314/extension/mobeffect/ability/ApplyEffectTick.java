package com.walterwei314.extension.mobeffect.ability;

import com.walterwei314.extension.mobeffect.ModMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;

public final class ApplyEffectTick {
    private ApplyEffectTick() {}

    public static final Map<MobEffect, BiPredicate<LivingEntity, Integer>> TICK_EFFECTS = new HashMap<>();
    public static final Map<MobEffect, BiPredicate<Integer, Integer>> CAN_EFFECTS = new HashMap<>();
    public static final BiPredicate<Integer, Integer> EVERY_TICK = (duration, amplifier) -> true;

    public static void register(){
        TICK_EFFECTS.put(ModMobEffects.SUSTENANCE.get(), (entity, amplifier) -> {
            if (!(entity instanceof Player player)){ // applyEffectTick must run in Client Side
                return false;
            }

            float minExhaustion = -4.0F * (amplifier + 1);
            float currentExhaustion = player.getFoodData().getExhaustionLevel();

            if (currentExhaustion > minExhaustion) {
                player.causeFoodExhaustion(Math.max(-0.01F * (amplifier + 1), minExhaustion - currentExhaustion));
            }

            return true;
        });
        CAN_EFFECTS.put(ModMobEffects.SUSTENANCE.get(), EVERY_TICK);
    }
}
