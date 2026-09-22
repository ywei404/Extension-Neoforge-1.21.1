package com.walterwei314.extension.enchantment;

import com.walterwei314.extension.Extensionneoforge1211;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentAttributeEffect;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.function.Supplier;

public final class EnchantmentUtils {
    private EnchantmentUtils() {}

    public static EnchantmentAttributeEffect createAttributeEffect(
            String name,
            Holder<Attribute> attribute,
            LevelBasedValue levelBasedValue,
            AttributeModifier.Operation operation
    ) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Effect name cannot be blank");
        }

        Objects.requireNonNull(attribute, "attribute cannot be null");
        Objects.requireNonNull(levelBasedValue, "LevelBasedValue cannot be null");
        Objects.requireNonNull(operation, "operation cannot be null");

        return new EnchantmentAttributeEffect(
                ResourceLocation.fromNamespaceAndPath(Extensionneoforge1211.MODID, "enchantment." + name),
                attribute, levelBasedValue, operation
        );
    }
}
