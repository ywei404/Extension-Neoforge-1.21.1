package com.walterwei314.extension.enchantment.registry;

import com.walterwei314.extension.enchantment.effect.ModEnchantmentEffects;
import com.walterwei314.extension.enchantment.tag.ModEnchantmentTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;

import static com.walterwei314.extension.enchantment.ModEnchantments.*;

public final class EnchantmentRegistrySets {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.ENCHANTMENT, EnchantmentRegistrySets::bootstrap);

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Item> items = context.lookup(Registries.ITEM);
        HolderGetter<Enchantment> enchantments = context.lookup(Registries.ENCHANTMENT);
        HolderSet<Item> armorEnchantable = items.getOrThrow(ItemTags.ARMOR_ENCHANTABLE);

        context.register(
                FORTIFICATION,
                Enchantment.enchantment(Enchantment.definition(// supported_items, primary_items
                                armorEnchantable, armorEnchantable,
                                5, 4,
                                Enchantment.dynamicCost(1, 11),
                                Enchantment.dynamicCost(12, 11),
                                2, EquipmentSlotGroup.ARMOR
                        )).exclusiveWith(enchantments.getOrThrow(ModEnchantmentTags.FORTIFICATION_REINFORCEMENT_EXCLUSIVE))
                        .withEffect(EnchantmentEffectComponents.ATTRIBUTES, ModEnchantmentEffects.FORTIFICATION_EFFECT).build(FORTIFICATION.location())
        );

        context.register(
                REINFORCEMENT,
                Enchantment.enchantment(Enchantment.definition(// supported_items, primary_items
                                armorEnchantable, armorEnchantable,
                                5, 4,
                                Enchantment.dynamicCost(1, 11),
                                Enchantment.dynamicCost(12, 11),
                                2, EquipmentSlotGroup.ARMOR
                        )).exclusiveWith(enchantments.getOrThrow(ModEnchantmentTags.FORTIFICATION_REINFORCEMENT_EXCLUSIVE))
                        .withEffect(EnchantmentEffectComponents.ATTRIBUTES, ModEnchantmentEffects.REINFORCEMENT_EFFECT).build(REINFORCEMENT.location())
        );

        context.register(
                VITALITY,
                Enchantment.enchantment(Enchantment.definition(// supported_items, primary_items
                                armorEnchantable, armorEnchantable,
                                5, 4,
                                Enchantment.dynamicCost(1, 11),
                                Enchantment.dynamicCost(12, 11),
                                2, EquipmentSlotGroup.ARMOR
                        )).withEffect(EnchantmentEffectComponents.ATTRIBUTES, ModEnchantmentEffects.VITALITY_EFFECT).build(VITALITY.location())
        );
    }

    private EnchantmentRegistrySets() {
    }
}