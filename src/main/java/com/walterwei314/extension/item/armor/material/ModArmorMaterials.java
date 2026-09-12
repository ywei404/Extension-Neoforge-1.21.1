package com.walterwei314.extension.item.armor.material;

import com.walterwei314.extension.Extensionneoforge1211;
import com.walterwei314.extension.attribute.BaseAttribute;
import com.walterwei314.extension.item.armor.util.ArmorItemUtils;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
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
                    ArmorItemUtils.distributeArmor(4), 5, SoundEvents.ARMOR_EQUIP_LEATHER,
                    () -> Ingredient.of(ItemTags.DIRT), List.of(new ArmorMaterial.Layer(id)), 0.0F, 0.0F
            )
    );

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> WOODEN_ARMOR_MATERIAL = ARMOR_MATERIALS.register(
            "wooden_armor_material",
            id -> new ArmorMaterial(
                    ArmorItemUtils.distributeArmor(9), 15, SoundEvents.ARMOR_EQUIP_GOLD,
                    () -> Ingredient.of(ItemTags.LOGS), List.of(new ArmorMaterial.Layer(id)), 0.0F, 0.0F
            )
    );

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> STONE_ARMOR_MATERIAL = ARMOR_MATERIALS.register(
            "stone_armor_material",
            id -> new ArmorMaterial(
                    ArmorItemUtils.distributeArmor(13), 7, SoundEvents.ARMOR_EQUIP_IRON,
                    () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS), List.of(new ArmorMaterial.Layer(id)), 0.0F, 0.0F
            )
    );
}
