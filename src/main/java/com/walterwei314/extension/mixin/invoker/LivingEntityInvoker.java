package com.walterwei314.extension.mixin.invoker;


import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.tools.obfuscation.ObfuscationManager;

@Mixin(LivingEntity.class)
public interface LivingEntityInvoker {
    @Invoker("getDamageAfterArmorAbsorb")
    float invokeGetDamageAfterArmorAbsorb(
            DamageSource damageSource,
            float damageAmount
    );

    @Invoker("getDamageAfterMagicAbsorb")
    float invokeGetDamageAfterMagicAbsorb(
            DamageSource damageSource,
            float damageAmount
    );
}
