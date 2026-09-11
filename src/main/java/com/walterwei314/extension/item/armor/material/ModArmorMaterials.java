package com.walterwei314.extension.item.armor.material;

import com.walterwei314.extension.Extensionneoforge1211;
import com.walterwei314.extension.attribute.BaseAttribute;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class ModArmorMaterials {
    private ModArmorMaterials() {}

    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, Extensionneoforge1211.MODID);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> DIRT_ARMOR_MATERIAL = ARMOR_MATERIALS.register(
            "dirt_armor_material",
            id -> new ArmorMaterial(
                    Map.of(
                            ArmorItem.Type.HELMET, 1,
                            ArmorItem.Type.CHESTPLATE, 1,
                            ArmorItem.Type.LEGGINGS, 1,
                            ArmorItem.Type.BOOTS, 1
                    ),
                    5,
                    SoundEvents.ARMOR_EQUIP_LEATHER,
                    () -> Ingredient.of(Items.DIRT),
                    List.of(new ArmorMaterial.Layer(id)),
                    0.0F,
                    0.0F
            )
    );
}
