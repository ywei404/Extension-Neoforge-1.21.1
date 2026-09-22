package com.walterwei314.extension.item.armor.ability;

import com.walterwei314.extension.item.armor.ModArmorItems;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public final class IsFoil {
    private IsFoil() {}

    public static final Map<ArmorItem, Predicate<ItemStack>> FOIL = new HashMap<>();

    public static void register() {
        ModArmorItems.ENCHANTED_GOLDEN_APPLE_ARMOR_MAP.values().forEach((armorItemDeferredItem) ->
                FOIL.put(armorItemDeferredItem.get(), itemStack -> true));
    }
}
