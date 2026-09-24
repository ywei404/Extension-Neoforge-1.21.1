package com.walterwei314.extension;

import com.walterwei314.extension.attribute.ModAttributes;
import com.walterwei314.extension.attribute.event.AttributeLivingAbsorptionChangeEventHandler;
import com.walterwei314.extension.attribute.modifier.ModAttributeModifierIds;
import com.walterwei314.extension.event.BaseLivingEvent;
import net.minecraft.world.effect.MobEffects;
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

import java.util.Deque;

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

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void testAbsorptionChange(
            BaseLivingEvent.LivingAbsorptionChangeEvent event
    ) {
        LivingEntity entity = event.getEntity();

        if (!(entity instanceof Player player) ||
                player.level().isClientSide()) {
            return;
        }

        // =========================================================
        // Absorption
        // =========================================================

        float currentAbsorption = player.getAbsorptionAmount();
        float requestedAbsorption = event.getAmount();

        float rawChange =
                requestedAbsorption - currentAbsorption;


        // =========================================================
        // Attributes
        // =========================================================

        AttributeInstance multiplierInstance =
                player.getAttribute(
                        ModAttributes.ABSORPTION_GAIN_MULTIPLIER
                );

        AttributeInstance baseValueInstance =
                player.getAttribute(
                        ModAttributes.ABSORPTION_EFFECT_BASE_VALUE
                );

        AttributeInstance maxAbsorptionInstance =
                player.getAttribute(
                        Attributes.MAX_ABSORPTION
                );


        double multiplier =
                multiplierInstance == null
                        ? Double.NaN
                        : multiplierInstance.getValue();

        double effectBaseValue =
                baseValueInstance == null
                        ? Double.NaN
                        : baseValueInstance.getValue();

        double currentMaxAbsorption =
                maxAbsorptionInstance == null
                        ? Double.NaN
                        : maxAbsorptionInstance.getValue();


        // =========================================================
        // MAX_ABSORPTION modifiers
        // =========================================================

        double absorptionEffectModifier = 0.0D;
        double dynamicMaxModifier = 0.0D;

        if (maxAbsorptionInstance != null) {

            AttributeModifier effectModifier =
                    maxAbsorptionInstance.getModifier(
                            ModAttributeModifierIds.ABSORPTION_MODIFIER
                    );

            AttributeModifier dynamicModifier =
                    maxAbsorptionInstance.getModifier(
                            ModAttributeModifierIds
                                    .ABSORPTION_GAIN_MULTIPLIER_MODIFIER
                    );

            if (effectModifier != null) {
                absorptionEffectModifier =
                        effectModifier.amount();
            }

            if (dynamicModifier != null) {
                dynamicMaxModifier =
                        dynamicModifier.amount();
            }
        }


        // =========================================================
        // Pending Absorption Effect change
        //
        // 现在每个 Entity 都有自己的 pending Deque。
        //
        // 注意：
        // Debug 只能 peek，不能 pop！
        // =========================================================

        Deque<Double> pendingChanges =
                AttributeLivingAbsorptionChangeEventHandler
                        .ABSORPTION_EFFECT_PENDING_CHANGES
                        .get(player.getUUID());

        boolean hasPendingEffectChange =
                pendingChanges != null &&
                        !pendingChanges.isEmpty();

        int pendingCount =
                pendingChanges == null
                        ? 0
                        : pendingChanges.size();

        double pendingEffectChange =
                hasPendingEffectChange
                        ? pendingChanges.peek()
                        : Double.NaN;


        // =========================================================
        // Predict Handler calculation
        // =========================================================

        /*
         * 正式 Handler：
         *
         * 如果是正增长，并且当前 Entity 有 Absorption Effect
         * 留下来的 pending change，则优先使用它。
         */
        double effectiveChange =
                rawChange > 0.0F &&
                        hasPendingEffectChange
                        ? pendingEffectChange
                        : rawChange;


        /*
         * 正增长才应用 multiplier。
         * 负增长保持原值。
         */
        double predictedActualChange =
                effectiveChange > 0.0D
                        ? effectiveChange * multiplier
                        : effectiveChange;


        /*
         * 预测最终 Absorption。
         */
        double predictedFinalAbsorption =
                currentAbsorption +
                        predictedActualChange;


        // =========================================================
        // Predict Dynamic MAX change
        // =========================================================

        double requiredDynamicMaxChange;

        if (effectiveChange > 0.0D) {

            if (hasPendingEffectChange) {

                /*
                 * Absorption Effect：
                 *
                 * 原版已经负责了 effectiveChange 对应的 MAX，
                 * 所以这里只需要 multiplier 额外创造出来的部分。
                 */
                requiredDynamicMaxChange =
                        predictedActualChange -
                                effectiveChange;

            } else {

                /*
                 * 普通 Absorption gain：
                 *
                 * 没有 Absorption Effect 帮我们提供 MAX，
                 * 所以整个 actual gain 都需要 dynamic MAX。
                 */
                requiredDynamicMaxChange =
                        predictedActualChange;
            }

        } else {

            /*
             * Absorption 减少：
             *
             * dynamic MAX 优先被消耗。
             */
            requiredDynamicMaxChange =
                    effectiveChange;
        }


        double predictedDynamicMax =
                Math.max(
                        0.0D,
                        dynamicMaxModifier +
                                requiredDynamicMaxChange
                );


        /*
         * 当前 MAX 中已经包含旧 dynamic modifier。
         *
         * 所以预测 Handler 执行后的 MAX：
         *
         * current MAX
         * - old dynamic MAX
         * + new dynamic MAX
         */
        double predictedMaxAbsorption =
                currentMaxAbsorption
                        - dynamicMaxModifier
                        + predictedDynamicMax;


        // =========================================================
        // Output
        // =========================================================

        System.out.printf("""
                    
                    ================= ABSORPTION DEBUG =================
                    Player: %s
                    UUID:   %s
                    
                    [ABSORPTION]
                      Current:                         %8.2f
                      Event Requested:                 %8.2f
                      Raw Change:                     %+8.2f
                    
                    [ABSORPTION EFFECT]
                      Effect Base Value:               %8.2f
                      Effect MAX Modifier:             %8.2f
                    
                    [PENDING EFFECT DATA]
                      Has Pending Change:              %8s
                      Pending Count:                   %8d
                      Pending Change:                  %8s
                    
                    [MULTIPLIER]
                      Gain Multiplier:                 %8.2f
                    
                    [DYNAMIC MAX]
                      Current Dynamic MAX Modifier:    %8.2f
                      Required Dynamic MAX Change:    %+8.2f
                      Predicted Dynamic MAX:           %8.2f
                    
                    [CALCULATION]
                      Effective Change:               %+8.2f
                      Predicted Actual Change:        %+8.2f
                      Predicted Final Absorption:      %8.2f
                    
                    [MAX ABSORPTION]
                      MAX Before Handler:              %8.2f
                      Predicted MAX After Handler:     %8.2f
                    
                    ====================================================
                    
                    """,

                player.getName().getString(),
                player.getUUID(),

                currentAbsorption,
                requestedAbsorption,
                rawChange,

                effectBaseValue,
                absorptionEffectModifier,

                hasPendingEffectChange,
                pendingCount,
                hasPendingEffectChange
                        ? String.format("%.2f", pendingEffectChange)
                        : "NONE",

                multiplier,

                dynamicMaxModifier,
                requiredDynamicMaxChange,
                predictedDynamicMax,

                effectiveChange,
                predictedActualChange,
                predictedFinalAbsorption,

                currentMaxAbsorption,
                predictedMaxAbsorption
        );
    }
}
