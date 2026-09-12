package com.walterwei314.extension.item.armor;

import com.walterwei314.extension.Extensionneoforge1211;
import com.walterwei314.extension.item.armor.material.ModArmorMaterials;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Map;

public final class ModArmorItems {

    private ModArmorItems() {
    }

    public static final DeferredRegister.Items ARMOR_ITEMS =
            DeferredRegister.createItems(Extensionneoforge1211.MODID);

    public static final Map<ArmorItem.Type, DeferredItem<ArmorItem>> DIRT_ARMOR_MAP =
            registerArmorSet("dirt", ModArmorMaterials.DIRT_ARMOR_MATERIAL.getDelegate(), 3);
    public static final Map<ArmorItem.Type, DeferredItem<ArmorItem>> WOODEN_ARMOR_MAP =
            registerArmorSet("wooden", ModArmorMaterials.WOODEN_ARMOR_MATERIAL.getDelegate(), 6);
    public static final Map<ArmorItem.Type, DeferredItem<ArmorItem>> STONE_ARMOR_MAP =
            registerArmorSet("stone", ModArmorMaterials.STONE_ARMOR_MATERIAL.getDelegate(), 13);

    private static Map<ArmorItem.Type, DeferredItem<ArmorItem>> registerArmorSet(
            String name,
            Holder<ArmorMaterial> material,
            int durabilityMultiplier
    ) {
        return Map.of(
                ArmorItem.Type.HELMET, registerArmorItem(name + "_helmet", material, ArmorItem.Type.HELMET, durabilityMultiplier),
                ArmorItem.Type.CHESTPLATE, registerArmorItem(name + "_chestplate", material, ArmorItem.Type.CHESTPLATE, durabilityMultiplier),
                ArmorItem.Type.LEGGINGS, registerArmorItem(name + "_leggings", material, ArmorItem.Type.LEGGINGS, durabilityMultiplier),
                ArmorItem.Type.BOOTS, registerArmorItem(name + "_boots", material, ArmorItem.Type.BOOTS, durabilityMultiplier)
        );
    }

    private static DeferredItem<ArmorItem> registerArmorItem(
            String name,
            Holder<ArmorMaterial> material,
            ArmorItem.Type type,
            int durabilityMultiplier
    ) {
        return registerArmorItem(name, material, type, durabilityMultiplier, new Item.Properties());
    }

    private static DeferredItem<ArmorItem> registerArmorItem(
            String name,
            Holder<ArmorMaterial> material,
            ArmorItem.Type type,
            int durabilityMultiplier,
            Item.Properties properties
    ) {
        return ARMOR_ITEMS.register(
                name,
                () -> new BaseArmor(
                        material,
                        type,
                        properties.durability(type.getDurability(durabilityMultiplier))
                )
        );
    }
}
