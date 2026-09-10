package com.walterwei314.extension.enchantment.registry;

import com.walterwei314.extension.enchantment.ModEnchantments;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public final class EnchantmentRegistrySets {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.ENCHANTMENT, ModEnchantments::bootstrap);

    private EnchantmentRegistrySets() {}
}