package com.walterwei314.extension;

import com.walterwei314.extension.attribute.ModAttributes;
import com.walterwei314.extension.attribute.modifier.ModAttributeModifierIds;
import com.walterwei314.extension.event.BaseLivingEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber
public class Test {
    @SubscribeEvent
    public static void test(EntityTickEvent.Pre event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player player && !player.level().isClientSide() && player.level().getGameTime() % 20 == 0) {
//            AttributeInstance instance = player.getAttribute(ModAttributes.HEAL_MULTIPLIER);
//
//            if (instance != null) {
//                System.out.println("heal_multiplier: " + instance.getValue());
//            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void testAbsorptionChange(
            BaseLivingEvent.LivingAbsorptionChangeEvent event
    ) {
        LivingEntity entity = event.getEntity();

        if (!(entity instanceof Player player) || player.level().isClientSide()) {
            return;
        }

        float oldAmount = player.getAbsorptionAmount();
        float requestedAmount = event.getAmount();
        float changeAmount = requestedAmount - oldAmount;

        AttributeInstance multiplierInstance =
                player.getAttribute(ModAttributes.ABSORPTION_GAIN_MULTIPLIER);

        AttributeInstance maxAbsorptionInstance =
                player.getAttribute(Attributes.MAX_ABSORPTION);

        double multiplier = multiplierInstance == null
                ? Double.NaN
                : multiplierInstance.getValue();

        double maxAbsorption = maxAbsorptionInstance == null
                ? Double.NaN
                : maxAbsorptionInstance.getValue();

        double bonus = 0.0;

        if (maxAbsorptionInstance != null) {
            AttributeModifier modifier = maxAbsorptionInstance.getModifier(
                    ModAttributeModifierIds.ABSORPTION_GAIN_MULTIPLIER_MODIFIER
            );

            if (modifier != null) {
                bonus = modifier.amount();
            }
        }

        System.out.println("""
            
            ===== ABSORPTION CHANGE =====
            Player: %s
            Old Absorption:       %.2f
            Event Amount:         %.2f
            Change Amount:       %+.2f
            Gain Multiplier:      %.2f
            Extra Max Bonus:      %.2f
            Max Absorption:       %.2f
            =============================
            """.formatted(
                player.getName().getString(),
                oldAmount,
                requestedAmount,
                changeAmount,
                multiplier,
                bonus,
                maxAbsorption
        ));
    }
}
