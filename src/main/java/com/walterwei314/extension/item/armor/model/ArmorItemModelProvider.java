package com.walterwei314.extension.item.armor.model;

import com.walterwei314.extension.Extensionneoforge1211;
import com.walterwei314.extension.item.armor.BaseArmor;
import com.walterwei314.extension.item.armor.ModArmorItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.function.BiConsumer;

public class ArmorItemModelProvider extends ItemModelProvider {

    public ArmorItemModelProvider(
            PackOutput output,
            ExistingFileHelper existingFileHelper
    ) {
        super(
                output,
                Extensionneoforge1211.MODID,
                existingFileHelper
        );
    }

    @Override
    protected void registerModels() {
//        BuiltInRegistries.ITEM.forEach(item -> {
//            ResourceLocation key =
//                    BuiltInRegistries.ITEM.getKey(item);
//
//            if (key.getNamespace().equals(Extensionneoforge1211.MODID) && item instanceof BaseArmor) {
//                basicItem(item);
//            }
//        });

        ModArmorItems.DIRT_ARMOR_MAP.values().forEach(item -> basicItem(item.get()));
        ModArmorItems.EMERALD_ARMOR_MAP.values().forEach(item -> basicItem(item.get()));
        ModArmorItems.WOODEN_ARMOR_MAP.values().forEach(item -> basicItem(item.get()));
        ModArmorItems.STONE_ARMOR_MAP.values().forEach(item -> basicItem(item.get()));
        ModArmorItems.OBSIDIAN_ARMOR_MAP.values().forEach(item -> basicItem(item.get()));
        ModArmorItems.GOLDEN_APPLE_ARMOR_MAP.values().forEach(item -> basicItem(item.get()));
        ModArmorItems.ENCHANTED_GOLDEN_APPLE_ARMOR_MAP.forEach((type, enchantedItem) -> {
            DeferredItem<ArmorItem> normalItem = ModArmorItems.GOLDEN_APPLE_ARMOR_MAP.get(type);

            withExistingParent(enchantedItem.getId().getPath(), mcLoc("item/generated"))
                    .texture("layer0", modLoc("item/" + normalItem.getId().getPath()));
        });
    }
}
