package com.walterwei314.extension.attribute;

import com.walterwei314.extension.Extensionneoforge1211;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModAttributes {
    private ModAttributes() {}

    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, Extensionneoforge1211.MODID);

    public static final DeferredHolder<Attribute, Attribute> HEAL_MULTIPLIER = ATTRIBUTES.register(
            "heal_multiplier",
            () -> new BaseAttribute("heal_multiplier", 1.0, 0.0, Float.MAX_VALUE)
    );

    public static final DeferredHolder<Attribute, Attribute> INVULNERABILITY_TICKS = ATTRIBUTES.register(
            "invulnerability_ticks",
            () -> new BaseAttribute("invulnerability_ticks", 20.0, 0.0, Float.MAX_VALUE)
    );

    public static final DeferredHolder<Attribute, Attribute> ABSORPTION_GAIN_MULTIPLIER = ATTRIBUTES.register(
            "absorption_gain_multiplier",
            () -> new BaseAttribute("absorption_gain_multiplier", 1.0, 0.0, Float.MAX_VALUE)
    );

    public static final DeferredHolder<Attribute, Attribute> ABSORPTION_EFFECT_BASE_VALUE = ATTRIBUTES.register(
            "absorption_effect_base_value",
            () -> new BaseAttribute("absorption_effect_base_value", 4.0, 0.0, Float.MAX_VALUE)
    );
}
