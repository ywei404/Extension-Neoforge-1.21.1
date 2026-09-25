package com.walterwei314.extension.mixin;

import com.walterwei314.extension.attribute.ModAttributes;
import com.walterwei314.extension.attribute.modifier.ModAttributeModifierIds;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.world.effect.AbsorptionMobEffect")
public abstract class AbsorptionMobEffectMixin {

    @ModifyConstant(
            method = "onEffectStarted",
            constant = @Constant(intValue = 4)
    )
    private int extension$modifyAbsorptionBaseValue(
            int original,
            LivingEntity entity,
            int amplifier
    ) {
        AttributeInstance instance = entity.getAttribute(ModAttributes.ABSORPTION_EFFECT_BASE_VALUE.getDelegate());

        if (instance == null) {
            return original;
        }

        return (int) instance.getValue();
    }

    @Inject(
            method = "onEffectStarted",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;setAbsorptionAmount(F)V"
            )
    )
    private void extension$replaceMaxAbsorptionModifier(
            LivingEntity p_294820_, int p_295222_, CallbackInfo ci
    ) {
        AttributeInstance maxAbsorption = p_294820_.getAttribute(Attributes.MAX_ABSORPTION);
        AttributeInstance baseValue = p_294820_.getAttribute(ModAttributes.ABSORPTION_EFFECT_BASE_VALUE.getDelegate());

        if (maxAbsorption == null || baseValue == null) {
            return;
        }

        maxAbsorption.removeModifier(ModAttributeModifierIds.ABSORPTION_MODIFIER);

        maxAbsorption.addPermanentModifier(
                new AttributeModifier(
                        ModAttributeModifierIds.ABSORPTION_MODIFIER,
                        baseValue.getValue() * (p_295222_ + 1),
                        AttributeModifier.Operation.ADD_VALUE
                )
        );
    }
}
