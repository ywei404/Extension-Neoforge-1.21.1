package com.walterwei314.extension.enchantment.effect;

import com.walterwei314.extension.enchantment.EnchantmentUtils;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentAttributeEffect;

public final class ModEnchantmentEffects {
    private ModEnchantmentEffects() {}

    public static final EnchantmentAttributeEffect FORTIFICATION_EFFECT =
            EnchantmentUtils.createAttributeEffect("fortification", Attributes.ARMOR_TOUGHNESS, LevelBasedValue.perLevel(0.5F), AttributeModifier.Operation.ADD_VALUE);
    public static final EnchantmentAttributeEffect REINFORCEMENT_EFFECT =
            EnchantmentUtils.createAttributeEffect("reinforcement", Attributes.ARMOR, LevelBasedValue.perLevel(1.0F), AttributeModifier.Operation.ADD_VALUE);
    public static final EnchantmentAttributeEffect VITALITY_EFFECT =
            EnchantmentUtils.createAttributeEffect("vitality", Attributes.MAX_HEALTH, LevelBasedValue.perLevel(2.0F), AttributeModifier.Operation.ADD_VALUE);
}
