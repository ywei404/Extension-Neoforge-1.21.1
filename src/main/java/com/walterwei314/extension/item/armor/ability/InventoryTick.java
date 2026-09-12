package com.walterwei314.extension.item.armor.ability;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.walterwei314.extension.item.armor.ModArmorItems;
import com.walterwei314.extension.mobeffect.util.MobEffectUtils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredItem;
import org.apache.commons.lang3.function.TriConsumer;

import java.util.function.BiConsumer;

public final class InventoryTick {
    private InventoryTick() {}

    public static final Multimap<ArmorItem, TriConsumer<ItemStack, LivingEntity, Integer>> TICKS =
            HashMultimap.create();

    public static void register() {
        ModArmorItems.WOODEN_ARMOR_MAP.forEach((type, armorItemDeferredItem) -> TICKS.put(armorItemDeferredItem.get(),
                (k, v, s) -> MobEffectUtils.addEffect(v, MobEffects.HUNGER, 600, 0, MobEffectUtils.ADD_BEFORE_EXPIRATION)));
        ModArmorItems.STONE_ARMOR_MAP.forEach((type, armorItemDeferredItem) -> TICKS.put(armorItemDeferredItem.get(),
                (k, v, s) -> MobEffectUtils.addEffect(v, MobEffects.MOVEMENT_SLOWDOWN, 600, 0, MobEffectUtils.ADD_BEFORE_EXPIRATION)));
    }
}
