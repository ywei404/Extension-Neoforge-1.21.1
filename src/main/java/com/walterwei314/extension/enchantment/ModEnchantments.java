package com.walterwei314.extension.enchantment;

import com.walterwei314.extension.Extensionneoforge1211;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
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

    public static final ResourceKey<Enchantment> FORTIFICATION =
            ResourceKey.create(
                    Registries.ENCHANTMENT,
                    ResourceLocation.fromNamespaceAndPath(
                            Extensionneoforge1211.MODID,
                            "fortification"
                    )
            );

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Item> items = context.lookup(Registries.ITEM);

        HolderSet<Item> armorEnchantable =
                items.getOrThrow(ItemTags.ARMOR_ENCHANTABLE);

        context.register(
                FORTIFICATION,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        armorEnchantable, // supported_items
                                        armorEnchantable, // primary_items
                                        5,                // weight
                                        4,                // max level
                                        Enchantment.dynamicCost(1, 11),
                                        Enchantment.dynamicCost(12, 11),
                                        2,
                                        EquipmentSlotGroup.ARMOR
                                )
                        )
                        .withEffect(
                                EnchantmentEffectComponents.ATTRIBUTES,
                                new EnchantmentAttributeEffect(
                                        ResourceLocation.fromNamespaceAndPath(
                                                Extensionneoforge1211.MODID,
                                                "enchantment.fortification"
                                        ),
                                        Attributes.ARMOR_TOUGHNESS,
                                        LevelBasedValue.perLevel(0.5F),
                                        AttributeModifier.Operation.ADD_VALUE
                                )
                        )
                        .build(FORTIFICATION.location())
        );
    }
}