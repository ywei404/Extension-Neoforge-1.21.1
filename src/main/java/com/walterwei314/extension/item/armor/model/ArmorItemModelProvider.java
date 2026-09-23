package com.walterwei314.extension.item.armor.model;

import com.walterwei314.extension.Extensionneoforge1211;
import com.walterwei314.extension.item.armor.ModArmorItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

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
        ModArmorItems.REFINED_DIAMOND_ARMOR_MAP.forEach((type, armorItem) -> {
            Item vanillaItem = switch (type){
                case ArmorItem.Type.HELMET -> Items.DIAMOND_HELMET;
                case ArmorItem.Type.CHESTPLATE -> Items.DIAMOND_CHESTPLATE;
                case ArmorItem.Type.LEGGINGS -> Items.DIAMOND_LEGGINGS;
                case ArmorItem.Type.BOOTS -> Items.DIAMOND_BOOTS;
                default -> throw new IllegalStateException("Unexpected armor type: " + type);
            };

            withExistingParent(armorItem.getId().getPath(), mcLoc("item/generated"))
                    .texture("layer0", BuiltInRegistries.ITEM.getKey(vanillaItem).withPrefix("item/"));
        });
    }
}
