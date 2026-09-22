package com.walterwei314.extension.enchantment.tag;

import com.walterwei314.extension.Extensionneoforge1211;
import com.walterwei314.extension.enchantment.ModEnchantments;
import com.walterwei314.extension.util.ReflectionUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EnchantmentTags;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModEnchantmentTagProvider extends EnchantmentTagsProvider {

    public ModEnchantmentTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        ReflectionUtils.staticResourceKeyForEach(ModEnchantments.class, resourceKey -> {
            ResourceLocation location = resourceKey.location();

            if (!location.getNamespace().equals(Extensionneoforge1211.MODID)) {
                return;
            }

            tag(EnchantmentTags.IN_ENCHANTING_TABLE).addOptional(location);
        });

        tag(ModEnchantmentTags.FORTIFICATION_REINFORCEMENT_EXCLUSIVE)
                .addOptional(ModEnchantments.FORTIFICATION.location())
                .addOptional(ModEnchantments.REINFORCEMENT.location());
    }
}