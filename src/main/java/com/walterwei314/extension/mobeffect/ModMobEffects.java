package com.walterwei314.extension.mobeffect;

import com.walterwei314.extension.Extensionneoforge1211;
import com.walterwei314.extension.attribute.ModAttributes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
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
                    .addAttributeModifier(
                            ModAttributes.HEAL_MULTIPLIER.getDelegate(),
                            ResourceLocation.fromNamespaceAndPath(Extensionneoforge1211.MODID, "effect.heal_boost"),
                            0.5D, AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                    )
    );

    public static final DeferredHolder<MobEffect, MobEffect> SUSTENANCE = MOB_EFFECTS.register(
            "sustenance", () -> new BaseMobEffect(MobEffectCategory.BENEFICIAL, 0xFFD700)
    );

    public static final DeferredHolder<MobEffect, MobEffect> RESILIENCE = MOB_EFFECTS.register(
            "resilience",
            () -> new BaseMobEffect(MobEffectCategory.BENEFICIAL, 0x4A90E2)
                    .addAttributeModifier(
                            ModAttributes.INVULNERABILITY_TICKS.getDelegate(),
                            ResourceLocation.fromNamespaceAndPath(Extensionneoforge1211.MODID, "effect.resilience"),
                            4.0D, AttributeModifier.Operation.ADD_VALUE
                    )
    );

    public static final DeferredHolder<MobEffect, MobEffect> VULNERABILITY = MOB_EFFECTS.register(
            "vulnerability",
            () -> new BaseMobEffect(MobEffectCategory.HARMFUL, 0x9B59B6)
                    .addAttributeModifier(
                            ModAttributes.INVULNERABILITY_TICKS.getDelegate(),
                            ResourceLocation.fromNamespaceAndPath(Extensionneoforge1211.MODID, "effect.vulnerability"),
                            -1.5D, AttributeModifier.Operation.ADD_VALUE
                    )
    );
}
