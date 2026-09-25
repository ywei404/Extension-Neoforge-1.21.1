package com.walterwei314.extension.mixin;

import com.walterwei314.extension.event.ModHooks;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @ModifyVariable(
            method = "setAbsorptionAmount",
            at = @At("HEAD"),
            argsOnly = true
    )
    private float extension$onAbsorptionAmountChange(float absorptionAmount) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (!(entity.level() instanceof ServerLevel serverLevel)) {
            return absorptionAmount;
        }

        if (serverLevel.getEntity(entity.getId()) != entity) {
            return absorptionAmount;
        }

        return ModHooks.onLivingSetAbsorption(entity, absorptionAmount);
    }
}
