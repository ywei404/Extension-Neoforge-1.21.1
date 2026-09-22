package com.walterwei314.extension.language;

import com.walterwei314.extension.Extensionneoforge1211;
import com.walterwei314.extension.enchantment.ModEnchantments;
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

public class ModEnglishLanguageProvider extends LanguageProvider {
    public ModEnglishLanguageProvider(PackOutput output) {
        super(output, Extensionneoforge1211.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        BuiltInRegistries.ITEM.forEach(item -> {
            ResourceLocation key = BuiltInRegistries.ITEM.getKey(item);
            String namespace = key.getNamespace();

            if (!namespace.equals(Extensionneoforge1211.MODID)){
                return;
            }

            add(item, StringUtils.fromPathToDisplayName(key.getPath()));
        });

        BuiltInRegistries.MOB_EFFECT.forEach(mobEffect -> {
            ResourceLocation key = BuiltInRegistries.MOB_EFFECT.getKey(mobEffect);
            String namespace = key.getNamespace();

            if (!namespace.equals(Extensionneoforge1211.MODID)){
                return;
            }

            add(mobEffect, StringUtils.fromPathToDisplayName(key.getPath()));
        });

        // Enchantments
        for (Field field : ModEnchantments.class.getDeclaredFields()) {

            if (!Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            if (!ResourceKey.class.isAssignableFrom(field.getType())) {
                continue;
            }

            try {
                Object value = field.get(null);

                if (!(value instanceof ResourceKey<?> resourceKey)) {
                    continue;
                }

                ResourceLocation key = resourceKey.location();

                if (!key.getNamespace().equals(Extensionneoforge1211.MODID)) {
                    continue;
                }

                add(
                        "enchantment." + key.getNamespace() + "." + key.getPath(),
                        StringUtils.fromPathToDisplayName(key.getPath())
                );

            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to generate language entry for " + field.getName(), e);
            }
        }
    }
}
