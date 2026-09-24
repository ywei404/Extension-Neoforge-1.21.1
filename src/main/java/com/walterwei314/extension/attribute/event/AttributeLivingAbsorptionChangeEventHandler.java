package com.walterwei314.extension.attribute.event;

import com.walterwei314.extension.attribute.ModAttributes;
import com.walterwei314.extension.attribute.modifier.ModAttributeModifierIds;
import com.walterwei314.extension.event.BaseLivingEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

import java.util.*;

@EventBusSubscriber
public class AttributeLivingAbsorptionChangeEventHandler {
    public static final Map<UUID, Deque<Double>> ABSORPTION_EFFECT_PENDING_CHANGES = new HashMap<>();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingAbsorptionChange(
            BaseLivingEvent.LivingAbsorptionChangeEvent event
    ) {
        LivingEntity entity = event.getEntity();

        if (entity.level().isClientSide()) {
            return;
        }

        AttributeInstance maxAbsorptionInstance = entity.getAttribute(Attributes.MAX_ABSORPTION);
        AttributeInstance absorptionMultiplierInstance = entity.getAttribute(ModAttributes.ABSORPTION_GAIN_MULTIPLIER);

        if (maxAbsorptionInstance == null || absorptionMultiplierInstance == null) {
            return;
        }

        float requestedAmount = event.getAmount();
        float currentAmount = entity.getAbsorptionAmount();

        /*
         * 默认情况下，直接使用 setAbsorptionAmount() 请求产生的变化量。
         */
        float changeAmount = requestedAmount - currentAmount;

        /*
         * Absorption MobEffect 会提前在 MobEffectEvent.Added 中记录
         * 更准确的 MAX_ABSORPTION 变化量。
         *
         * 例如：
         *
         * Absorption I -> IV
         *
         * 原版 effect modifier:
         *     4 -> 16
         *
         * 真正的 effect gain:
         *     +12
         *
         * 但如果当前实际 absorption 已经因为 multiplier 变成 8，
         * setter 表面只能看到：
         *
         *     8 -> 16
         *     raw change = +8
         *
         * 因此这里优先使用提前记录的 +12。
         */
        Deque<Double> pendingChanges = ABSORPTION_EFFECT_PENDING_CHANGES.get(entity.getUUID());
        boolean isCausedByAbsorptionEffect = pendingChanges != null && !pendingChanges.isEmpty();

        if (changeAmount > 0.0F && isCausedByAbsorptionEffect) {
            changeAmount = pendingChanges.pop().floatValue();

            if (pendingChanges.isEmpty()) {
                ABSORPTION_EFFECT_PENDING_CHANGES.remove(entity.getUUID());
            }
        }

        /*
         * 获取当前由本系统维护的动态 MAX_ABSORPTION。
         */
        AttributeModifier modifier =
                maxAbsorptionInstance.getModifier(ModAttributeModifierIds.ABSORPTION_GAIN_MULTIPLIER_MODIFIER);
        float dynamicMaxAbsorption = modifier == null ? 0.0F : (float) modifier.amount();

        /*
         * 后面需要重新计算 modifier，
         * 所以先删除旧值。
         */
        if (modifier != null) {
            maxAbsorptionInstance.removeModifier(modifier);
        }

        if (changeAmount > 0.0F) {

            /*
             * 正增长：
             *
             * multiplier = 2
             * changeAmount = 4
             *
             * actualChangeAmount = 8
             */
            double multiplier = absorptionMultiplierInstance.getValue();
            float actualChangeAmount = (float) (changeAmount * multiplier);

            /*
             * 两种来源需要不同处理：
             *
             * 1. Absorption MobEffect
             *
             *    原版 MAX_ABSORPTION 已经增加了 changeAmount。
             *    因此我们只需要提供 multiplier 额外创造的部分：
             *
             *        actual - original
             *
             *    例如：
             *
             *        vanilla MAX +4
             *        actual gain = 8
             *        dynamic MAX += 4
             *
             *
             * 2. 普通 absorption gain
             *
             *    例如：
             *
             *        setAbsorptionAmount(current + 5)
             *
             *    这种操作本身没有增加 MAX_ABSORPTION。
             *    因此本系统需要承担整个 multiplied gain：
             *
             *        dynamic MAX += actual
             */
            float requiredMaxIncrease =
                    isCausedByAbsorptionEffect ? actualChangeAmount - changeAmount : actualChangeAmount;
            dynamicMaxAbsorption += requiredMaxIncrease;

            /*
             * 最终真正应用到 absorption 的变化量。
             */
            changeAmount = actualChangeAmount;

        } else if (changeAmount < 0.0F) {

            /*
             * Absorption 减少时：
             *
             * 优先消耗本系统提供的 dynamic MAX。
             *
             * 例如：
             *
             * dynamic MAX = 16
             * damage = 5
             *
             * 16 -> 11
             */
            dynamicMaxAbsorption = Math.max(0.0F, dynamicMaxAbsorption + changeAmount);
        }

        /*
         * 如果仍然存在 dynamic MAX，
         * 重新添加 modifier。
         *
         * 使用 Permanent Modifier：
         * 玩家退出世界 / 重新进入之后仍然保存。
         */
        if (dynamicMaxAbsorption > 0.0F) {
            maxAbsorptionInstance.addPermanentModifier(
                    new AttributeModifier(
                            ModAttributeModifierIds.ABSORPTION_GAIN_MULTIPLIER_MODIFIER,
                            dynamicMaxAbsorption,
                            AttributeModifier.Operation.ADD_VALUE
                    )
            );
        }

        /*
         * 修改最终的 absorption amount。
         *
         * LivingEntity#setAbsorptionAmount() 后续仍然会使用
         * 更新后的 MAX_ABSORPTION 进行 clamp。
         */
        event.setAmount(currentAmount + changeAmount);
    }

    @SubscribeEvent
    public static void onLivingAddAbsorptionEffect(
            MobEffectEvent.Added event
    ) {
        LivingEntity entity = event.getEntity();

        if (entity.level().isClientSide()) {
            return;
        }

        MobEffectInstance effectInstance = event.getEffectInstance();

        if (effectInstance.getEffect() != MobEffects.ABSORPTION) {
            return;
        }

        AttributeInstance maxAbsorptionInstance = entity.getAttribute(Attributes.MAX_ABSORPTION);
        AttributeInstance baseValueInstance = entity.getAttribute(ModAttributes.ABSORPTION_EFFECT_BASE_VALUE);

        if (maxAbsorptionInstance == null || baseValueInstance == null) {
            return;
        }

        /*
         * 获取更新之前的原版 Absorption Effect modifier。
         */
        AttributeModifier originalModifier = maxAbsorptionInstance.getModifier(ModAttributeModifierIds.ABSORPTION_MODIFIER);
        double originalAmount = originalModifier == null ? 0.0D : originalModifier.amount();

        /*
         * 计算新 Absorption Effect 应提供的基础 MAX。
         *
         * 默认：
         *
         * I   = 4
         * II  = 8
         * III = 12
         * IV  = 16
         */
        double newAmount = baseValueInstance.getValue() * (effectInstance.getAmplifier() + 1);
        double changeAmount = newAmount - originalAmount;

        /*
         * 只有正增长需要记录。
         *
         * 后面的 LivingAbsorptionChangeEvent 会消费这个值，
         * 从而知道 Absorption Effect 真正增加了多少。
         */
        if (changeAmount > 0.0D) {
            ABSORPTION_EFFECT_PENDING_CHANGES.computeIfAbsent(entity.getUUID(), uuid -> new ArrayDeque<>()).push(changeAmount);
        }
    }
}
