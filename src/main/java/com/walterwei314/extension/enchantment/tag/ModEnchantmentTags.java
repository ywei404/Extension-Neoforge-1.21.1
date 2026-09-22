package com.walterwei314.extension.enchantment.tag;

import com.walterwei314.extension.Extensionneoforge1211;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;

public final class ModEnchantmentTags {
    private ModEnchantmentTags() {}

    public static final TagKey<Enchantment> FORTIFICATION_REINFORCEMENT_EXCLUSIVE =
            TagKey.create(
                    Registries.ENCHANTMENT,
                    ResourceLocation.fromNamespaceAndPath(Extensionneoforge1211.MODID, "exclusive_set/fortification_reinforcement")
            );

}
