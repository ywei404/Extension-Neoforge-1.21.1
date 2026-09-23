package com.walterwei314.extension.event;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForge;

public final class ModHooks {
    private ModHooks() {}

    public static float onLivingSetAbsorption(LivingEntity entity, float amount) {
        BaseLivingEvent.LivingAbsorptionChangeEvent event = new BaseLivingEvent.LivingAbsorptionChangeEvent(entity, amount);
        return NeoForge.EVENT_BUS.post(event).isCanceled() ? entity.getAbsorptionAmount() : event.getAmount();
    }
}
