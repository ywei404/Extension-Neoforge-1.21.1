package com.walterwei314.extension.datacomponent;

import com.mojang.serialization.Codec;
import com.walterwei314.extension.Extensionneoforge1211;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModDataComponents {
    private ModDataComponents() {}

    public static final DeferredRegister.DataComponents COMPONENTS =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Extensionneoforge1211.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> ACTIVATED =
            COMPONENTS.registerComponentType(
                    "activated",
                    builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL)
            );
}
