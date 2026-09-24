package com.walterwei314.extension.attribute.event;

import com.walterwei314.extension.attribute.ModAttributes;
import com.walterwei314.extension.attribute.modifier.ModAttributeModifierIds;
import com.walterwei314.extension.event.BaseLivingEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class AttributeLivingAbsorptionChangeEventHandler {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingAbsorptionChange(
            BaseLivingEvent.LivingAbsorptionChangeEvent event
    ) {
        LivingEntity entity = event.getEntity();

        if (entity.level().isClientSide()) {
            return;
        }

        float newAmount = event.getAmount();
        float amount = entity.getAbsorptionAmount();
        float changeAmount = newAmount - amount;

        AttributeInstance maxAbsorptionInstance =
                entity.getAttribute(Attributes.MAX_ABSORPTION);

        AttributeInstance absorptionMultiplierInstance =
                entity.getAttribute(ModAttributes.ABSORPTION_GAIN_MULTIPLIER);

        if (maxAbsorptionInstance == null ||
                absorptionMultiplierInstance == null) {
            return;
        }

        AttributeModifier modifier =
                maxAbsorptionInstance.getModifier(
                        ModAttributeModifierIds.ABSORPTION_GAIN_MULTIPLIER_MODIFIER
                );

        float extraMaxAbsorption =
                modifier == null ? 0.0F : (float) modifier.amount();

        if (modifier != null) {
            maxAbsorptionInstance.removeModifier(modifier);
        }

        if (changeAmount > 0.0F) {
            double multiplier = absorptionMultiplierInstance.getValue();
            float actualChangeAmount = (float) (changeAmount * multiplier);
            float bonusChangeAmount = actualChangeAmount - changeAmount;
            extraMaxAbsorption += bonusChangeAmount;
            changeAmount = actualChangeAmount;
        } else {
            // bonus 最先被消耗
            extraMaxAbsorption = Math.max(0.0F, extraMaxAbsorption + changeAmount);
        }

        if (extraMaxAbsorption > 0.0F) {
            maxAbsorptionInstance.addTransientModifier(
                    new AttributeModifier(
                            ModAttributeModifierIds.ABSORPTION_GAIN_MULTIPLIER_MODIFIER,
                            extraMaxAbsorption,
                            AttributeModifier.Operation.ADD_VALUE
                    )
            );
        }

        event.setAmount(amount + changeAmount);
    }
}
