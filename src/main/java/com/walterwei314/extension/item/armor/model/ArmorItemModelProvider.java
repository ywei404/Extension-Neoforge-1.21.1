package com.walterwei314.extension.item.armor.model;

import com.walterwei314.extension.Extensionneoforge1211;
import com.walterwei314.extension.item.armor.BaseArmor;
import com.walterwei314.extension.item.armor.ModArmorItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

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

        ModArmorItems.EMERALD_ARMOR_MAP.values().forEach(item -> basicItem(item.get()));
    }
}
