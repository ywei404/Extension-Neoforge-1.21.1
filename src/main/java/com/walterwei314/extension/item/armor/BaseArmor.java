package com.walterwei314.extension.item.armor;

import com.walterwei314.extension.item.armor.ability.InventoryTick;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class BaseArmor extends ArmorItem {
    public BaseArmor(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void inventoryTick(
            @NotNull ItemStack stack,
            @NotNull Level level,
            @NotNull Entity entity,
            int slotId,
            boolean isSelected
    ) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);

        if (level.isClientSide()) {
            return;
        }

        if (!(entity instanceof LivingEntity livingEntity)) {
            return;
        }

        // 必须真的穿在对应装备栏里
        if (livingEntity.getItemBySlot(getType().getSlot()) != stack) {
            return;
        }

        InventoryTick.TICKS.get(this)
                .forEach(tick -> tick.accept(stack, livingEntity, slotId));
    }
}
