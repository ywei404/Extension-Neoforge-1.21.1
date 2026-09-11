package com.walterwei314.extension.item.armor;

import com.walterwei314.extension.Extensionneoforge1211;
import com.walterwei314.extension.item.armor.material.ModArmorMaterials;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public final class ModArmorItems {

    private ModArmorItems() {
    }

    public static final DeferredRegister.Items ARMOR_ITEMS =
            DeferredRegister.createItems(Extensionneoforge1211.MODID);

    public static final List<DeferredItem<ArmorItem>> DIRT_ARMOR_SET =
            registerArmorSet("dirt", ModArmorMaterials.DIRT_ARMOR_MATERIAL.getDelegate(), 3);

    private static List<DeferredItem<ArmorItem>> registerArmorSet(
            String name,
            Holder<ArmorMaterial> material,
            int durabilityMultiplier
    ) {
        return List.of(
                registerArmorItem(name + "_helmet", material, ArmorItem.Type.HELMET, durabilityMultiplier),
                registerArmorItem(name + "_chestplate", material, ArmorItem.Type.CHESTPLATE, durabilityMultiplier),
                registerArmorItem(name + "_leggings", material, ArmorItem.Type.LEGGINGS, durabilityMultiplier),
                registerArmorItem(name + "_boots", material, ArmorItem.Type.BOOTS, durabilityMultiplier)
        );
    }

    private static DeferredItem<ArmorItem> registerArmorItem(
            String name,
            Holder<ArmorMaterial> material,
            ArmorItem.Type type,
            int durabilityMultiplier
    ) {
        return ARMOR_ITEMS.register(
                name,
                () -> new ArmorItem(
                        material,
                        type,
                        new Item.Properties().durability(type.getDurability(durabilityMultiplier))
                )
        );
    }
}
