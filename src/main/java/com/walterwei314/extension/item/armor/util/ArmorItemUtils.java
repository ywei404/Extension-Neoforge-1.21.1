package com.walterwei314.extension.item.armor.util;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

public final class ArmorItemUtils {

    private ArmorItemUtils() {
    }

    private static final double HELMET_RATIO = 5.0 / 24.0;
    private static final double CHESTPLATE_RATIO = 8.0 / 24.0;
    private static final double LEGGINGS_RATIO = 7.0 / 24.0;
    private static final double BOOTS_RATIO = 4.0 / 24.0;

    public static Map<ArmorItem.Type, Integer> distributeArmor(int totalArmor) {
        if (totalArmor < 4) {
            throw new IllegalArgumentException(
                    "totalArmor must be at least 4"
            );
        }

        // Special cases
        if (totalArmor == 4) {
            return createArmorMap(1, 1, 1, 1);
        }

        if (totalArmor == 5) {
            return createArmorMap(1, 2, 1, 1);
        }

        // Dynamic allocation for 6+
        int bestHelmet = 0;
        int bestChestplate = 0;
        int bestLeggings = 0;
        int bestBoots = 0;

        double bestError = Double.MAX_VALUE;

        for (int helmet = 1; helmet < totalArmor; helmet++) {
            for (int chestplate = 1; chestplate < totalArmor; chestplate++) {
                for (int leggings = 1; leggings < totalArmor; leggings++) {

                    int boots =
                            totalArmor
                                    - helmet
                                    - chestplate
                                    - leggings;

                    if (boots < 1) {
                        continue;
                    }

                    // Helmet and boots must both be weaker
                    // than chestplate and leggings.
                    if (helmet >= chestplate
                            || helmet >= leggings
                            || boots >= chestplate
                            || boots >= leggings) {
                        continue;
                    }

                    double targetHelmet =
                            totalArmor * HELMET_RATIO;

                    double targetChestplate =
                            totalArmor * CHESTPLATE_RATIO;

                    double targetLeggings =
                            totalArmor * LEGGINGS_RATIO;

                    double targetBoots =
                            totalArmor * BOOTS_RATIO;

                    double error =
                            square(helmet - targetHelmet)
                                    + square(chestplate - targetChestplate)
                                    + square(leggings - targetLeggings)
                                    + square(boots - targetBoots);

                    if (error < bestError) {
                        bestError = error;

                        bestHelmet = helmet;
                        bestChestplate = chestplate;
                        bestLeggings = leggings;
                        bestBoots = boots;
                    }
                }
            }
        }

        if (bestError == Double.MAX_VALUE) {
            throw new IllegalStateException(
                    "Cannot find valid armor distribution for totalArmor = "
                            + totalArmor
            );
        }

        return createArmorMap(
                bestHelmet,
                bestChestplate,
                bestLeggings,
                bestBoots
        );
    }

    public static Map<ArmorItem.Type, Integer> createArmorMap(
            int helmet,
            int chestplate,
            int leggings,
            int boots
    ) {
        Map<ArmorItem.Type, Integer> result =
                new EnumMap<>(ArmorItem.Type.class);

        result.put(ArmorItem.Type.HELMET, helmet);
        result.put(ArmorItem.Type.CHESTPLATE, chestplate);
        result.put(ArmorItem.Type.LEGGINGS, leggings);
        result.put(ArmorItem.Type.BOOTS, boots);

        return result;
    }

    private static double square(double value) {
        return value * value;
    }

    public static boolean isWearingFullSet(Player player, Map<ArmorItem.Type, ArmorItem> armorItems){
        if (player == null || player.level().isClientSide() || armorItems == null || armorItems.isEmpty()) {
            return false;
        }

        return armorItems.entrySet().stream().allMatch(entry ->
                player.getItemBySlot(entry.getKey().getSlot()).is(entry.getValue()));
    }

    public static Map<ArmorItem.Type, ArmorItem> getArmorItemMap(Map<ArmorItem.Type, DeferredItem<ArmorItem>> deferredItemMap){
        Map<ArmorItem.Type, ArmorItem> result = new EnumMap<>(ArmorItem.Type.class);

        if (deferredItemMap == null || deferredItemMap.isEmpty()) {
            return result;
        }

        for (Map.Entry<ArmorItem.Type, DeferredItem<ArmorItem>> entry : deferredItemMap.entrySet()) {
            result.put(entry.getKey(), entry.getValue().get());
        }

        return result;
    }

    public static Map<ArmorItem.Type, ArmorItem> getCurrentArmorItemMap(Player player) {
        EnumMap<ArmorItem.Type, ArmorItem> result =
                new EnumMap<>(ArmorItem.Type.class);

        if (player == null || player.level().isClientSide()) {
            return result;
        }

        for (ArmorItem.Type type : ArmorItem.Type.values()) {
            Item item = player.getItemBySlot(type.getSlot()).getItem();

            if (!type.hasTrims() || !(item instanceof ArmorItem armorItem)) {
                continue;
            }

            result.put(type, armorItem);
        }

        return result;
    }

    public static <T> boolean isAllArmorsContainSameDataComponent(
            Player player,
            DeferredHolder<DataComponentType<?>, DataComponentType<T>> dataComponent,
            T containValue
    ) {
        if (player == null || dataComponent == null || containValue == null) {
            return false;
        }

        for (ItemStack armorStack : player.getArmorSlots()) {
            if (!containValue.equals(armorStack.get(dataComponent.get()))) {
                return false;
            }
        }

        return true;
    }
}