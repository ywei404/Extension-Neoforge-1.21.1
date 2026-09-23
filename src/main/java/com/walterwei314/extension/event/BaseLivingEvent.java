package com.walterwei314.extension.event;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

public class BaseLivingEvent extends LivingEvent {
    public BaseLivingEvent(LivingEntity entity) {
        super(entity);
    }

    public static class LivingAbsorptionChangeEvent extends BaseLivingEvent implements ICancellableEvent {
        private float amount;

        public LivingAbsorptionChangeEvent(LivingEntity entity, float amount) {
            super(entity);
            this.amount = amount;
        }

        public float getAmount() {
            return amount;
        }

        public void setAmount(float amount) {
            this.amount = amount;
        }
    }
}
