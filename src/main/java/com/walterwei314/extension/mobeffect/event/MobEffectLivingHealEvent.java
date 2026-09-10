package com.walterwei314.extension.mobeffect.event;

import com.walterwei314.extension.mobeffect.ModMobEffects;
import com.walterwei314.extension.mobeffect.util.MobEffectUtils;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;

@EventBusSubscriber
public class MobEffectLivingHealEvent {
    @SubscribeEvent
    public static void healBoost(LivingHealEvent event) {
        MobEffectUtils.applyIfHasMobEffectOnServer(
                event,
                ModMobEffects.HEAL_BOOST,
                (mobEffectInstance) -> {
                    int level = mobEffectInstance.getAmplifier() + 1;
                    event.setAmount(event.getAmount() * (1.0F + 0.2F * level));
                }
        );
    }
}
