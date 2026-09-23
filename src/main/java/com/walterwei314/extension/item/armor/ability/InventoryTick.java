package com.walterwei314.extension.item.armor.ability;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.walterwei314.extension.item.armor.ModArmorItems;
import com.walterwei314.extension.item.armor.util.ArmorItemUtils;
import com.walterwei314.extension.mobeffect.ModMobEffects;
import com.walterwei314.extension.mobeffect.util.MobEffectUtils;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.EffectCure;
import net.neoforged.neoforge.common.extensions.IMobEffectExtension;
import net.neoforged.neoforge.registries.DeferredItem;
import org.apache.commons.lang3.function.TriConsumer;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class InventoryTick {
    private InventoryTick() {
    }

    public static final Multimap<ArmorItem, TriConsumer<ItemStack, LivingEntity, Integer>> TICKS = HashMultimap.create();
    public static final Multimap<Map<ArmorItem.Type, ArmorItem>, BiConsumer<Player, Long>> FULL_SET_TICKS = HashMultimap.create();
    public static final Map<Map<ArmorItem.Type, ArmorItem>, Map<UUID, Long>> TICK_COUNTS = new HashMap<>();

    public static void register() {
        ModArmorItems.WOODEN_ARMOR_MAP.forEach((type, armorItemDeferredItem) -> TICKS.put(armorItemDeferredItem.get(),
                (k, v, s) -> MobEffectUtils.addEffect(v, MobEffects.HUNGER, 600, 0, MobEffectUtils.ADD_BEFORE_EXPIRATION)));
        ModArmorItems.STONE_ARMOR_MAP.forEach((type, armorItemDeferredItem) -> TICKS.put(armorItemDeferredItem.get(),
                (k, v, s) -> MobEffectUtils.addEffect(v, MobEffects.MOVEMENT_SLOWDOWN, 600, 0, MobEffectUtils.ADD_BEFORE_EXPIRATION)));
        ModArmorItems.OBSIDIAN_ARMOR_MAP.forEach((type, armorItemDeferredItem) -> TICKS.put(armorItemDeferredItem.get(),
                (k, v, s) -> MobEffectUtils.addEffect(v, MobEffects.MOVEMENT_SLOWDOWN, 100, 0, MobEffectUtils.ADD_BEFORE_EXPIRATION)));
        FULL_SET_TICKS.put(ArmorItemUtils.getArmorItemMap(ModArmorItems.GOLDEN_APPLE_ARMOR_MAP),
                (player, tickCount) -> {
                    MobEffectUtils.addEffect(player, MobEffects.REGENERATION, 100, 0, MobEffectUtils.ADD_BEFORE_EXPIRATION);
                    MobEffectUtils.addEffect(player, ModMobEffects.SUSTENANCE, 100, 0, MobEffectUtils.ADD_BEFORE_EXPIRATION);
                });
        FULL_SET_TICKS.put(ArmorItemUtils.getArmorItemMap(ModArmorItems.ENCHANTED_GOLDEN_APPLE_ARMOR_MAP),
                (player, tickCount) -> {
                    MobEffectUtils.addEffect(player, MobEffects.REGENERATION, 600, 2, MobEffectUtils.ADD_BEFORE_EXPIRATION);
                    MobEffectUtils.addEffect(player, ModMobEffects.SUSTENANCE, 600, 4, MobEffectUtils.ADD_BEFORE_EXPIRATION);
                    MobEffectUtils.addEffect(player, MobEffects.SATURATION, 600, 0, MobEffectUtils.ADD_BEFORE_EXPIRATION);
                    MobEffectUtils.addEffect(player, ModMobEffects.HEAL_BOOST, 600, 0, MobEffectUtils.ADD_BEFORE_EXPIRATION);
                    MobEffectUtils.addEffect(player, MobEffects.DAMAGE_RESISTANCE, 6000, 1, MobEffectUtils.ADD_BEFORE_EXPIRATION);
                    MobEffectUtils.addEffect(player, MobEffects.FIRE_RESISTANCE, 6000, 1, MobEffectUtils.ADD_BEFORE_EXPIRATION);
                    MobEffectUtils.addEffect(player, MobEffects.WATER_BREATHING, 6000, 1, MobEffectUtils.ADD_BEFORE_EXPIRATION);
                    MobEffectUtils.addEffect(player, MobEffects.NIGHT_VISION, 6000, 1, MobEffectUtils.ADD_BEFORE_EXPIRATION);
                    MobEffectUtils.addEffectEveryTicks(player, MobEffects.ABSORPTION, 2400, 4, 600, tickCount, MobEffectUtils.ALWAYS_ADD);
                    MobEffectUtils.removeBadEffects(player);
                });
    }
}
