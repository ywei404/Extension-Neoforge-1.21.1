package com.walterwei314.extension.language;

import com.walterwei314.extension.Extensionneoforge1211;
import com.walterwei314.extension.enchantment.ModEnchantments;
import com.walterwei314.extension.util.ModUtils;
import com.walterwei314.extension.util.ReflectionUtils;
import com.walterwei314.extension.util.StringUtils;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.function.Consumer;

public class ModEnglishLanguageProvider extends LanguageProvider {
    public ModEnglishLanguageProvider(PackOutput output) {
        super(output, Extensionneoforge1211.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        BuiltInRegistries.ITEM.forEach(item -> {
            ResourceLocation key = BuiltInRegistries.ITEM.getKey(item);

            if (!ModUtils.isKeyInThisMod(key)){
                return;
            }

            add(item, StringUtils.fromPathToDisplayName(key.getPath()));
        });

        BuiltInRegistries.MOB_EFFECT.forEach(mobEffect -> {
            ResourceLocation key = BuiltInRegistries.MOB_EFFECT.getKey(mobEffect);

            if (!ModUtils.isKeyInThisMod(key)){
                return;
            }

            add(mobEffect, StringUtils.fromPathToDisplayName(key.getPath()));
        });

        // Enchantments
        ReflectionUtils.staticResourceKeyForEach(ModEnchantments.class, resourceKey -> {
            ResourceLocation key = resourceKey.location();

            if (!ModUtils.isKeyInThisMod(key)) {
                return;
            }

            add(
                    "enchantment." + key.getNamespace() + "." + key.getPath(),
                    StringUtils.fromPathToDisplayName(key.getPath())
            );
        });
    }
}
