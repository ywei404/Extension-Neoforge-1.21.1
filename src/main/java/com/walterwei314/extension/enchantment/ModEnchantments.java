package com.walterwei314.extension.enchantment;

import com.walterwei314.extension.Extensionneoforge1211;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

public final class ModEnchantments {

    private ModEnchantments() {
    }

    public static final ResourceKey<Enchantment> FORTIFICATION =
            ResourceKey.create(
                    Registries.ENCHANTMENT,
                    ResourceLocation.fromNamespaceAndPath(Extensionneoforge1211.MODID, "fortification")
            );
    public static final ResourceKey<Enchantment> REINFORCEMENT =
            ResourceKey.create(
                    Registries.ENCHANTMENT,
                    ResourceLocation.fromNamespaceAndPath(Extensionneoforge1211.MODID, "reinforcement")
            );
    public static final ResourceKey<Enchantment> VITALITY =
            ResourceKey.create(
                    Registries.ENCHANTMENT,
                    ResourceLocation.fromNamespaceAndPath(Extensionneoforge1211.MODID, "vitality")
            );
}