package com.walterwei314.extension.mobeffect;

import com.walterwei314.extension.Extensionneoforge1211;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModMobEffects {
    private ModMobEffects() {
    }

    // Create a Deferred Register to hold MobEffects which will all be registered under the "extension" namespace
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, Extensionneoforge1211.MODID);

    public static final DeferredHolder<MobEffect, MobEffect> HEAL_BOOST = MOB_EFFECTS.register(
            "heal_boost",
            () -> new BaseMobEffect(MobEffectCategory.BENEFICIAL, 0x00FF00)
    );
}
