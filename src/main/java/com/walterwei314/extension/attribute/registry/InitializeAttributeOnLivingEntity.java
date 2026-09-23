package com.walterwei314.extension.attribute.registry;

import com.walterwei314.extension.Extensionneoforge1211;
import com.walterwei314.extension.attribute.ModAttributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;

@EventBusSubscriber(modid = Extensionneoforge1211.MODID)
public final class InitializeAttributeOnLivingEntity {

    private InitializeAttributeOnLivingEntity() {
    }

    @SubscribeEvent
    public static void modifyAttributes(EntityAttributeModificationEvent event) {
        // 给你需要的实体类型添加 HEAL_MULTIPLIER
        event.getTypes().forEach(entityType -> {
            event.add(entityType, ModAttributes.HEAL_MULTIPLIER.getDelegate());
            event.add(entityType, ModAttributes.INVULNERABILITY_TICKS.getDelegate());
        });
    }
}
