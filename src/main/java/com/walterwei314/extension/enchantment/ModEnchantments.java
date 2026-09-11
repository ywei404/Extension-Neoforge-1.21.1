package com.walterwei314.extension.enchantment;

import com.walterwei314.extension.Extensionneoforge1211;
import com.walterwei314.extension.attribute.ModAttributes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentAttributeEffect;

public final class ModEnchantments {
    private ModEnchantments() {
    }

    public static final ResourceKey<Enchantment> SHAN_JIA = ResourceKey.create(
            Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(Extensionneoforge1211.MODID, "shan_jia")
    );

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Item> items = context.lookup(Registries.ITEM);

        context.register(SHAN_JIA, Enchantment.enchantment(Enchantment.definition(
                                items.getOrThrow(ItemTags.ARMOR_ENCHANTABLE), // 可以附魔到所有盔甲
                                5, // weight
                                4, // max level
                                Enchantment.dynamicCost(5, 8), // minimum enchanting cost
                                Enchantment.dynamicCost(25, 8), // maximum enchanting cost
                                2, // anvil cost
                                EquipmentSlotGroup.ARMOR // 生效装备槽
                        ))
                        .withEffect(EnchantmentEffectComponents.ATTRIBUTES, // Type of effect
                                new EnchantmentAttributeEffect(
                                        ResourceLocation.fromNamespaceAndPath(
                                                Extensionneoforge1211.MODID,
                                                "enchantment.shan_jia"
                                        ), // enchantment id
                                        ModAttributes.HEAL_MULTIPLIER, // attribute
                                        LevelBasedValue.perLevel(0.1F), // value
                                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE // operation
                                ))
                        .build(SHAN_JIA.location()) // creation
        );
    }
}
